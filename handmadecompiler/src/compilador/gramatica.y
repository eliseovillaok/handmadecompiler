%{
  package compilador;
  import java.util.*;
  import java.io.*;
%}

%token ID BEGIN END IF TOS THEN ELSE END_IF OUTF TYPEDEF FUN RET REPEAT UNTIL STRUCT GOTO SINGLE UINTEGER TAG UINTEGER_CONST SINGLE_CONST HEXA_CONST CADENA MENOR_IGUAL ASIGNACION MAYOR_IGUAL DISTINTO 
%right ASIGNACION
%start programa
%%

programa: ID BEGIN lista_sentencias END  { System.out.println("Programa reconocido correctamente"); }
       | ID error lista_sentencias END  { yyerror(ERROR_BEGIN); }
       | ID BEGIN error  { yyerror(ERROR_END); }
       | error BEGIN lista_sentencias END  { yyerror(ERROR_NOMBRE_PROGRAMA); }
       ;


lista_sentencias: sentencia
                | lista_sentencias sentencia
                ;

sentencia: sentencia_declarativa
         | sentencia_ejecutable
         ;

sentencia_declarativa: tipo lista_variables ';'
					| tipo lista_variables error {yyerror(ERROR_PUNTOCOMA);}
		     		| tipo ID ';'
		     		| tipo ID error {yyerror(ERROR_PUNTOCOMA);}
		     		| ID ';'
		     		| ID error {yyerror(ERROR_PUNTOCOMA);}
             		| lista_variables ';' { /* Aquí se verifica que la variable esté declarada */ }
             		| lista_variables error {yyerror(ERROR_PUNTOCOMA);}
                    | tipo FUN ID '(' parametro ')' BEGIN lista_sentencias END {System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());}
                    | tipo FUN error '(' parametro ')' BEGIN lista_sentencias END {yyerror(ERROR_NOMBRE_FUNCION);}
                    | tipo FUN ID '(' parametro ')' BEGIN error END  {yyerror(ERROR_RET);}
                    | struct ';'
                    | struct error {yyerror(ERROR_PUNTOCOMA);}
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
				 | ID ASIGNACION expresion error {yyerror(ERROR_PUNTOCOMA);}
                 | ID '.' ID ASIGNACION expresion ';'
                 | ID '.' ID ASIGNACION expresion error {yyerror(ERROR_PUNTOCOMA);}
                 ;

asignacion_multiple: lista_variables ASIGNACION lista_expresiones ';'
					| lista_variables ASIGNACION lista_expresiones error {yyerror(ERROR_PUNTOCOMA);}
                   ;
                
lista_variables: ID ',' ID /* Dos variables normales*/
	       | ID '.' ID ',' ID '.' ID /* Dos variables struct*/
	       | lista_variables ',' ID 
               | lista_variables ',' ID '.' ID
               ;

lista_expresiones: expresion ',' expresion
	         | lista_expresiones ',' expresion
                 ;


retorno: RET '(' expresion ')' ';' {System.out.println("RETORNO. Linea "+lex.getNumeroLinea());}
       | RET '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
       ;

expresion: expresion '+' termino {System.out.println("SUMA. Linea "+lex.getNumeroLinea());}
         | expresion '-' termino {System.out.println("RESTA. Linea "+lex.getNumeroLinea());}
         | termino
         ;

termino: termino '*' factor {System.out.println("MULTIPLICACIÓN. Linea "+lex.getNumeroLinea());}
       | termino '/' factor {System.out.println("DIVISION. Linea "+lex.getNumeroLinea());}
       | factor
       ;

factor: ID 
      | ID '.' ID
      | UINTEGER_CONST 
      | SINGLE_CONST 
      | HEXA_CONST 
      | invocacion_funcion
      | '-' SINGLE_CONST { actualizarSimbolo($2.sval); } /* SINGLE negativo (actualizo TS) */
      ;

invocacion_funcion: ID '(' expresion ')'
                  ;

seleccion_if: IF '(' condicion ')' THEN bloque_sentencias END_IF ';' {System.out.println("DECLARACION DE IF. Linea "+lex.getNumeroLinea());}
            | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';' {System.out.println("DECLARACION DE IF-ELSE. Linea "+lex.getNumeroLinea());}
            | IF '(' condicion ')' THEN bloque_sentencias END_IF error {yyerror(ERROR_PUNTOCOMA);}
            | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF error {yyerror(ERROR_PUNTOCOMA);}
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
        | OUTF '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
        | OUTF '(' CADENA ')' error {yyerror(ERROR_PUNTOCOMA);}
        ;

repeat_until: REPEAT bloque_sentencias UNTIL '(' condicion ')' ';' {System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());}
            | REPEAT bloque_sentencias UNTIL '(' condicion ')' error {yyerror(ERROR_PUNTOCOMA);}
            ;

struct: TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID {System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea());}
      | TYPEDEF STRUCT '<' tipo '>' '{' ID '}' ID {System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea());}
      ;

lista_tipos: lista_tipos ',' tipo
           | tipo ',' tipo
           ;

goto: GOTO TAG ';' {System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea());}
    | GOTO TAG error {yyerror(ERROR_PUNTOCOMA);}
    ;

conversion_explicita: TOS '(' expresion ')' ';'
					| TOS '(' expresion ')' error {yyerror(ERROR_PUNTOCOMA);}
                    ;

%%

private static final String ERROR_NOMBRE_PROGRAMA = "se espera un nombre de programa";
private static final String ERROR_BEGIN = "se espera un delimitador (BEGIN)";
private static final String ERROR_END = "se espera un delimitador (END)";
private static final String ERROR_PUNTOCOMA = "falta un ';' al final";
private static final String ERROR_NOMBRE_FUNCION = "se espera un nombre de funcion";
private static final String ERROR_RET = "se espera un retorno (RET)";
private static final String ERROR_COMA = "falta una ',' luego de la variable";

static AnalizadorLexico lex = null;

void main(String filePath) {
    // Código principal del compilador
    System.out.println("Iniciando análisis sintáctico...");
    lex = AnalizadorLexico.getInstance(filePath);
    run();
    System.out.println("Fin del análisis sintáctico.");
}

void yyerror(String s) {
	if (!s.equalsIgnoreCase("syntax error"))
    	System.err.println("Error: " + s + " en la linea "+lex.getNumeroLinea());
}

int yylex(){
  return lex.getProximoToken().getId();
}

void actualizarSimbolo(String valor) {
    TablaSimbolos ts = TablaSimbolos.getInstance();
    ts.actualizarSimbolo(valor);
}

