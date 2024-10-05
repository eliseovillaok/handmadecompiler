#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "gramatica.y"
  package compilador;
  import java.util.*;
  import java.io.*;
#line 10 "y.tab.c"
#define ID 257
#define BEGIN 258
#define END 259
#define IF 260
#define TOS 261
#define THEN 262
#define ELSE 263
#define END_IF 264
#define OUTF 265
#define TYPEDEF 266
#define FUN 267
#define RET 268
#define REPEAT 269
#define UNTIL 270
#define STRUCT 271
#define GOTO 272
#define SINGLE 273
#define UINTEGER 274
#define TAG 275
#define UINTEGER_CONST 276
#define SINGLE_CONST 277
#define HEXA_CONST 278
#define CADENA 279
#define MENOR_IGUAL 280
#define ASIGNACION 281
#define MAYOR_IGUAL 282
#define DISTINTO 283
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    1,    1,    2,    2,    3,    3,    3,    3,    3,
    3,    5,    5,    5,    7,    4,    4,    4,    4,    4,
    4,    4,    4,    9,    9,   17,   17,   18,    6,    6,
    6,    6,   20,   20,   10,   19,   19,   19,   21,   21,
   21,   22,   22,   22,   22,   22,   22,   11,   12,   12,
   24,   24,   25,   25,   23,   26,   26,   26,   26,   26,
   26,   13,   13,   14,    8,    8,   27,   27,   15,   16,
};
short yylen[] = {                                         2,
    4,    1,    2,    1,    1,    3,    3,    2,    2,    9,
    1,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    4,    6,    4,    3,    7,
    3,    5,    3,    3,    5,    3,    3,    1,    3,    3,
    1,    1,    3,    1,    1,    1,    1,    4,    8,   10,
    3,    1,    2,    1,    3,    1,    1,    1,    1,    1,
    1,    5,    5,    7,   10,   10,    3,    3,    3,    5,
};
short yydefred[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   13,   12,    0,    2,    4,    5,    0,    0,   11,
   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,
    0,    8,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,    0,    0,    0,    1,    3,    0,    0,
    0,    0,    9,    0,    0,   44,   45,   46,   47,    0,
    0,   41,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,   54,    0,    0,   69,    7,    0,    0,    6,
    0,    0,    0,    0,   26,    0,    0,    0,    0,   48,
    0,    0,   60,   61,   57,   56,   58,   59,    0,    0,
    0,    0,    0,   14,    0,    0,    0,   51,   53,    0,
    0,    0,    0,   28,    0,    0,   43,    0,    0,   39,
   40,    0,    0,    0,    0,   70,   63,   62,    0,    0,
    0,    0,   35,    0,    0,    0,    0,    0,   32,   27,
    0,    0,   68,    0,   67,    0,    0,   15,    0,   30,
    0,    0,    0,    0,    0,   64,    0,    0,   49,    0,
    0,    0,    0,    0,    0,   10,   50,   66,   65,
};
short yydgoto[] = {                                       2,
   14,   15,   16,   17,   18,   44,  136,   20,   21,   22,
   59,   24,   25,   26,   27,   28,   29,   30,   66,   82,
   61,   62,   67,   45,   74,   99,  106,
};
short yysindex[] = {                                   -230,
 -218,    0, -130,   -3,   39,   66,  114, -195,  138,  -86,
 -193,    0,    0, -149,    0,    0,    0, -158,   -5,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -183,    0, -183, -157,  -70, -183, -183, -187,  144, -183,
   -2,  -84,    0,  -33,  -48,  166,    0,    0,   42,  -30,
   78, -183,    0,  -29,   31,    0,    0,    0,    0,   59,
   86,    0,  149,  -28,    0,    2,  188,  150,  189,  175,
 -190,  178,    0,  -58,  191,    0,    0,  -25,  193,    0,
  153,  101,  190,  -23,    0, -183, -183, -183, -183,    0,
 -183,  -20,    0,    0,    0,    0,    0,    0, -183,  -27,
  179,  181,  184,    0,   16,   36,  186,    0,    0, -183,
  206, -190, -183,    0, -183,   -1,    0,   86,   86,    0,
    0,   62,  208,  170,  -86,    0,    0,    0, -190,  132,
 -190,  134,    0,  217,    3,  218,  170,  170,    0,    0,
    4, -123,    0,    5,    0,    6,  205,    0,    8,    0,
  -86,  209,  140,  180,  -22,    0, -130,   11,    0,   10,
   12, -104,  213,  221,  222,    0,    0,    0,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0, -108,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -41,    0,    0,    0,    0,    0,
  -36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -31,   -9,    0,
    0,    0,    0,  236,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  107,  108,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
  126,    1,    0,    7,  -16,   43,    0,    0,    0,    0,
   55,    0,    0,    0,    0,    0,    0,    0,   35,    0,
   60,  120,  176,  -66,    0,    0,    0,
};
#define YYTABLESIZE 286
short yytable[] = {                                      42,
   42,   42,   42,   42,   38,   42,   38,   38,   38,   36,
   54,   36,   36,   36,   48,   92,   43,   42,   42,   42,
   42,   54,   38,   38,   38,   38,    1,   36,   36,   36,
   36,   37,   31,   37,   37,   37,   33,   33,   54,    3,
   35,   35,   34,   34,   86,   19,   87,   31,   73,   37,
   37,   37,   37,   53,  105,   32,   19,   23,  142,  129,
   51,   97,   96,   98,   23,   60,  104,   63,   23,   55,
   33,   68,   70,   55,   72,   39,   84,  130,   36,  131,
  109,   46,   12,   13,  158,   35,   81,   78,   56,   57,
   58,   69,   56,   57,   58,  135,   23,  132,   49,   64,
   77,   86,  161,   87,   86,   37,   87,    4,   50,   47,
    5,    6,  143,   31,  145,    7,    8,   85,    9,   10,
  140,   54,   11,   12,   13,  122,    4,   88,   23,    5,
    6,   43,   89,  124,    7,    8,   80,    9,   10,  151,
  152,   11,   12,   13,  115,  118,  119,  137,   14,  138,
   33,   34,    4,   38,  166,    5,    6,   43,   14,  114,
    7,    8,   48,    9,   10,   33,   34,   11,   12,   13,
   41,   42,   41,    5,    6,    5,    6,   40,    7,   23,
    7,    9,   10,    9,   10,   11,   65,   11,  155,   90,
  101,   86,   86,   87,   87,   86,  113,   87,   41,   19,
  108,    5,    6,   71,   19,   23,    7,  120,  121,    9,
   10,   23,   86,   11,   87,  103,   23,   86,  107,   87,
   86,   75,   87,   35,   76,   78,   79,   83,  100,  102,
  110,  111,  112,  117,  125,  116,  123,  126,   42,  127,
   42,   42,  128,   38,  133,   38,   38,   52,   36,   92,
   36,   36,   91,  141,  144,  139,  146,  147,  149,  148,
  150,  153,  154,  156,  160,  157,  164,  159,  165,   31,
   37,  167,   37,   37,  163,   52,   55,   31,   31,  168,
  169,   93,  162,   94,   95,  134,
};
short yycheck[] = {                                      41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   44,   43,   44,   45,   14,   44,   10,   59,   60,   61,
   62,   44,   59,   60,   61,   62,  257,   59,   60,   61,
   62,   41,   44,   43,   44,   45,   40,   40,   44,  258,
   44,   44,   46,   46,   43,    3,   45,   59,   42,   59,
   60,   61,   62,   59,   71,   59,   14,    3,  125,   44,
   18,   60,   61,   62,   10,   31,  257,   33,   14,  257,
   40,   37,   38,  257,   40,  271,   46,   62,   40,   44,
   74,  275,  273,  274,  151,   44,   52,   46,  276,  277,
  278,  279,  276,  277,  278,  112,   42,   62,  257,  257,
   59,   43,  125,   45,   43,   40,   45,  257,  267,  259,
  260,  261,  129,  125,  131,  265,  266,   59,  268,  269,
   59,   44,  272,  273,  274,   91,  257,   42,   74,  260,
  261,  125,   47,   99,  265,  266,   59,  268,  269,  263,
  264,  272,  273,  274,   44,   86,   87,  113,  257,  115,
   44,   44,  257,   40,  259,  260,  261,  151,  267,   59,
  265,  266,  162,  268,  269,   59,   59,  272,  273,  274,
  257,  258,  257,  260,  261,  260,  261,   40,  265,  125,
  265,  268,  269,  268,  269,  272,  257,  272,  146,   41,
   41,   43,   43,   45,   45,   43,   44,   45,  257,  157,
  259,  260,  261,   60,  162,  151,  265,   88,   89,  268,
  269,  157,   43,  272,   45,   41,  162,   43,   41,   45,
   43,  270,   45,   44,   59,   46,  257,  257,   41,   41,
   40,  257,   40,  257,  262,   46,  257,   59,  280,   59,
  282,  283,   59,  280,   59,  282,  283,  281,  280,   44,
  282,  283,  281,   46,  123,  257,  123,   41,   41,  257,
  257,  257,  257,   59,  125,  258,  257,   59,  257,  281,
  280,   59,  282,  283,  264,  281,   41,  281,  281,   59,
   59,  280,  157,  282,  283,  110,
};
#define YYFINAL 2
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 283
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'","'.'","'/'",0,0,0,0,0,0,0,0,0,0,
0,"';'","'<'","'='","'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,"'}'",
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,"ID","BEGIN","END","IF","TOS","THEN","ELSE","END_IF",
"OUTF","TYPEDEF","FUN","RET","REPEAT","UNTIL","STRUCT","GOTO","SINGLE",
"UINTEGER","TAG","UINTEGER_CONST","SINGLE_CONST","HEXA_CONST","CADENA",
"MENOR_IGUAL","ASIGNACION","MAYOR_IGUAL","DISTINTO",
};
char *yyrule[] = {
"$accept : programa",
"programa : ID BEGIN lista_sentencias END",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_variables ';'",
"sentencia_declarativa : tipo ID ';'",
"sentencia_declarativa : ID ';'",
"sentencia_declarativa : lista_variables ';'",
"sentencia_declarativa : tipo FUN ID '(' parametro ')' BEGIN lista_sentencias END",
"sentencia_declarativa : struct",
"tipo : UINTEGER",
"tipo : SINGLE",
"tipo : ID",
"parametro : tipo ID",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : retorno",
"sentencia_ejecutable : invocacion_funcion",
"sentencia_ejecutable : seleccion_if",
"sentencia_ejecutable : imprimir",
"sentencia_ejecutable : repeat_until",
"sentencia_ejecutable : goto",
"sentencia_ejecutable : conversion_explicita",
"asignacion : asignacion_simple",
"asignacion : asignacion_multiple",
"asignacion_simple : ID ASIGNACION expresion ';'",
"asignacion_simple : ID '.' ID ASIGNACION expresion ';'",
"asignacion_multiple : lista_variables ASIGNACION lista_expresiones ';'",
"lista_variables : ID ',' ID",
"lista_variables : ID '.' ID ',' ID '.' ID",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ',' ID '.' ID",
"lista_expresiones : expresion ',' expresion",
"lista_expresiones : lista_expresiones ',' expresion",
"retorno : RET '(' expresion ')' ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : ID '.' ID",
"factor : UINTEGER_CONST",
"factor : SINGLE_CONST",
"factor : HEXA_CONST",
"factor : invocacion_funcion",
"invocacion_funcion : ID '(' expresion ')'",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias END_IF ';'",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'",
"bloque_sentencias : BEGIN lista_sentencias_ejecutables END",
"bloque_sentencias : sentencia_ejecutable",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"condicion : expresion comparador expresion",
"comparador : '='",
"comparador : DISTINTO",
"comparador : '<'",
"comparador : '>'",
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"imprimir : OUTF '(' expresion ')' ';'",
"imprimir : OUTF '(' CADENA ')' ';'",
"repeat_until : REPEAT bloque_sentencias UNTIL '(' condicion ')' ';'",
"struct : TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID ';'",
"struct : TYPEDEF STRUCT '<' tipo '>' '{' ID '}' ID ';'",
"lista_tipos : lista_tipos ',' tipo",
"lista_tipos : tipo ',' tipo",
"goto : GOTO TAG ';'",
"conversion_explicita : TOS '(' expresion ')' ';'",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 141 "gramatica.y"

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

#line 337 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 8:
#line 26 "gramatica.y"
{ /* Aquí se verifica que la variable esté declarada */ }
break;
case 9:
#line 27 "gramatica.y"
{ /* Aquí se verifica que la variable esté declarada */ }
break;
case 36:
#line 75 "gramatica.y"
{System.out.println("SUMA");}
break;
case 37:
#line 76 "gramatica.y"
{System.out.println("RESTA");}
break;
case 39:
#line 80 "gramatica.y"
{System.out.println("MULTIPLICACIÓN");}
break;
case 40:
#line 81 "gramatica.y"
{System.out.println("DIVISION");}
break;
case 42:
#line 85 "gramatica.y"
{System.out.println(((Token)yyvsp[0].obj).getLexema());}
break;
#line 505 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
