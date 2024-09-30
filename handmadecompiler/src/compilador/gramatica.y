%{
    // Importamos las clases necesarias de Java
    import java.io.*;
    import java.util.*;

    // Definimos el Analizador Léxico
    Yylex lexer;

    // Método main para ejecutar el parser
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser(new Yylex(System.in));
        parser.yyparse();
    }

    // Constructor que recibe el Analizador Léxico
    public Parser(Yylex lexer) {
        this.lexer = lexer;
    }

    // Método para invocar el analizador léxico
    private int yylex() {
        try {
            return lexer.yylex();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Método para el manejo de errores
    public void yyerror(String error) {
        System.err.println("Error: " + error);
    }
%}

// Declaración de tokens
%token BEGIN END IF THEN ELSE END_IF REPEAT UNTIL UP DOWN RET OUTF TOS TOD
%token UINTEGER LONGINT ULONGINT SINGLE DOUBLE ID
%token '+' '-' '*' '/' ASSIGN ';' ',' '(' ')'

%%

// Definición de la gramática

programa:
    ID BEGIN lista_sentencias END
    ;

lista_sentencias:
    lista_sentencias sentencia
    | /* vacío */
    ;

sentencia:
    declaracion
    | asignacion
    | seleccion_if
    | iteracion_repeat
    | salida
    | retorno
    ;

declaracion:
    tipo lista_variables ';'
    | tipo FUN ID '(' parametro ')' BEGIN lista_sentencias END
    ;

lista_variables:
    lista_variables ',' ID
    | ID
    ;

parametro:
    tipo ID
    ;

tipo:
    INTEGER | UINTEGER | LONGINT | ULONGINT | SINGLE | DOUBLE
    ;

asignacion:
    ID ASSIGN expresion ';'
    | lista_variables ASSIGN lista_expresiones ';'
    ;

lista_expresiones:
    lista_expresiones ',' expresion
    | expresion
    ;

expresion:
    expresion '+' termino
    | expresion '-' termino
    | termino
    ;

termino:
    termino '*' factor
    | termino '/' factor
    | factor
    ;

factor:
    ID
    | numero
    | '(' expresion ')'
    ;

numero:
    UINTEGER | SINGLE
    ;

seleccion_if:
    IF '(' condicion ')' THEN bloque_sentencias opcion_else END_IF ';'
    ;

opcion_else:
    ELSE bloque_sentencias
    | /* vacío */
    ;

condicion:
    expresion comparacion expresion
    ;

comparacion:
    '<' | '>' | '=' | "<=" | ">=" | "!="
    ;

bloque_sentencias:
    BEGIN lista_sentencias END
    | sentencia
    ;

iteracion_repeat:
    REPEAT bloque_sentencias UNTIL '(' condicion ')' ';'
    ;

UP_DOWN:
    UP | DOWN
    ;

salida:
    OUTF '(' expresion ')' ';'
    ;

retorno:
    RET '(' expresion ')' ';'
    ;

%%

/* Sección Java */

/**
 * El método principal para lanzar el analizador sintáctico se encuentra en la sección de código.
 * El manejo de errores y las llamadas al analizador léxico también están definidos.
 */

