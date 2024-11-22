%{
    import estructura_arbol.*;
    import error.*;
    import java.util.List;
    import java.util.ArrayList;
    import java.util.Map;
    import java.util.NavigableMap;
    import manejo_archivos.*;
  %}
  
  %token ID BEGIN END IF TOS THEN ELSE END_IF OUTF TYPEDEF FUN RET REPEAT UNTIL STRUCT GOTO SINGLE UINTEGER TAG UINTEGER_CONST SINGLE_CONST HEXA_CONST CADENA MENOR_IGUAL ASIGNACION MAYOR_IGUAL DISTINTO
  %right ASIGNACION
  %start programa
  %%
  
 programa: header_programa lista_sentencias END {
              Nodo programa = new NodoCompuesto("programa",(Nodo)$2.obj,null);
              FileHandler.appendToFile("salida_arbol_sintactico.txt",programa.toString()); // Salida del arbol sintactico a un archivo
              $$.obj = programa; programaFinal = programa;
              actualizarTipo($1.sval, "NOMBRE_PROGRAMA"); // Actualiza el tipo de la variable que se genera con el nombre del programa, puede servir a futuro..
              actualizarUso($1.sval, "NOMBRE_PROGRAMA");
              System.out.println("-------- FIN DEL PROGRAMA --------");
          }
        | header_programa lista_sentencias error { yyerror(ERROR_END); }
        ;

header_programa: ID BEGIN {mangling.add($1.sval);
                //$$.obj = new NodoConcreto($1.sval);  // Nodo para una variable
}
                | ID error {yyerror(ERROR_BEGIN);}
                | error BEGIN {yyerror(ERROR_NOMBRE_PROGRAMA);}
                ;
  
  
lista_sentencias: sentencia { $$ = $1; }
               | lista_sentencias sentencia { $$.obj = new NodoCompuesto("s",(Nodo)$1.obj,(Nodo)$2.obj); } //PREGUNTAR PORQUE ESTO GENERA NODOS DE SENTENCIAS DECLARATIVAS
               ;
  
  sentencia: sentencia_declarativa { $$ = $1; }
           | sentencia_ejecutable  { $$ = $1; }
           ;
    
  sentencia_declarativa: tipo lista_variables ';' {String[] lista = ($2.sval).split(",");
                                                   for (String s : lista){
                                                        if(ts.buscar(actualizarAmbito(s)) == null){
                                                            String s_mangling = nameMangling(s);
                                                            actualizarTipo(s_mangling, $1.sval);
                                                        }else{
                                                            yyerror(VARIABLE_REDECLARADA);
                                                            borrarSimbolos(s);
                                                        }
                                                   }
                                                  }
                      | TAG ';' {
                                if(ts.buscar(actualizarAmbito($1.sval)) == null){
                                    actualizarUso($1.sval, "TAG");
                                    String nameTag = nameMangling($1.sval);
                                    $$.obj = new NodoTAG(nameTag, null, null);
                                }else{
                                    yyerror(VARIABLE_REDECLARADA);
                                    borrarSimbolos($1.sval);
                                }
                                }
                      | TAG error {yyerror(ERROR_PUNTOCOMA);}
                      | tipo ID ';' {
                                    if(ts.buscar(actualizarAmbito($2.sval)) == null){
                                        actualizarUso($2.sval, "Variable");
                                        if (tipoEmbebido($2.sval))
                                            chequeoTipo($2.sval,$1.sval);
                                        else
                                            actualizarTipo($2.sval, $1.sval);
                                        //SE BUSCA EN LA TABLA DE SIMBOLOS SI EL TIPO DE LA VARIABLE ES UN STRUCT, SE DESCARTA LA BUSQUEDA SI EL TIPO ES UINTEGER O SINGLE
                                        if(!($1.sval.equalsIgnoreCase("UINTEGER") || $1.sval.equalsIgnoreCase("SINGLE"))){
                                            Token estructura = estaDeclarado($1.sval);
                                            if (estructura == null){
                                                yyerror(TIPO_NO_DEFINIDO);
                                            }else{
                                                NavigableMap<String,String> variables = ((TokenStruct)estructura).getVariables();                                       //obtengo las variables del struct
                                                for (Map.Entry<String, String> entry : variables.entrySet()) {                                                          //recorro las variables del struct
                                                    ts.insertar(new Token(257,nameMangling(entry.getKey()+":"+$2.sval),"Identificador",estructura.getType(entry.getKey()), ""));     //las agrego a la tabla de simbolos
                                                }
                                                FileHandler.appendToFile(filePathParser,"DECLARACION DE STRUCT. Linea "+lex.getNumeroLinea() );
                                            }
                                            borrarSimbolos($1.sval);
                                        }
                                        nameMangling($2.sval);
                                    }else{
                                        yyerror(VARIABLE_REDECLARADA);
                                    }
                                }
                      | tipo ID error {yyerror(ERROR_PUNTOCOMA);}
                      | ID ';' {
                                if(ts.buscar(actualizarAmbito($1.sval)) == null){
                                    actualizarUso($1.sval, "Variable"); nameMangling($1.sval);
                                }else{
                                    yyerror(VARIABLE_REDECLARADA);
                                    borrarSimbolos($1.sval);
                                }
                               }
                      | ID error {yyerror(ERROR_PUNTOCOMA);}
                      | lista_variables ';' {
                                            $$ = $1; $$.obj = null;
                                            String[] lista = ($1.sval).split(",");
                                                   for (String s : lista){
                                                        if(ts.buscar(actualizarAmbito(s)) == null){
                                                            nameMangling(s);
                                                        }else{
                                                            yyerror(VARIABLE_REDECLARADA);
                                                            borrarSimbolos(s);
                                                        }
                                                   }
                                            }
                      | header_funcion BEGIN lista_sentencias END {
                                                                    if ($$.ival == 1){
                                                                        FileHandler.appendToFile(filePathParser,"DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                        FileHandler.appendToFile(filePathParser,"FUNCION: "+$1.sval);
                                                                        Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+$1.sval);
                                                                        $$.obj = new NodoFuncion($1.sval,(Nodo)$3.obj,delimitador, ts.devolverTipo($1.sval));
                                                                        mangling.remove(mangling.size() - 1);
                                                                    }
                                                                  }
                      | header_funcion BEGIN error END  {yyerror(ERROR_RET);}
                      | error BEGIN lista_sentencias END {yyerror(ERROR_HEADER_FUNC);}
                      | struct ';'
                      | struct error {yyerror(ERROR_PUNTOCOMA);}
                      | tipo lista_variables error {yyerror(ERROR_PUNTOCOMA);}
                      | lista_variables error {yyerror(ERROR_PUNTOCOMA);}
                      ;

  header_funcion: tipo FUN ID '(' parametro ')'{
                                if(ts.buscar(actualizarAmbito($3.sval)) == null){
                                    actualizarUso($3.sval, "Funcion"); actualizarTipo($3.sval, $1.sval);
                                    errorRedeclaracion($3.sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                                    this.nuevoNombre = nameMangling($3.sval); mangling.add($3.sval); $$.sval = this.nuevoNombre;
                                    String[] parametro = ($5.sval).split(",");
                                    nameMangling(parametro[1]);
                                    actualizarTipoParamEsperado(this.nuevoNombre, parametro[0]);
                                    $$.ival = 1;
                                }else{
                                    yyerror(FUNCION_REDECLARADA);
                                    borrarSimbolos($3.sval);
                                    $$.ival = 0;
                                }
                              }
                
                | tipo FUN error {yyerror(ERROR_NOMBRE_FUNCION);}
                | tipo FUN ID '(' error ')' {yyerror(ERROR_CANTIDAD_PARAMETRO);}
                ;


  tipo: UINTEGER 
      | SINGLE 
      | ID
      ;
  
  parametro: tipo ID {actualizarUso($2.sval, "Parametro"); actualizarTipo($2.sval, $1.sval);
                      errorRedeclaracion($2.sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      $$.sval = $1.sval + "," + $2.sval;
                     }
          | tipo error {yyerror(ERROR_NOMBRE_PARAMETRO);}
          | error ID {yyerror(ERROR_TIPO);}
          ;
  
  sentencia_ejecutable: asignacion
                      | retorno
                      | invocacion_funcion
                      | seleccion_if
                      | imprimir
                      | repeat_until
                      | goto
                      ;
  
  asignacion: asignacion_simple
            | asignacion_multiple
            ;
  
  asignacion_simple: ID ASIGNACION expresion ';' {
                      borrarSimbolos($1.sval);
                      Token simbolo = estaDeclarado($1.sval);
                      if(simbolo != null){
                        $$.obj = new NodoAsignacion(":=",new NodoConcreto(simbolo.getLexema(), simbolo.getType()),(Nodo)$3.obj); // Lo creamos compuesto
                        FileHandler.appendToFile(filePathParser,"ASIGNACION");
                      }else{
                        yyerror(VARIABLE_NO_DECLARADA);
                      }
                   }
                   | ID ASIGNACION expresion error {yyerror(ERROR_PUNTOCOMA);}
                   | ID ASIGNACION error ';' {yyerror(ERROR_EXPRESION);}
                   | ID '.' ID ASIGNACION expresion ';' {
                                borrarSimbolos($1.sval);
                                borrarSimbolos($3.sval);
                                Token simbolo = estaDeclarado($3.sval + ":" + $1.sval);
                                if(simbolo != null){
                                    $$.obj = new NodoAsignacion(":=",new NodoConcreto(simbolo.getLexema(), simbolo.getType()),(Nodo)$5.obj); // Lo creamos compuesto
                                    FileHandler.appendToFile(filePathParser,"ASIGNACION");
                                }else{
                                    yyerror(VARIABLE_NO_DECLARADA);
                                }
                   }
                   | ID '.' ID ASIGNACION error ';' {yyerror(ERROR_EXPRESION);}
                   | ID '.' ID ASIGNACION expresion error {yyerror(ERROR_PUNTOCOMA);}
                   ;
  
  asignacion_multiple: lista_variables ASIGNACION lista_expresiones ';' {FileHandler.appendToFile(filePathParser,"ASIGNACION MULTIPLE");
                                                                         $$.obj = new NodoAsignacionMultiple(":=",(Nodo)$1.obj,(Nodo)$3.obj);
                                                                         if (!igualCantElementos($1.sval,$3.sval)) 
                                                                            yyerror(ERROR_CANTIDAD_ASIGNACION);
                                                                         borrarSimbolos($1.sval);
                                                                        }
                      | lista_variables ASIGNACION lista_expresiones error {yyerror(ERROR_PUNTOCOMA);}
                     ;
                  
  lista_variables: ID ',' ID /* Dos variables normales*/ {
                                                          Token hijoIzq = estaDeclarado($1.sval);
                                                          Token hijoDer = estaDeclarado($3.sval);
                                                          actualizarUso($1.sval, "Variable"); actualizarUso($3.sval, "Variable");
                                                          $$.sval = $1.sval + "," + $3.sval;
                                                          if (hijoIzq != null && hijoDer != null)
                                                            $$.obj = new NodoLista(",",new NodoConcreto(hijoIzq.getLexema(), hijoIzq.getType()),new NodoConcreto(hijoDer.getLexema(), hijoDer.getType()));
                                                          else
                                                            $$.obj = new NodoLista(",",new NodoConcreto($1.sval),new NodoConcreto($3.sval));
                                                         }
                  | ID '.' ID ',' ID '.' ID /* Dos variables struct*/
                  | lista_variables ',' ID  {actualizarUso($3.sval, "Variable");
                                            $$.sval = $1.sval + "," + $3.sval;
                                            Token simbolo = estaDeclarado($3.sval);
                                            if (simbolo != null)
                                                $$.obj = new NodoLista(",",(Nodo)$1.obj,new NodoConcreto(simbolo.getLexema(), simbolo.getType()));
                                            else
                                                $$.obj = new NodoLista(",",(Nodo)$1.obj,new NodoConcreto($1.sval));}
                  | lista_variables ',' ID '.' ID
                  //| ID ID {yyerror(ERROR_COMA);}
                  | ID '.' ID ID '.' ID {yyerror(ERROR_COMA);}
                  | lista_variables ID {yyerror(ERROR_COMA);}
                  | lista_variables ID '.' ID {yyerror(ERROR_COMA);}
                 ;
  
  lista_expresiones: expresion ',' expresion {$$.obj = new NodoLista(",",(Nodo)$1.obj,(Nodo)$3.obj); $$.sval = $1.sval + "," + $3.sval;}
                   | lista_expresiones ',' expresion {$$.obj = new NodoLista(",",(Nodo)$1.obj,(Nodo)$3.obj); $$.sval = $1.sval + "," + $3.sval;}
                   | expresion error expresion {yyerror(ERROR_COMA);}
                   //| lista_expresiones error expresion {yyerror(ERROR_COMA);}
                   | error ',' expresion {yyerror(ERROR_EXPRESION);}
                   | expresion ',' error {yyerror(ERROR_EXPRESION);}
                   ;
  
  
  retorno: RET '(' expresion ')' ';' { FileHandler.appendToFile(filePathParser,"RETORNO. Linea "+lex.getNumeroLinea());
                                      $$.obj = new NodoRet("RET",(Nodo)$3.obj,null);
                                    }
         | RET '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
         | RET '(' error ')' ';' {yyerror(ERROR_RETORNO);}
         ;
  
  expresion: expresion '+' termino {
                $$.obj = new NodoSuma("+",(Nodo)$1.obj,(Nodo)$3.obj);
                FileHandler.appendToFile(filePathParser, "SUMA. Linea " + lex.getNumeroLinea());
            }
         | expresion '-' termino {
            $$.obj = new NodoResta("-",(Nodo)$1.obj,(Nodo)$3.obj);
            FileHandler.appendToFile(filePathParser, "RESTA. Linea " + lex.getNumeroLinea());
        }
           | expresion '+' error {yyerror(ERROR_OPERANDO);}
           | expresion '-' error {yyerror(ERROR_OPERANDO);}
           | error '+' termino {yyerror(ERROR_OPERANDO);}
           | error '-' termino {yyerror(ERROR_OPERANDO);}
           | error '+' error {yyerror(ERROR_OPERANDO);}
           | error '-' error {yyerror(ERROR_OPERANDO);}
           | termino { $$ = $1;  }
           ;
  
  termino: termino '*' factor {
              $$.obj = new NodoMultiplicacion("*",(Nodo)$1.obj,(Nodo)$3.obj);
              FileHandler.appendToFile(filePathParser, "MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
       | termino '/' factor {
              $$.obj = new NodoDivision("/",(Nodo)$1.obj,(Nodo)$3.obj);
              FileHandler.appendToFile(filePathParser, "DIVISION. Linea " + lex.getNumeroLinea());
         }
         | termino '*' error  {yyerror(ERROR_OPERANDO);}
         | termino '/' error {yyerror(ERROR_OPERANDO);}
         | error '*' factor {yyerror(ERROR_OPERANDO);}
         | error '/' factor {yyerror(ERROR_OPERANDO);}
         | error '*' error {yyerror(ERROR_OPERANDO);}
         | error '/' error {yyerror(ERROR_OPERANDO);}
         | factor { $$ = $1; }
         ;
  
  factor: ID {
            Token simbolo = estaDeclarado($1.sval);
            if (simbolo == null){
                yyerror(VARIABLE_NO_DECLARADA);
                $$.obj = new NodoConcreto("N/D", "N/D");  // Nodo para una variable no declarada
            }
            else
                $$.obj = new NodoConcreto(simbolo.getLexema(), simbolo.getType());  // Nodo para una variable
            
            borrarSimbolos($1.sval);
         }
        | UINTEGER_CONST {
            $$.obj = new NodoConcreto($1.sval,"UINTEGER");  // Nodo para constante UINTEGER
         }
        | SINGLE_CONST {
            $$.obj = new NodoConcreto($1.sval,"SINGLE");  // Nodo para constante SINGLE
         }
        | HEXA_CONST {
            $$.obj = new NodoConcreto($1.sval,"HEXA");  // Nodo para constante HEXA
         }
        | ID '.' ID {   // Nodo para una variable struct
                        String componente = $3.sval + ":" + actualizarAmbito($1.sval);
                        $$.obj = new NodoConcreto(componente,ts.buscar(componente).getType());
                        borrarSimbolos($3.sval);
                        }
        | invocacion_funcion
        | conversion_explicita
        | '-' ID {
            Token simbolo = estaDeclarado($2.sval);
            if (simbolo == null)
                yyerror(VARIABLE_NO_DECLARADA);
            else{
                $$.obj = new NodoConcreto("-" + $2.sval, simbolo.getType());  // Nodo para una variable negativa
                borrarSimbolos($2.sval);
            }
        }
        | '-' SINGLE_CONST {actualizarSimbolo("-" + $2.sval,$2.sval); $$.obj = new NodoConcreto("-"+$2.sval,"SINGLE");}
        | '-' error {yyerror(ERROR_NO_NEGATIVO);}
        ;
  
  invocacion_funcion: ID '(' expresion ')' {
                                                Nodo nodoExpresion = (Nodo)$3.obj; // N/D si no hay nada

                                                if ((estaDeclarado($1.sval) != null) && paramRealIgualFormal($1.sval,nodoExpresion.devolverTipo(mangling))){
                                                    String nombreFuncion = estaDeclarado($1.sval).getLexema();
                                                    $$.obj = new NodoInvocacionFuncion("INVOCACION_FUNCION_" + nombreFuncion,nodoExpresion,null, ts.buscar(nombreFuncion).getType());
                                                }
                                                else if (estaDeclarado($1.sval) == null){
                                                    yyerror(FUNCION_NO_DECLARADA);
                                                    $$.obj = new NodoCompuesto("INVOCACION_FUNCION_" + "N/D",nodoExpresion,null);
                                                }
                                                else if(!paramRealIgualFormal($1.sval,nodoExpresion.devolverTipo(mangling))) {
                                                    yyerror(ERROR_TIPO_PARAMETRO);
                                                    $$.obj = new NodoCompuesto("INVOCACION_FUNCION_" + $1.sval,nodoExpresion,null);
                                                }

                                                borrarSimbolos($1.sval);
                                               }
                    | ID '(' error ')'{yyerror(ERROR_CANTIDAD_PARAMETRO);}
                    ;
  
  seleccion_if: IF '(' condicion ')' THEN bloque_sentencias END_IF ';' {
                  $$.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)$3.obj,null),new NodoCuerpo("CUERPO",(Nodo)$6.obj,null));
                  FileHandler.appendToFile(filePathParser,"DECLARACION DE IF. Linea " + lex.getNumeroLinea() );
              }
              | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';' {
                  $$.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)$3.obj,null),new NodoCuerpo("CUERPO",new NodoCompuesto("THEN",(Nodo)$6.obj,null),new NodoCompuesto("ELSE",(Nodo)$8.obj,null)));
                  FileHandler.appendToFile(filePathParser,"DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
              | IF '(' condicion ')' THEN bloque_sentencias END_IF error {yyerror(ERROR_PUNTOCOMA);}
              | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF error {yyerror(ERROR_PUNTOCOMA);}
              | IF  condicion ')' THEN bloque_sentencias END_IF ';'{yyerror(ERROR_PARENTESIS);}
              | IF '(' condicion THEN bloque_sentencias END_IF ';'{yyerror(ERROR_PARENTESIS);}
              | IF condicion THEN bloque_sentencias END_IF ';'{yyerror(ERROR_PARENTESIS);}
              | IF  condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';' {yyerror(ERROR_PARENTESIS);}
              | IF '(' condicion  THEN bloque_sentencias ELSE bloque_sentencias END_IF ';' {yyerror(ERROR_PARENTESIS);}
              | IF  condicion  THEN bloque_sentencias ELSE bloque_sentencias END_IF ';' {yyerror(ERROR_PARENTESIS);}
              | IF '(' condicion ')' THEN error END_IF ';' {yyerror(ERROR_CUERPO);}
              | IF '(' condicion ')' THEN error ELSE error END_IF ';' {yyerror(ERROR_CUERPO);}
              | IF '(' condicion ')' THEN bloque_sentencias ';' {yyerror(ERROR_END_IF);}
              | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias ';' {yyerror(ERROR_END_IF);}
              ;
  
  bloque_sentencias: BEGIN lista_sentencias_ejecutables END {$$ = $2;}
                   | sentencia_ejecutable {$$ = $1;}
                   ;
  
  lista_sentencias_ejecutables: lista_sentencias_ejecutables sentencia_ejecutable {$$.obj = new NodoCompuesto("s",(Nodo)$1.obj, new NodoCompuesto("s",(Nodo)$2.obj, null)); }
                              | sentencia_ejecutable {$$ = $1;}
                              ;
  
  condicion: expresion comparador expresion {$$.obj = new NodoCondicion($2.sval,(Nodo)$1.obj,(Nodo)$3.obj);}
           | expresion error expresion {yyerror(ERROR_OPERADOR);}
           | error comparador expresion {yyerror(ERROR_OPERANDO);}
           | expresion comparador error {yyerror(ERROR_OPERANDO);}
           ;
  
  comparador: '='
            | DISTINTO
            | '<'
            | '>'
            | MENOR_IGUAL
            | MAYOR_IGUAL
            ;
  
  imprimir: OUTF '(' expresion ')' ';' {$$.obj = new NodoOUTF("OUTF",(Nodo)$3.obj,null);}
          | OUTF '(' CADENA ')' ';' {$$.obj = new NodoOUTF("OUTF",new NodoConcreto($3.sval, "CADENA"),null);}
          | OUTF '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
          | OUTF '(' CADENA ')' error {yyerror(ERROR_PUNTOCOMA);}
          | OUTF '(' ')' ';' {yyerror(ERROR_CANTIDAD_PARAMETRO);}
          | OUTF '(' error ')' ';' {yyerror(ERROR_PARAMETRO);}
          ;
  
  repeat_until: REPEAT bloque_sentencias UNTIL '(' condicion ')' ';' {  FileHandler.appendToFile(filePathParser,"SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                        $$.obj = new NodoRepeat("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)$2.obj,null),new NodoCompuesto("CONDICION",(Nodo)$5.obj,null));
                                                                    } 
              | REPEAT bloque_sentencias '(' condicion ')' ';' {yyerror(ERROR_UNTIL);}
              | REPEAT bloque_sentencias UNTIL '(' condicion ')' error {yyerror(ERROR_PUNTOCOMA);}
              | REPEAT bloque_sentencias UNTIL  condicion ')' ';' {yyerror(ERROR_PARENTESIS);}
              | REPEAT bloque_sentencias UNTIL '(' condicion ';' {yyerror(ERROR_PARENTESIS);}
              | REPEAT bloque_sentencias UNTIL  condicion ';' {yyerror(ERROR_PARENTESIS);}
              | REPEAT error UNTIL '(' condicion ')' ';' {yyerror(ERROR_CUERPO);}
              ;
  
  struct: TYPEDEF bloque_struct_multiple ID {
                                            if (estaDeclarado($3.sval) == null){
                                                actualizarUso($3.sval, "Struct"); ts.insertar(new TokenStruct( 257, nameMangling($3.sval), $2.sval ));
                                            }else{
                                                yyerror(TIPO_REDEFINIDO);
                                                borrarSimbolos($3.sval);
                                            }
                                            }
        | TYPEDEF bloque_struct_simple ID  {
                                            if (estaDeclarado($3.sval) == null){
                                                actualizarUso($3.sval, "Struct"); ts.insertar(new TokenStruct( 257, nameMangling($3.sval), $2.sval ));
                                            }else{
                                                yyerror(TIPO_REDEFINIDO);
                                                borrarSimbolos($3.sval);
                                            }
                                           }
        | TYPEDEF bloque_struct_multiple error  {yyerror(ERROR_ID_STRUCT);}
        | TYPEDEF bloque_struct_simple error {yyerror(ERROR_ID_STRUCT);}
        ;
  
  bloque_struct_multiple: STRUCT '<' lista_tipos '>' BEGIN lista_variables END {actualizarTipoStruct($3.sval, $6.sval);  $$.sval = $6.sval+"."+$3.sval;}
                        | '<' lista_tipos '>' BEGIN lista_variables END {yyerror(ERROR_STRUCT);}
                        | STRUCT lista_tipos '>' BEGIN lista_variables END {yyerror(ERROR_TIPO_STRUCT);}
                        | STRUCT '<' lista_tipos BEGIN lista_variables END {yyerror(ERROR_TIPO_STRUCT);}
                        ;
  
  bloque_struct_simple: STRUCT '<' tipo '>' BEGIN ID END {actualizarUso($6.sval, "Variable"); $$.sval = $6.sval+"."+ $3.sval;}
                      | '<' tipo '>' BEGIN ID END {yyerror(ERROR_STRUCT);}
                      | tipo '>' BEGIN ID END {yyerror(ERROR_TIPO_STRUCT);}
                      | '<' tipo BEGIN ID END {yyerror(ERROR_TIPO_STRUCT);}
                      ;
  
  
  
  lista_tipos: lista_tipos ',' tipo {$$.sval = $1.sval + "," + $3.sval;}
             | tipo ',' tipo {$$.sval = $1.sval + "," + $3.sval;}
             ;
  
  goto: GOTO TAG ';' {
                        FileHandler.appendToFile(filePathParser, "SENTENCIA GOTO. Linea "+lex.getNumeroLinea());
                        errorRedeclaracion($2.sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:");
                        Token tokenTAG = estaDeclarado($2.sval);
                        if (tokenTAG != null){
                            $$.obj = new NodoGOTO("GOTO",new NodoConcreto(tokenTAG.getLexema()),null);
                        }
                        else
                            $$.obj = new NodoGOTO("GOTO",new NodoConcreto("N/D"),null);
                        borrarSimbolos($2.sval);
                     }
      | GOTO TAG error {yyerror(ERROR_PUNTOCOMA);}
      | GOTO error ';' {yyerror(ERROR_ETIQUETA);}
      ;
  
  conversion_explicita: TOS '(' expresion ')'{$$.obj = new NodoTOS("TOS",(Nodo)$3.obj,null);} // ¿CAMBIAR EXPRESION POR UINTEGER PARA NO ROMPER TODO CON STRUCT?
                      | TOS '(' error ')'{yyerror(ERROR_EXPRESION);}
                      ;
  
  %%
  
    private static final String VARIABLE_NO_DECLARADA = "variable no declarada";
    private static final String VARIABLE_REDECLARADA = "variable redeclarada";
    private static final String FUNCION_NO_DECLARADA = "funcion no declarada";
    private static final String FUNCION_REDECLARADA = "funcion redeclarada";
    private static final String TIPO_NO_DEFINIDO = "tipo no definido";
    private static final String TIPO_REDEFINIDO = "tipo redefinido";
    private static final String ERROR_BEGIN = "se espera un delimitador (BEGIN)";
    private static final String ERROR_CANTIDAD_PARAMETRO = "cantidad de parametros incorrectos";
    private static final String ERROR_CANTIDAD_ASIGNACION = "asignacion fallida: cantidad de variables y expresiones no coinciden";
    private static final String ERROR_TIPO_PARAMETRO = "tipo del parametro real no coincide con tipo del parametro formal";
    private static final String ERROR_COMA = "falta una ',' luego de la variable/expresion";
    private static final String ERROR_CUERPO = "error/falta de cuerpo";
    private static final String ERROR_END = "se espera un delimitador (END)";
    private static final String ERROR_END_IF = "falta de END_IF";
    private static final String ERROR_ETIQUETA = "falta la (TAG) de la etiqueta en GOTO";
    private static final String ERROR_EXPRESION = "falta una expresion";
    private static final String ERROR_NOMBRE_FUNCION = "se espera un nombre de funcion";
    private static final String ERROR_NOMBRE_PARAMETRO = "se espera un parametro correcto";
    private static final String ERROR_NOMBRE_PROGRAMA = "se espera un nombre de programa";
    private static final String ERROR_NO_NEGATIVO = "el factor no puede ser negativo";
    private static final String ERROR_OPERANDO = "falta operando en la expresion";
    private static final String ERROR_OPERADOR = "falta operador en la expresion";
    private static final String ERROR_PARENTESIS = "falta de parentesis";
    private static final String ERROR_PARAMETRO = "parametros incorrectos";
    private static final String ERROR_PUNTOCOMA = "falta un ';' al final";
    private static final String ERROR_RET = "se espera un retorno (RET)";
    private static final String ERROR_RETORNO = "Falta un retorno valido";
    private static final String ERROR_TIPO = "se espera un tipo";
    private static final String ERROR_UNTIL = "falta la palabra reservada (UNTIL)";
    private static final String ERROR_STRUCT = "falta la palabra reservada (STRUCT)";
    private static final String ERROR_ID_STRUCT = "error en la declaracion del nombre de la estructura STRUCT";
    private static final String ERROR_TIPO_STRUCT = "falta '<' o '>' al declarar el tipo";
    private static final String ERROR_HEADER_FUNC = "Algo fallo en la declaracion de la funcion";

    public static List<String> mangling = new ArrayList<String>();
    private String nuevoNombre = "";
    private Nodo programaFinal = null;

    static AnalizadorLexico lex = null;
    static TablaSimbolos ts = TablaSimbolos.getInstance();

    static String filePathParser = "salida_parser.txt";
  
    void main(String filePath) {
        // Código principal del compilador
        FileHandler.appendToFile(filePathParser,"Iniciando análisis sintáctico...");
        lex = AnalizadorLexico.getInstance(filePath);
        run();
        FileHandler.appendToFile(filePathParser,"Fin del análisis sintáctico");
        GeneradorCodigo.generarAssembler(programaFinal);
    }
  
    public static void yyerror(String s) {
        if (!s.equalsIgnoreCase("syntax error"))
            ErrorHandler.addError("Error: " + s + " en la linea "+lex.getNumeroLinea());
    }
  
    int yylex(){
        int token = lex.getProximoToken().getId();
        yylval = new ParserVal(lex.getUltimoLexema());
        return token;
    }
  
    void actualizarSimbolo(String lexema, String nuevo_lexema) {
        ts.actualizarSimbolo(lexema,nuevo_lexema);
    }

    void actualizarUso(String lexema,String uso) {
        ts.actualizarUso(lexema,uso);
    }

    void actualizarTipo(String lexema, String tipo) {
        ts.actualizarTipo(lexema, tipo);
    }

    String devolverTipo(String lexema) {
        return ts.devolverTipo(lexema);
    }

    void actualizarTipoStruct(String tipos, String variables) {
        String[] tiposArray = tipos.split(",");
        String[] variablesArray = variables.split(",");

        for (int i = 0; i < variablesArray.length; i++) {
            if (tipoEmbebido(variablesArray[i]))
                chequeoTipo(variablesArray[i],tiposArray[i]);
            else
                actualizarTipo(variablesArray[i], tiposArray[i]);
        }
    }

    boolean tipoEmbebido(String lexema){
        if (lexema.charAt(0) == 'u' || lexema.charAt(0) == 'v' || lexema.charAt(0) == 'w' || lexema.charAt(0) == 's')
            return true;
        return false;
    }

    void errorRedeclaracion(String lexema, String mensajeError) {
        if (tipoEmbebido(lexema))
            ErrorHandler.addError(mensajeError + lexema);
    }

    void chequeoTipo(String nombre, String tipo) {
        if ((nombre.charAt(0) == 's') && tipo.equals("uinteger") ) {
            ts.actualizarTipo(nombre, "UINTEGER");
            System.out.println("##WARNING##: Redeclaracion de variable "+nombre+" como UINTEGER. Linea "+lex.getNumeroLinea());
        } else if ((nombre.charAt(0) == 'u' || nombre.charAt(0) == 'v' || nombre.charAt(0) == 'w') && tipo.equals("single") ) {
            ts.actualizarTipo(nombre, "SINGLE");
            System.out.println("##WARNING##: Redeclaracion de variable "+nombre+" como SINGLE. Linea "+lex.getNumeroLinea());
        }
    }

    String actualizarAmbito(String lexema){
        for (String mangle : mangling) {
                lexema = lexema + ":" + mangle;
            }
        return lexema;
    }

    String actualizarAmbito(String lexema, ArrayList<String> ambitoActual){
        for (String mangle : ambitoActual) {
                lexema = lexema + ":" + mangle;
            }
        return lexema;
    }

    String nameMangling(String lexema){
        if (lexema.isEmpty())
            return null;
        String lexema_viejo = lexema;
        String lexema_nuevo = actualizarAmbito(lexema);
        ts.actualizarSimbolo(lexema_nuevo, lexema_viejo);
        return lexema_nuevo;
    }

    void actualizarTipoParamEsperado(String funcion, String tipoParametro){
        ts.actualizarTipoParamEsperado(funcion, tipoParametro);
    }

    
    Boolean paramRealIgualFormal(String funcion, String tipoParamReal){
        Token token = ts.buscar(estaDeclarado(funcion).getLexema());

        if (token != null) {
            String tipoParamFormal = token.getTipoParametroEsperado();
            if(tipoParamFormal.equals(tipoParamReal)){
                return true;
            }
            return false;
        }
        return false;
    }

    Boolean igualCantElementos(String variables, String expresiones){
        String[] variablesArray = variables.split(",");
        String[] expresionesArray = expresiones.split(",");
        return variablesArray.length == expresionesArray.length;
    }

    void borrarSimbolos(String lexema) {
        ts.borrarSimbolos(lexema);
    }

    Token estaDeclarado(String lexema) {
        String copiaLexema = lexema;
        copiaLexema = actualizarAmbito(copiaLexema);
        while (copiaLexema.contains(":")) {
            Token tokenRetorno = ts.buscar(copiaLexema);
            if(tokenRetorno != null)
                return tokenRetorno;
            copiaLexema = copiaLexema.substring(0, copiaLexema.lastIndexOf(":"));
        }
        return null;
    }