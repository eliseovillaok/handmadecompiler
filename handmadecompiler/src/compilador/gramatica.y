%{
  package compilador;
  import java.util.*;
  import java.io.*;
%}


%token ID BEGIN END IF TOS THEN ELSE END_IF OUTF TYPEDEF FUN RET REPEAT UNTIL STRUCT GOTO SINGLE UINTEGER TAG UINTEGER_CONST SINGLE_CONST HEXA_CONST CADENA MENOR_IGUAL ASIGNACION MAYOR_IGUAL DISTINTO 
%right ASIGNACION
%start programa
%%

programa: ID BEGIN lista_sentencias END
        ;

lista_sentencias: sentencia
                | lista_sentencias sentencia
                ;

sentencia: sentencia_declarativa
         | sentencia_ejecutable
         ;

sentencia_declarativa: tipo lista_variables ';'
		     | tipo ID ';'
		     | ID ';' { /* Aquí se verifica que la variable esté declarada */ }
                     | lista_variables ';' { /* Aquí se verifica que la variable esté declarada */ }
                     | tipo FUN ID '(' parametro ')' BEGIN lista_sentencias END
                     | struct
                     ;

tipo: UINTEGER
    | SINGLE
    | ID  /* Para el caso del struct */
    ;

parametro: tipo ID
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

asignacion_simple: ID ASIGNACION expresion ';'
                 | ID '.' ID ASIGNACION expresion ';'
                 ;

asignacion_multiple: lista_variables ASIGNACION lista_expresiones ';'
                   ;
                
lista_variables: ID ',' ID /* Dos variables normales*/
	       | ID '.' ID ',' ID '.' ID /* Dos variables struct*/
	       | lista_variables ',' ID 
               | lista_variables ',' ID '.' ID
               ;

lista_expresiones: expresion ',' expresion
	         | lista_expresiones ',' expresion
                 ;


retorno: RET '(' expresion ')' ';'
       ;

expresion: expresion '+' termino {System.out.println("SUMA");}
         | expresion '-' termino {System.out.println("RESTA");}
         | termino
         ;

termino: termino '*' factor {System.out.println("MULTIPLICACIÓN");}
       | termino '/' factor {System.out.println("DIVISION");}
       | factor
       ;

factor: ID 
      | ID '.' ID
      | UINTEGER_CONST 
      | SINGLE_CONST 
      | HEXA_CONST 
      | invocacion_funcion
      | '-' SINGLE_CONST { actualizarSimbolo($2); } /* SINGLE negativo (actualizo TS) */
      ;

invocacion_funcion: ID '(' expresion ')'
                  ;

seleccion_if: IF '(' condicion ')' THEN bloque_sentencias END_IF ';'
            | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'
            ;

bloque_sentencias: BEGIN lista_sentencias_ejecutables END
                 | sentencia_ejecutable
                 ;

lista_sentencias_ejecutables: lista_sentencias_ejecutables sentencia_ejecutable
                            | sentencia_ejecutable
                            ;

condicion: expresion comparador expresion
         ;

comparador: '='
          | DISTINTO
          | '<'
          | '>'
          | MENOR_IGUAL
          | MAYOR_IGUAL
          ;

imprimir: OUTF '(' expresion ')' ';'
        | OUTF '(' CADENA ')' ';'
        ;

repeat_until: REPEAT bloque_sentencias UNTIL '(' condicion ')' ';'
            ;

struct: TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID ';'
      | TYPEDEF STRUCT '<' tipo '>' '{' ID '}' ID ';'
      ;

lista_tipos: lista_tipos ',' tipo
           | tipo ',' tipo
           ;

goto: GOTO TAG ';'
    ;

conversion_explicita: TOS '(' expresion ')' ';'
                    ;

%%

static AnalizadorLexico lex = null;
static Sintactico sintactico = null;
static Parser par = null;

void main(String filePath) {
    // Código principal del compilador
    System.out.println("Iniciando análisis sintáctico...");
    lex = AnalizadorLexico.getInstance(filePath);
    //sintactico = Sintactico.getInstance();
    par = new Parser(false); //DEJO EN TRUE PARA HACER PRUEBAS Y DEBUGEAR MAS FACIL
    par.run();
    System.out.println("Fin del análisis sintáctico.");
}

void yyerror(String s) {
    System.err.println("Error: " + s);
}

int yylex(){
  /*ArrayList<Integer> listaDeTokens = sintactico.ejecutar(lex);
  int salida = 0;
  while (!listaDeTokens.isEmpty()) {
    return listaDeTokens.remove(0);
  }*/
  return lex.getProximoToken().getId();
}

void actualizarSimbolo(int valor) {
    TablaSimbolos ts = TablaSimbolos.getInstance();
    ts.actualizarSimbolo(valor);
}

