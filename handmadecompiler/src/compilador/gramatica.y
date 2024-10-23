%{
    package compilador;
    import estructura_arbol.*;
  %}
  
  %token ID BEGIN END IF TOS THEN ELSE END_IF OUTF TYPEDEF FUN RET REPEAT UNTIL STRUCT GOTO SINGLE UINTEGER TAG UINTEGER_CONST SINGLE_CONST HEXA_CONST CADENA MENOR_IGUAL ASIGNACION MAYOR_IGUAL DISTINTO ID_STRUCT
  %right ASIGNACION
  %start programa
  %%
  
 programa: ID BEGIN lista_sentencias END {
              Nodo programa = new NodoCompuesto("programa",(Nodo)$1.obj, (Nodo)$3.obj);
              System.out.println(programa.toString());  // Imprime el árbol sintáctico completo
              $$.obj = programa;  // Almacena el nodo en ParserVal
              actualizarTipo($1.sval, "NOMBRE_PROGRAMA"); // Actualiza el tipo de la variable que se genera con el nombre del programa, puede servir a futuro..
              actualizarUso($1.sval, "NOMBRE_PROGRAMA");
          }
        | ID BEGIN lista_sentencias error { yyerror(ERROR_END); }
        | ID error lista_sentencias END  { yyerror(ERROR_BEGIN); }
        | error BEGIN lista_sentencias END  { yyerror(ERROR_NOMBRE_PROGRAMA); }
        ;

  
  
lista_sentencias: sentencia { $$ = $1; }
               | lista_sentencias sentencia { $$.obj = new NodoCompuesto("s",(Nodo)$1.obj,(Nodo)$2.obj); } //PREGUNTAR PORQUE ESTO GENERA NODOS DE SENTENCIAS DECLARATIVAS
               ;
  
  sentencia: sentencia_declarativa { $$ = $1; }
           | sentencia_ejecutable  { $$ = $1; }
           ;
    
  sentencia_declarativa: tipo lista_variables ';' {//actualizarTipo($2.sval, $1.sval);
                                                actualizarTipoDelGrupo($1.sval);
                                                }
                      | TAG ';'
                      | tipo ID ';' {actualizarUso($2.sval, "Variable"); actualizarTipo($2.sval, $1.sval);}
                      | ID ';' {actualizarUso($1.sval, "Variable");}
                      | lista_variables ';'
                      | tipo FUN ID '(' parametro ')' BEGIN lista_sentencias END {System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea()); actualizarUso($3.sval, "Funcion"); actualizarTipo($3.sval, $1.sval);
                                                                                  Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+$3.sval); // Uso delimitador para las funciones
                                                                                  $$.obj = new NodoCompuesto("FUNCION",(Nodo)$8.obj,delimitador);
                                                                                }
                      | struct ';'
                      | tipo lista_variables error {yyerror(ERROR_PUNTOCOMA);}
                      | tipo ID error {yyerror(ERROR_PUNTOCOMA);}
                      | ID error {yyerror(ERROR_PUNTOCOMA);}
                      | lista_variables error {yyerror(ERROR_PUNTOCOMA);}
                      | tipo FUN error '(' parametro ')' BEGIN lista_sentencias END {yyerror(ERROR_NOMBRE_FUNCION);}
                      | tipo FUN ID '(' parametro ')' BEGIN error END  {yyerror(ERROR_RET);}
                      | struct error {yyerror(ERROR_PUNTOCOMA);}
                      | tipo FUN ID '(' error ')' BEGIN lista_sentencias END {yyerror(ERROR_CANTIDAD_PARAMETRO);}
                      | TAG error {yyerror(ERROR_PUNTOCOMA);}
                      ;
  
  tipo: UINTEGER 
      | SINGLE 
      | ID_STRUCT /* Para el caso del struct */ /*ID_STRUCT*/
      ;
  
  parametro: tipo ID {actualizarUso($2.sval, "Parametro"); actualizarTipo($2.sval, $1.sval);}
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
                      | conversion_explicita
                      ;
  
  asignacion: asignacion_simple
            | asignacion_multiple
            ;
  
  asignacion_simple: ID ASIGNACION expresion ';' {
                      $$.obj = new NodoCompuestoBinario(":=",new NodoConcreto($1.sval),(Nodo)$3.obj); // Lo creamos compuesto
                      System.out.println("ASIGNACION");
                   }
                   | ID ASIGNACION expresion error {yyerror(ERROR_PUNTOCOMA);}
                   | ID ASIGNACION error ';' {yyerror(ERROR_EXPRESION);}
                   | ID_STRUCT '.' ID ASIGNACION expresion ';'
                   | ID_STRUCT '.' ID ASIGNACION error ';' {yyerror(ERROR_EXPRESION);}
                   | ID_STRUCT '.' ID ASIGNACION expresion error {yyerror(ERROR_PUNTOCOMA);}
                   ;
  
  asignacion_multiple: lista_variables ASIGNACION lista_expresiones ';' {System.out.println("ASIGNACION MULTIPLE");
                                                                         $$.obj = new NodoCompuestoBinario(":=",(Nodo)$1.obj,(Nodo)$3.obj);}
                      | lista_variables ASIGNACION lista_expresiones error {yyerror(ERROR_PUNTOCOMA);}
                     ;
                  
  lista_variables: ID ',' ID /* Dos variables normales*/ {actualizarUso($1.sval, "Variable"); actualizarUso($3.sval, "Variable");
                                                          agregarGrupoVariable($1.sval, $3.sval);
                                                          $$.obj = new NodoCompuestoBinario(",",new NodoConcreto($1.sval),new NodoConcreto($3.sval));}
                  | ID_STRUCT '.' ID ',' ID_STRUCT '.' ID /* Dos variables struct*/
                  | lista_variables ',' ID  {actualizarUso($3.sval, "Variable");
                                            agregarGrupoVariable($3.sval);
                                            $$.obj = new NodoCompuestoBinario(",",(Nodo)$1.obj,new NodoConcreto($3.sval));}
                  | lista_variables ',' ID_STRUCT '.' ID
                  | ID ID {yyerror(ERROR_COMA);}                            
                  | ID_STRUCT '.' ID ID_STRUCT '.' ID {yyerror(ERROR_COMA);}
                  | lista_variables ID {yyerror(ERROR_COMA);}
                  | lista_variables ID_STRUCT '.' ID {yyerror(ERROR_COMA);}
                 ;
  
  lista_expresiones: expresion ',' expresion {$$.obj = new NodoCompuestoBinario(",",(Nodo)$1.obj,(Nodo)$3.obj);}
                   | lista_expresiones ',' expresion {$$.obj = new NodoCompuestoBinario(",",(Nodo)$1.obj,(Nodo)$3.obj);}
                   | expresion error expresion {yyerror(ERROR_COMA);}
                   //| lista_expresiones error expresion {yyerror(ERROR_COMA);}
                   | error ',' expresion {yyerror(ERROR_EXPRESION);}
                   | expresion ',' error {yyerror(ERROR_EXPRESION);}
                   ;
  
  
  retorno: RET '(' expresion ')' ';' {System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      $$.obj = new NodoCompuesto("RET",(Nodo)$3.obj,null);}
         | RET '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
         | RET '(' error ')' ';' {yyerror(ERROR_RETORNO);}
         ;
  
  expresion: expresion '+' termino {
                $$.obj = new NodoCompuestoBinario("+",(Nodo)$1.obj,(Nodo)$3.obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
         | expresion '-' termino {
            $$.obj = new NodoCompuestoBinario("-",(Nodo)$1.obj,(Nodo)$3.obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
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
              $$.obj = new NodoCompuestoBinario("*",(Nodo)$1.obj,(Nodo)$3.obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
       | termino '/' factor {
              $$.obj = new NodoCompuestoBinario("/",(Nodo)$1.obj,(Nodo)$3.obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
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
             $$.obj = new NodoConcreto($1.sval);  // Nodo para una variable
         }
        | ID_STRUCT '.' ID
        | UINTEGER_CONST {
            $$.obj = new NodoConcreto($1.sval);  // Nodo para constante UINTEGER
         }
       | SINGLE_CONST {
            $$.obj = new NodoConcreto($1.sval);  // Nodo para constante SINGLE
         }
       | HEXA_CONST {
            $$.obj = new NodoConcreto($1.sval);  // Nodo para constante HEXA
         }
        | invocacion_funcion
        | '-' ID 
        | '-' ID_STRUCT '.' ID
        | '-' SINGLE_CONST {actualizarSimbolo("-" + $2.sval,$2.sval);}
        | '-' error {yyerror(ERROR_NO_NEGATIVO);}
        ;
  
  invocacion_funcion: ID '(' expresion ')' ';' {$$.obj = new NodoCompuesto("INVOCACION_FUNCION_" + $1.sval,(Nodo)$3.obj,null);}
                    | ID '(' error ')' ';'{yyerror(ERROR_CANTIDAD_PARAMETRO);}
                    | ID '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
                    ;
  
  seleccion_if: IF '(' condicion ')' THEN bloque_sentencias END_IF ';' {
                  $$.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)$3.obj,null),new NodoCompuesto("CUERPO",(Nodo)$6.obj,null)); // No es necesario nodo de control "CUERPO" porque el camino es solo del THEN. MIRAR FILMINAS 14 DEL PAQUETE 08.3 (basado en eso para crear la estructura del arbol adecuado reutilizando clases por patron composite)
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
              | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';' {
                  $$.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)$3.obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)$6.obj,null),new NodoCompuesto("ELSE",(Nodo)$8.obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
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
  
  condicion: expresion comparador expresion {$$.obj = new NodoCompuestoBinario($2.sval,(Nodo)$1.obj,(Nodo)$3.obj);}
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
  
  imprimir: OUTF '(' expresion ')' ';' {$$.obj = new NodoCompuesto("OUTF",(Nodo)$3.obj,null);}
          | OUTF '(' CADENA ')' ';' {$$.obj = new NodoCompuesto("OUTF",new NodoConcreto($3.sval),null);}
          | OUTF '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
          | OUTF '(' CADENA ')' error {yyerror(ERROR_PUNTOCOMA);}
          | OUTF '(' ')' ';' {yyerror(ERROR_CANTIDAD_PARAMETRO);}
          | OUTF '(' error ')' ';' {yyerror(ERROR_PARAMETRO);}
          ;
  
  repeat_until: REPEAT bloque_sentencias UNTIL '(' condicion ')' ';' {
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    $$.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)$2.obj,null),new NodoCompuesto("CONDICION",(Nodo)$5.obj,null));
                                                                    } 
              | REPEAT bloque_sentencias '(' condicion ')' ';' {yyerror(ERROR_UNTIL);}
              | REPEAT bloque_sentencias UNTIL '(' condicion ')' error {yyerror(ERROR_PUNTOCOMA);}
              | REPEAT bloque_sentencias UNTIL  condicion ')' ';' {yyerror(ERROR_PARENTESIS);}
              | REPEAT bloque_sentencias UNTIL '(' condicion ';' {yyerror(ERROR_PARENTESIS);}
              | REPEAT bloque_sentencias UNTIL  condicion ';' {yyerror(ERROR_PARENTESIS);}
              | REPEAT error UNTIL '(' condicion ')' ';' {yyerror(ERROR_CUERPO);}
              ;
  
  struct: TYPEDEF bloque_struct_multiple ID {System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso($3.sval, "Struct");} /* HACER ACCION QUE TOME LA POSCION DE ID Y LE CAMBIE EL TIPO EN LA TALBA A ID_STRUCT*/
        | TYPEDEF bloque_struct_simple ID  {System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso($3.sval, "Struct");}
        | TYPEDEF bloque_struct_multiple error  {yyerror(ERROR_ID_STRUCT);}
        | TYPEDEF bloque_struct_simple error {yyerror(ERROR_ID_STRUCT);}
        ;
  
  bloque_struct_multiple: STRUCT '<' lista_tipos '>' BEGIN lista_variables END
                        | '<' lista_tipos '>' BEGIN lista_variables END {yyerror(ERROR_STRUCT);}
                        | STRUCT lista_tipos '>' BEGIN lista_variables END {yyerror(ERROR_TIPO_STRUCT);}
                        | STRUCT '<' lista_tipos BEGIN lista_variables END {yyerror(ERROR_TIPO_STRUCT);}
                        ;
  
  bloque_struct_simple: STRUCT '<' tipo '>' BEGIN ID END {actualizarUso($6.sval, "Variable");}
                      | '<' tipo '>' BEGIN ID END {yyerror(ERROR_STRUCT);}
                      | tipo '>' BEGIN ID END {yyerror(ERROR_TIPO_STRUCT);}
                      | '<' tipo BEGIN ID END {yyerror(ERROR_TIPO_STRUCT);}
                      ;
  
  
  
  lista_tipos: lista_tipos ',' tipo
             | tipo ',' tipo
             ;
  
  goto: GOTO TAG ';' {System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea());
                      $$.obj = new NodoCompuesto("GOTO",new NodoConcreto($2.sval),null);}
      | GOTO TAG error {yyerror(ERROR_PUNTOCOMA);}
      | GOTO error ';' {yyerror(ERROR_ETIQUETA);}
      ;
  
  conversion_explicita: TOS '(' expresion ')' ';' {$$.obj = new NodoCompuesto("TOS",(Nodo)$3.obj,null);} // ¿CAMBIAR EXPRESION POR UINTEGER PARA NO ROMPER TODO CON STRUCT?
                      | TOS '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
                      | TOS '(' error ')' ';' {yyerror(ERROR_EXPRESION);}
                      ;
  
  %%
  
    private static final String ERROR_BEGIN = "se espera un delimitador (BEGIN)";
    private static final String ERROR_CANTIDAD_PARAMETRO = "cantidad de parametros incorrectos";
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
    private static final String ERROR_ID_STRUCT = "ERROR en la declaracion del nombre de la estructura STRUCT";
    private static final String ERROR_TIPO_STRUCT = "falta '<' o '>' al declarar el tipo";
    
    static String grupoVariable = "";

    static AnalizadorLexico lex = null;
    static TablaSimbolos ts = TablaSimbolos.getInstance();
  
    void main(String filePath) {
        // Código principal del compilador
        System.out.println("Iniciando análisis sintáctico...");
        lex = AnalizadorLexico.getInstance(filePath);
        run();
        System.out.println("Fin del análisis sintáctico.");
    }
  
    void yyerror(String s) {
        if (!s.equalsIgnoreCase("syntax error"))
            System.err.println("SINTACTICO::::: Error: " + s + " en la linea "+lex.getNumeroLinea());
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

    void agregarGrupoVariable(String var1, String var2) {
        grupoVariable = var1 + "," + var2;
    }

    void agregarGrupoVariable(String var1) {
        grupoVariable = grupoVariable + "," + var1;
    }

    void actualizarTipoDelGrupo(String tipo) {
        String[] variables = grupoVariable.split(",");
        for (String variable : variables) {
            actualizarTipo(variable, tipo);
        }
        grupoVariable = "";
    }