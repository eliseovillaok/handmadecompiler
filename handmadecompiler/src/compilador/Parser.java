//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
  import java.util.*;
  /* Clase auxiliar para manejar pares de tokens*/
  /*class Par<T1, T2> { // Preguntar
    public T1 first;
    public T2 second;
    public Par(T1 first, T2 second) {
      this.first = first;
      this.second = second;
    }
  }*/
//#line 28 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short BEGIN=257;
public final static short END=258;
public final static short FUN=259;
public final static short TYPEDEF=260;
public final static short STRUCT=261;
public final static short REPEAT=262;
public final static short UNTIL=263;
public final static short OUTF=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short RET=269;
public final static short GOTO=270;
public final static short TAG=271;
public final static short TOS=272;
public final static short ID=273;
public final static short CONSTANTE=274;
public final static short CADENA=275;
public final static short UINTEGER=276;
public final static short SINGLE=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    3,    3,    3,    5,
    5,    5,    7,    4,    4,    4,    4,    4,    4,    4,
    4,    9,    9,    9,    9,    6,    6,   18,   18,   10,
   17,   17,   17,   19,   19,   19,   20,   20,   20,   20,
   11,   12,   12,   22,   22,   23,   23,   21,   24,   24,
   24,   24,   24,   24,   13,   13,   14,    8,   25,   25,
   15,   16,
};
final static short yylen[] = {                            2,
    4,    1,    2,    1,    1,    3,    2,    9,    1,    1,
    1,    1,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    6,    4,    4,    1,    3,    3,    1,    5,
    3,    3,    1,    3,    3,    1,    1,    3,    1,    1,
    4,    8,   10,    3,    1,    2,    1,    3,    1,    1,
    1,    1,    1,    1,    5,    5,    7,   10,    3,    1,
    3,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   10,   11,    0,    2,    4,    5,    0,    0,    9,
   14,   15,   16,   17,   18,   19,   20,   21,    0,    0,
    0,   45,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    3,    0,   26,    0,    7,    0,    0,
    0,   47,    0,    0,    0,   39,    0,   40,    0,    0,
   36,    0,    0,    0,   61,    0,    0,    0,    0,    0,
    0,    6,    0,    0,   27,   12,   60,    0,   44,   46,
    0,    0,    0,    0,    0,    0,    0,    0,   49,   50,
   51,   52,   53,   54,    0,    0,    0,    0,   41,   24,
   22,    0,    0,   25,    0,    0,    0,    0,   38,   56,
   55,    0,    0,   34,   35,    0,    0,   30,   62,    0,
    0,    0,    0,   59,    0,    0,    0,   23,   13,    0,
    0,   57,    0,    0,    0,    0,    0,   42,    0,    0,
    0,    8,   58,   43,
};
final static short yydgoto[] = {                          2,
   14,   15,   16,   17,   18,   33,  122,   20,   21,   22,
   58,   24,   25,   26,   27,   28,   62,   74,   60,   61,
   63,   34,   53,   95,   78,
};
final static short yysindex[] = {                      -232,
 -178,    0,  -65, -151, -144,   74,   77,   91, -147,   93,
  -24,    0,    0, -161,    0,    0,    0, -179,  -17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   78, -111,
  -24,    0,  -27, -118, -168, -184, -184,   89, -184, -184,
 -130, -133,    0,    0, -117,    0,   24,    0, -184, -113,
 -154,    0, -123,  143,   38,    0,  144,    0,  135,   44,
    0,   -6,  147,  174,    0,  179,  180,   17,   15,  -94,
  151,    0,   87,   33,    0,    0,    0,    2,    0,    0,
 -184,  -81,  137,  139, -184, -184, -184, -184,    0,    0,
    0,    0,    0,    0, -184,  -64,  142,  150,    0,    0,
    0, -184, -154,    0, -184, -154,   80,  165,    0,    0,
    0,   44,   44,    0,    0,   87, -144,    0,    0,   16,
  -63,  185,   87,    0,  -46,  169, -104,    0,    0,  -28,
  -29,    0, -144,  172,  -65,  -43,  -35,    0,  -83,  173,
  175,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    3,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  191,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -41,    0,    0,    0,    0,  -36,
    0,    0,    0,    0,    0,    0,    0,  171,    0,    0,
    0,    0,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -31,   -9,    0,    0,  195,    0,    0,    0,    0,
    0,    0,   43,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  102,   -3,    0,   40,   -8,   30,    0,    0,    0,    0,
   35,    0,    0,    0,    0,    0,   32,    0,   81,   84,
  160,  -51,    0,    0,    0,
};
final static int YYTABLESIZE=276;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   37,   37,   37,   37,   33,   37,   33,   33,   33,   31,
   44,   31,   31,   31,   50,   40,   50,   37,   37,   37,
   37,   42,   33,   33,   33,   33,   50,   31,   31,   31,
   31,   32,   19,   32,   32,   32,   85,   23,   86,   23,
    1,   48,   77,   19,   32,  106,   26,   47,   23,   32,
   32,   32,   32,   91,   89,   92,   40,   85,   85,   86,
   86,   26,   82,  107,   23,  127,   59,   50,   64,   52,
   66,   67,   69,  101,  128,  100,  105,   40,    3,   45,
   73,  137,   72,   82,   29,   87,   28,   23,   55,   56,
   88,  104,   80,   46,  121,  136,   43,  124,    4,   29,
    5,   28,    6,    7,   55,   56,   57,    8,    9,   29,
   10,   11,   30,   35,   12,   13,   36,    5,   76,    6,
    7,   12,   13,   38,    8,    9,  116,   10,   31,   85,
   37,   86,   39,  120,   79,   44,  123,   51,    5,   70,
    6,    7,   68,   56,   54,    8,    9,   65,   10,   31,
    5,   23,    6,    7,  131,   71,   32,    8,    9,   75,
   10,   31,  133,  134,   19,  112,  113,   23,   19,   23,
  114,  115,   32,   23,  142,   84,    4,   85,    5,   86,
    6,    7,   81,  102,   83,    8,    9,   96,   10,   11,
  103,  109,   12,   13,    4,  110,    5,  111,    6,    7,
  118,  117,  125,    8,    9,  126,   10,   11,  119,  129,
   12,   13,   37,   37,   97,   37,   85,   37,   86,   98,
   99,   85,   85,   86,   86,  130,   46,  132,  135,  140,
  138,  143,  141,  144,   26,   48,  139,   37,   37,   37,
  108,    0,   33,   33,   33,    0,    0,   31,   31,   31,
   49,    0,    0,   41,    0,    0,    0,    0,    0,    0,
   49,   12,    0,    0,    0,    0,    0,    0,    0,   32,
   32,   32,   90,   93,   94,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   14,   43,   44,   45,   44,   40,   44,   59,   60,   61,
   62,   46,   59,   60,   61,   62,   44,   59,   60,   61,
   62,   41,    3,   43,   44,   45,   43,    3,   45,    5,
  273,   59,   51,   14,    5,   44,   44,   18,   14,   59,
   60,   61,   62,   60,   61,   62,   40,   43,   43,   45,
   45,   59,   46,   62,   30,  117,   35,   44,   37,   30,
   39,   40,   41,   59,   59,   59,   44,   40,  257,  259,
   49,  133,   59,   46,   44,   42,   44,   53,  273,  274,
   47,   59,   53,  273,  103,  125,  258,  106,  260,   59,
  262,   59,  264,  265,  273,  274,  275,  269,  270,  261,
  272,  273,  257,   40,  276,  277,   40,  262,  273,  264,
  265,  276,  277,  271,  269,  270,   95,  272,  273,   43,
   40,   45,   40,  102,  258,  139,  105,   60,  262,  273,
  264,  265,  273,  274,  263,  269,  270,   59,  272,  273,
  262,  117,  264,  265,  125,  273,  117,  269,  270,  273,
  272,  273,  267,  268,  135,   85,   86,  133,  139,  135,
   87,   88,  133,  139,  258,   41,  260,   43,  262,   45,
  264,  265,   40,  278,   41,  269,  270,   41,  272,  273,
   40,  273,  276,  277,  260,   59,  262,   59,  264,  265,
   59,  266,  123,  269,  270,   41,  272,  273,   59,  273,
  276,  277,   42,   43,   41,   45,   43,   47,   45,   41,
   41,   43,   43,   45,   45,   41,  273,   59,  257,  273,
   59,   59,  268,   59,   44,   41,  135,  279,  280,  281,
   81,   -1,  279,  280,  281,   -1,   -1,  279,  280,  281,
  278,   -1,   -1,  278,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  259,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  279,
  280,  281,  279,  280,  281,  273,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"BEGIN","END","FUN","TYPEDEF","STRUCT",
"REPEAT","UNTIL","OUTF","IF","THEN","ELSE","END_IF","RET","GOTO","TAG","TOS",
"ID","CONSTANTE","CADENA","UINTEGER","SINGLE","\":=\"","\"!=\"","\"<=\"",
"\">=\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID BEGIN lista_sentencias END",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_variables ';'",
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
"asignacion : ID \":=\" expresion ';'",
"asignacion : ID '.' ID \":=\" expresion ';'",
"asignacion : ID \":=\" ID ';'",
"asignacion : lista_variables \":=\" lista_expresiones ';'",
"lista_variables : ID",
"lista_variables : lista_variables ',' ID",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
"retorno : RET '(' expresion ')' ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : ID '.' ID",
"factor : CONSTANTE",
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
"comparador : \"!=\"",
"comparador : '<'",
"comparador : '>'",
"comparador : \"<=\"",
"comparador : \">=\"",
"imprimir : OUTF '(' expresion ')' ';'",
"imprimir : OUTF '(' CADENA ')' ';'",
"repeat_until : REPEAT bloque_sentencias UNTIL '(' condicion ')' ';'",
"struct : TYPEDEF STRUCT '<' lista_tipos '>' '{' lista_variables '}' ID ';'",
"lista_tipos : lista_tipos ',' tipo",
"lista_tipos : tipo",
"goto : GOTO TAG ';'",
"conversion_explicita : TOS '(' expresion ')' ';'",
};

//#line 140 "gramatica.y"

int main(String[] args) {
    // Código principal del compilador
    System.out.println("Iniciando análisis sintáctico...");
    yyparse();
}

void yyerror(String s) {
    System.err.println("Error: " + s);
}

//#line 369 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 7:
//#line 37 "gramatica.y"
{ /* Aquí se verifica que la variable esté declarada */ }
break;
//#line 522 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
