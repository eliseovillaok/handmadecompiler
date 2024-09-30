%{
  import java.util.*;
  // Clase auxiliar para manejar pares de tokens
  /*class Par<T1, T2> { // Preguntar
    public T1 first;
    public T2 second;
    public Par(T1 first, T2 second) {
      this.first = first;
      this.second = second;
    }
  }*/
%}
/*
%union { // Preguntar
  int ival;
  double dval;
  String sval;
  Par<Integer, String> par;
}*/

%token BEGIN END FUN TYPEDEF STRUCT REPEAT UNTIL OUTF IF THEN ELSE END_IF RET GOTO TAG TOS ID CONSTANTE CADENA UINTEGER SINGLE

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

asignacion: ID ":=" expresion ';'
          | ID '.' ID ":=" expresion ';'
          | ID ":=" ID ';'
          | lista_variables ":=" lista_expresiones ';'
          ;

lista_variables: ID
               | lista_variables ',' ID
               ;

lista_expresiones: lista_expresiones ',' expresion
                 | expresion
                 ;

retorno: RET '(' expresion ')' ';'
       ;

expresion: expresion '+' termino
         | expresion '-' termino
         | termino
         ;

termino: termino '*' factor
       | termino '/' factor
       | factor
       ;

factor: ID
      | ID '.' ID
      | CONSTANTE
      | invocacion_funcion
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
          | '!='
          | '<'
          | '>'
          | '<='
          | '>='
          ;

imprimir: OUTF '(' expresion ')' ';'
        | OUTF '(' CADENA ')' ';'
        ;

repeat_until: REPEAT bloque_sentencias UNTIL '(' condicion ')' ';'
            ;

struct: TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID ';'
      ;

lista_tipos: lista_tipos ',' tipo
           | tipo
           ;

goto: GOTO TAG ';'
    ;

conversion_explicita: TOS '(' expresion ')' ';'
                    ;

%%

int main(String[] args) {
    // Código principal del compilador
    System.out.println("Iniciando análisis sintáctico...");
    yyparse();
}

void yyerror(String s) {
    System.err.println("Error: " + s);
}

