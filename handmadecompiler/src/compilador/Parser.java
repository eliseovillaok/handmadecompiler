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
    package compilador;
    import estructura_arbol.*;
    import error.*;
    import java.util.List;
    import java.util.ArrayList;
    import java.util.Map;
    import java.util.NavigableMap;
    import manejo_archivos.*;
  
//#line 27 "Parser.java"




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
public final static short ID=257;
public final static short BEGIN=258;
public final static short END=259;
public final static short IF=260;
public final static short TOS=261;
public final static short THEN=262;
public final static short ELSE=263;
public final static short END_IF=264;
public final static short OUTF=265;
public final static short TYPEDEF=266;
public final static short FUN=267;
public final static short RET=268;
public final static short REPEAT=269;
public final static short UNTIL=270;
public final static short STRUCT=271;
public final static short GOTO=272;
public final static short SINGLE=273;
public final static short UINTEGER=274;
public final static short TAG=275;
public final static short UINTEGER_CONST=276;
public final static short SINGLE_CONST=277;
public final static short HEXA_CONST=278;
public final static short CADENA=279;
public final static short MENOR_IGUAL=280;
public final static short ASIGNACION=281;
public final static short MAYOR_IGUAL=282;
public final static short DISTINTO=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    2,    2,    3,    3,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    8,    8,    6,    6,    6,
    9,    9,    9,    5,    5,    5,    5,    5,    5,    5,
   11,   11,   18,   18,   18,   18,   18,   18,   19,   19,
    7,    7,    7,    7,    7,    7,    7,   21,   21,   21,
   21,   21,   12,   12,   12,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   13,   13,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   26,
   26,   27,   27,   25,   25,   25,   25,   28,   28,   28,
   28,   28,   28,   15,   15,   15,   15,   15,   15,   16,
   16,   16,   16,   16,   16,   16,   10,   10,   10,   10,
   29,   29,   29,   29,   30,   30,   30,   30,   31,   31,
   17,   17,   17,   24,   24,
};
final static short yylen[] = {                            2,
    3,    3,    2,    2,    2,    1,    2,    1,    1,    3,
    2,    2,    3,    3,    2,    2,    2,    7,    7,    7,
    7,    2,    2,    3,    2,    3,    3,    1,    1,    1,
    2,    2,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    4,    4,    6,    6,    6,    4,    4,
    3,    7,    3,    5,    6,    2,    4,    3,    3,    3,
    3,    3,    5,    5,    5,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    1,    1,    1,    1,    1,    3,    1,    1,
    2,    2,    2,    4,    4,    8,   10,    8,   10,    7,
    7,    6,    9,    9,    8,    8,   10,    7,    9,    3,
    1,    2,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    1,    1,    5,    5,    5,    5,    4,    5,    7,
    6,    7,    6,    6,    5,    7,    3,    3,    3,    3,
    7,    6,    6,    6,    7,    6,    5,    5,    3,    3,
    3,    3,    3,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    5,    4,    3,    0,    0,    0,
    0,    0,    0,    0,    0,   29,   28,    0,    0,    6,
    8,    9,    0,    0,    0,    0,   34,   35,   36,   37,
   38,   39,   40,   41,   42,    0,   16,    0,   15,    0,
    0,    0,    0,    0,    0,   85,   86,   87,    0,    0,
   89,    0,    0,   83,   90,    0,    0,   30,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  111,    0,    0,
    0,    0,   12,   11,    0,    1,    7,    0,    0,    0,
   25,    0,    0,   17,    0,    0,   23,   22,    0,    0,
    0,    0,    0,    0,    0,    0,   51,  122,  123,  119,
    0,    0,    0,    0,  118,  120,  121,    0,    0,    0,
    0,   93,   91,   92,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  139,  137,  140,  138,    0,    0,    0,  113,
    0,    0,    0,  153,  152,  151,   14,   13,    0,   27,
   26,   24,   10,    0,    0,    0,    0,    0,    0,    0,
   33,   32,   31,    0,   45,   44,   43,   95,   94,    0,
    0,    0,    0,    0,    0,    0,   81,   79,   82,   80,
    0,    0,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   77,   75,   78,   76,    0,    0,
    0,    0,  128,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  110,  112,    0,    0,
    0,    0,   57,    0,    0,    0,   50,   49,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  155,  154,    0,
    0,    0,    0,    0,  129,  127,  125,  126,  124,    0,
    0,    0,  150,  149,    0,    0,    0,    0,    0,   65,
   64,   63,    0,    0,  135,    0,    0,    0,    0,    0,
    0,    0,   54,    0,    0,    0,   55,   47,   48,   46,
    0,    0,    0,    0,    0,    0,  102,    0,    0,    0,
    0,    0,    0,    0,  148,    0,    0,  147,    0,  134,
    0,  133,  131,    0,    0,    0,   21,   52,    0,  101,
    0,    0,    0,    0,  108,    0,    0,  100,    0,  144,
    0,  143,  146,  142,  136,  132,  130,   20,   19,   18,
    0,    0,  106,    0,   98,   96,  105,    0,  145,  141,
  104,    0,    0,  109,  103,  107,   99,   97,
};
final static short yydgoto[] = {                          3,
    4,   19,   20,   21,   68,   23,   69,   25,   91,   26,
   27,   28,   51,   30,   31,   32,   33,   34,   35,   52,
  157,   53,   54,   55,   56,   70,  141,  108,   62,   63,
  129,
};
final static short yysindex[] = {                        84,
 -242, -143,    0,  512,    0,    0,    0,  -20,  -38,  -31,
  -16,  142,   -8,  561, -220,    0,    0,  -46,  411,    0,
    0,    0, -197,   -5,   33,   83,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  202,    0,  108,    0,  178,
 -176, -165,  129,   79,   80,    0,    0,    0,  190, -168,
    0,  138,  189,    0,    0,  -29,  166,    0,   86,  122,
   85,  174,  184,  196, -103,  -21,  577,    0,  -37,  -36,
  116,   99,    0,    0,  -20,    0,    0,    5,  207,  169,
    0,  148,  204,    0,  -77,  347,    0,    0,  -41,  258,
  267,  580,   71,  608,   26,   47,    0,    0,    0,    0,
  222,  240,  246,  249,    0,    0,    0,  252,   77,  275,
   31,    0,    0,    0,  252,  281,  287,  293,  316,  322,
  583,   68,  747,  313,  351,  163,  122,  344,  135,  159,
  157,  228,    0,    0,    0,    0,  815,  447,  398,    0,
  431,  172,  190,    0,    0,    0,    0,    0,  237,    0,
    0,    0,    0,  247,  828,  120,   43,  465,   49,  478,
    0,    0,    0,  262,    0,    0,    0,    0,    0,  489,
  329,  284,  390,  189,  390,  189,    0,    0,    0,    0,
  728,  323,    0,  822,  450,  583,  294,  323,  390,  189,
  390,  189,  728,  323,    0,    0,    0,    0,  270,  583,
  517,  146,    0,  168,  291,  263,  122,  122,  324,  327,
  330,  331,  334,  536,  180,  190,    0,    0,  190,    3,
  555,   41,    0,  252,  252,  352,    0,    0,  252,  340,
  343,  356,  512,  345,  599,  150,  566,    0,    0,  276,
  567,  583,  556,  303,    0,    0,    0,    0,    0,  368,
  361,  375,    0,    0,  361,  378,  381,  361,  386,    0,
    0,    0,  611,  430,    0,  581,  597,  323,  323,  728,
  323,  323,    0,  512,  541,  448,    0,    0,    0,    0,
  402,  583,  601,  311,   73,  399,    0,  583,  605,  412,
  354,   53,  361,   74,    0,  413,  134,    0,  614,    0,
  256,    0,    0,  470,  -35,  492,    0,    0,  414,    0,
  419,  633,  583,  299,    0,  634,  434,    0,  436,    0,
  144,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  642,  446,    0,  -42,    0,    0,    0,  650,    0,    0,
    0,  652,  312,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0, -191,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -14,    0,    0,    0,    0,    0,    0,
    0,    0,   -3,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  715,    0,    0,    0,    0,    0,
    0,    6,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  133,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,   39,   62,   67,    0,    0,    0,    0,
    0,   -7,    0,    0,    0,    0,    0,   -6,   90,   95,
  100,  125,    2,   27,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  304,  360,  418,
  425,  426,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  -60,  -18,    0,    7,  575,   -1,    0,  632,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,  579,
    0,  445,  528,    0,  438,  424,    0,  667,    0,    0,
  -23,
};
final static int YYTABLESIZE=875;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
   77,   40,   24,  143,   36,   42,   85,   41,   49,   29,
   22,  122,   74,   50,   29,    5,  344,   24,   40,   36,
   39,   80,   42,   57,   41,   22,   84,   84,   84,   84,
   84,   64,   84,  116,  115,   71,  131,   74,   85,   74,
   74,   74,  117,  266,   84,   84,   84,   84,   42,   56,
  149,  116,  115,   84,   72,   74,   74,   74,   74,   78,
  117,  265,   29,  148,   56,   30,  169,  114,  116,   79,
  117,  187,   86,  140,   72,   30,   72,   72,   72,   70,
   96,   70,   70,   70,  172,  114,  229,  112,  113,  231,
  172,   97,   72,   72,   72,   72,   85,   70,   70,   70,
   70,  228,   73,  206,   73,   73,   73,   71,  114,   71,
   71,   71,    6,  116,    7,  117,   29,   85,   40,  110,
   73,   73,   73,   73,  109,   71,   71,   71,   71,  167,
   68,  315,   68,   68,   68,   66,   29,   66,   66,   66,
   69,   88,   69,   69,   69,  127,  132,  218,   68,   68,
   68,   68,   50,   66,   66,   66,   66,  146,   69,   69,
   69,   69,  116,  226,  117,   67,  139,   67,   67,   67,
  103,  101,  276,  102,  144,  104,   53,   85,  208,  158,
  116,   29,  117,   67,   67,   67,   67,   85,  106,  105,
  107,   53,  116,  154,  117,   29,  209,  106,  105,  107,
  208,   60,  207,  204,  247,  116,  125,  117,  280,   73,
   50,  219,   85,  304,  306,  161,   50,   37,  212,   82,
  211,  343,   50,  329,   43,   44,  249,  153,   29,   45,
  119,   24,  121,  142,   50,  120,   29,   29,  262,   22,
   50,   84,   38,   83,   46,   47,   48,   84,   50,  292,
   81,   82,   74,  294,  116,  115,  297,   77,   74,   38,
  147,   56,   56,  117,   56,   84,   50,   84,   84,   29,
   29,   29,   24,   24,   24,   83,   74,   29,   74,   74,
   22,   22,   22,   29,   50,   77,   56,   77,  114,   72,
   50,  321,  186,   50,   70,   72,   50,  170,  227,   29,
   70,   29,   24,  170,   24,  161,  208,  164,   29,   82,
   22,  320,   22,   72,  327,   72,   72,   73,   70,   50,
   70,   70,   71,   73,  252,   50,  166,  171,   71,  200,
   82,   50,  322,  183,  207,  313,  314,   50,   87,    1,
    2,   73,   58,   73,   73,   68,   71,   61,   71,   71,
   66,   68,  250,  202,  145,   69,   66,  336,   16,   17,
   50,   69,   61,   92,   44,  116,   50,  117,   45,   68,
  348,   68,   68,   50,   66,  225,   66,   66,   58,   69,
   67,   69,   69,   46,   47,   48,   67,  207,   53,   53,
   82,   53,  324,  115,   16,   17,   50,   42,   58,  149,
   82,  246,  340,   60,   67,  279,   67,   67,   98,  203,
   99,  100,   59,   53,   16,   17,  210,   98,   60,   99,
  100,  123,   44,  248,  152,   82,   45,   43,   44,  133,
  134,  103,   45,   94,   44,  261,  104,  216,   45,  135,
  136,   46,   47,   48,  124,   43,   44,   46,   47,   48,
   45,  137,   44,   46,   47,   48,   45,   89,   58,  155,
   44,   62,  150,  151,   45,   46,   47,   48,   58,   59,
  301,   46,   47,   48,   16,   17,   62,  173,   44,   46,
   47,   48,   45,   58,   59,  213,  111,  215,  300,  116,
  239,  117,  116,  222,  117,  175,   44,   46,   47,   48,
   45,  177,   44,  223,  179,   44,   45,  181,   44,   45,
  230,  326,   45,  162,  163,   46,   47,   48,  232,  233,
  251,   46,   47,   48,   46,   47,   48,   46,   47,   48,
  184,   44,  242,  243,  234,   45,  189,   44,  282,  283,
  237,   45,  191,   44,  199,  174,  176,   45,  193,   44,
   46,   47,   48,   45,  335,  241,   46,   47,   48,   61,
  190,  192,   46,   47,   48,  288,  289,  347,   46,   47,
   48,  195,   44,  311,  312,  245,   45,  197,   44,  220,
  221,  255,   45,  256,  235,   44,   61,  257,  258,   45,
  259,   46,   47,   48,  260,  267,  273,   46,   47,   48,
  274,  277,  159,   58,   46,   47,   48,  270,   44,  240,
   90,  281,   45,  275,  287,   60,   93,  291,   95,   16,
   17,  103,  101,  244,  102,  290,  104,   46,   47,   48,
  178,  180,  293,  128,  130,  126,  295,  296,  165,  302,
  103,  101,  138,  102,  298,  104,  196,  198,  168,  103,
  101,  299,  102,  263,  104,  303,  264,  278,  308,  310,
   90,  156,  316,  318,  285,  286,   75,    9,  319,   76,
   10,  323,  325,   62,  332,   11,   12,  331,   13,   14,
   58,   59,   15,   16,   17,   18,  182,   66,  185,  217,
   10,  333,  337,  188,  339,   11,  194,  338,   13,   14,
  341,  205,   15,    8,    9,  309,  307,   10,  345,  342,
  346,  317,   11,   12,    2,   13,   14,  160,  118,   15,
   16,   17,   18,    0,    0,    8,    9,    0,  328,   10,
    0,    0,    0,    0,   11,   12,  334,   13,   14,    0,
    0,   15,   16,   17,   18,    0,    0,    8,    9,  236,
  330,   10,    0,    0,    0,    0,   11,   12,    0,   13,
   14,    0,    0,   15,   16,   17,   18,    8,    9,  103,
  101,   10,  102,    0,  104,    0,   11,   12,    0,   13,
   14,  253,  254,   15,   16,   17,   18,  201,  103,  101,
    0,  102,    0,  104,    0,    0,  305,    9,    0,    0,
   10,    0,  268,  269,  271,   11,   12,  272,   13,   14,
    0,    0,   15,   16,   17,   18,   65,   66,   67,    0,
   10,    0,  284,   66,   67,   11,   10,    0,   13,   14,
    0,   11,   15,   66,   13,   14,   10,    0,   15,   66,
   67,   11,   10,    0,   13,   14,    0,   11,   15,    0,
   13,   14,    0,    0,   15,  214,  103,  101,    0,  102,
    0,  104,  238,  103,  101,    0,  102,    0,  104,  103,
  101,  224,  102,    0,  104,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
   19,   40,    4,   40,   40,   44,   44,   46,   40,   14,
    4,   41,   59,   45,   19,  258,   59,   19,   40,   40,
   59,   23,   44,   40,   46,   19,   41,   42,   43,   44,
   45,   40,   47,   41,   41,  256,   60,   41,   44,   43,
   44,   45,   41,   41,   59,   60,   61,   62,   44,   44,
   46,   59,   59,   59,  275,   59,   60,   61,   62,  257,
   59,   59,   67,   59,   59,  257,   41,   41,   43,  267,
   45,   41,   40,   67,   41,  267,   43,   44,   45,   41,
  257,   43,   44,   45,   44,   59,   44,  256,  257,   41,
   44,  257,   59,   60,   61,   62,   44,   59,   60,   61,
   62,   59,   41,  127,   43,   44,   45,   41,  277,   43,
   44,   45,  256,   43,  258,   45,  121,   44,   40,   40,
   59,   60,   61,   62,   46,   59,   60,   61,   62,   59,
   41,   59,   43,   44,   45,   41,  141,   43,   44,   45,
   41,   59,   43,   44,   45,   60,   62,  141,   59,   60,
   61,   62,   45,   59,   60,   61,   62,   59,   59,   60,
   61,   62,   43,   44,   45,   41,  270,   43,   44,   45,
   42,   43,  233,   45,   59,   47,   44,   44,   44,  257,
   43,  186,   45,   59,   60,   61,   62,   44,   60,   61,
   62,   59,   43,   46,   45,  200,   62,   60,   61,   62,
   44,   60,   44,   41,   59,   43,   41,   45,   59,  256,
   45,   40,   44,  274,  275,  257,   45,  256,   62,  257,
   62,  264,   45,  259,  256,  257,   59,   59,  233,  261,
   42,  233,  262,  270,   45,   47,  241,  242,   59,  233,
   45,  256,  281,  281,  276,  277,  278,  262,   45,  251,
  256,  257,  256,  255,  262,  262,  258,  276,  262,  281,
  256,  256,  257,  262,  259,  280,   45,  282,  283,  274,
  275,  276,  274,  275,  276,  281,  280,  282,  282,  283,
  274,  275,  276,  288,   45,  304,  281,  306,  262,  256,
   45,  293,  262,   45,  256,  262,   45,  257,  256,  304,
  262,  306,  304,  257,  306,  257,   44,   41,  313,  257,
  304,  259,  306,  280,   59,  282,  283,  256,  280,   45,
  282,  283,  256,  262,   62,   45,  256,  281,  262,  262,
  257,   45,  259,  257,   44,  263,  264,   45,  256,  256,
  257,  280,  257,  282,  283,  256,  280,   44,  282,  283,
  256,  262,   62,   41,  256,  256,  262,   59,  273,  274,
   45,  262,   59,  256,  257,   43,   45,   45,  261,  280,
   59,  282,  283,   45,  280,  256,  282,  283,  257,  280,
  256,  282,  283,  276,  277,  278,  262,   44,  256,  257,
  257,  259,  259,  256,  273,  274,   45,   44,  257,   46,
  257,  256,  259,   44,  280,  256,  282,  283,  280,   59,
  282,  283,  271,  281,  273,  274,  258,  280,   59,  282,
  283,  256,  257,  256,  256,  257,  261,  256,  257,  256,
  257,   42,  261,  256,  257,  256,   47,   40,  261,  256,
  257,  276,  277,  278,  279,  256,  257,  276,  277,  278,
  261,  256,  257,  276,  277,  278,  261,  256,  257,  256,
  257,   44,  256,  257,  261,  276,  277,  278,   44,   44,
   41,  276,  277,  278,  273,  274,   59,  256,  257,  276,
  277,  278,  261,   59,   59,  258,   49,   41,   59,   43,
   41,   45,   43,  257,   45,  256,  257,  276,  277,  278,
  261,  256,  257,  257,  256,  257,  261,  256,  257,  261,
   46,  256,  261,  256,  257,  276,  277,  278,   41,  258,
  258,  276,  277,  278,  276,  277,  278,  276,  277,  278,
  256,  257,  263,  264,   46,  261,  256,  257,  263,  264,
  257,  261,  256,  257,  121,  101,  102,  261,  256,  257,
  276,  277,  278,  261,  256,  262,  276,  277,  278,  256,
  116,  117,  276,  277,  278,  263,  264,  256,  276,  277,
  278,  256,  257,  263,  264,   59,  261,  256,  257,  142,
  143,  258,  261,  257,  256,  257,   12,  258,  258,  261,
  257,  276,  277,  278,   59,   41,  257,  276,  277,  278,
  258,  257,  256,  257,  276,  277,  278,  256,  257,  186,
   36,   46,  261,  258,   59,  256,   38,  257,   40,  273,
  274,   42,   43,  200,   45,  258,   47,  276,  277,  278,
  103,  104,  258,   59,   60,   57,  259,  257,   59,   59,
   42,   43,   64,   45,  259,   47,  119,  120,   41,   42,
   43,   41,   45,  216,   47,   59,  219,   59,  257,   59,
   86,   83,  264,   59,  241,  242,  256,  257,  257,  259,
  260,  259,   59,  256,  256,  265,  266,  264,  268,  269,
  256,  256,  272,  273,  274,  275,  108,  257,  110,  259,
  260,   59,   59,  115,  259,  265,  118,  264,  268,  269,
   59,  127,  272,  256,  257,  282,  259,  260,   59,  264,
   59,  288,  265,  266,    0,  268,  269,   86,   52,  272,
  273,  274,  275,   -1,   -1,  256,  257,   -1,  259,  260,
   -1,   -1,   -1,   -1,  265,  266,  313,  268,  269,   -1,
   -1,  272,  273,  274,  275,   -1,   -1,  256,  257,  171,
  259,  260,   -1,   -1,   -1,   -1,  265,  266,   -1,  268,
  269,   -1,   -1,  272,  273,  274,  275,  256,  257,   42,
   43,  260,   45,   -1,   47,   -1,  265,  266,   -1,  268,
  269,  207,  208,  272,  273,  274,  275,   41,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,  256,  257,   -1,   -1,
  260,   -1,  224,  225,  226,  265,  266,  229,  268,  269,
   -1,   -1,  272,  273,  274,  275,  256,  257,  258,   -1,
  260,   -1,  256,  257,  258,  265,  260,   -1,  268,  269,
   -1,  265,  272,  257,  268,  269,  260,   -1,  272,  257,
  258,  265,  260,   -1,  268,  269,   -1,  265,  272,   -1,
  268,  269,   -1,   -1,  272,   41,   42,   43,   -1,   45,
   -1,   47,   41,   42,   43,   -1,   45,   -1,   47,   42,
   43,   44,   45,   -1,   47,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=283;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"ID","BEGIN","END","IF","TOS","THEN","ELSE",
"END_IF","OUTF","TYPEDEF","FUN","RET","REPEAT","UNTIL","STRUCT","GOTO","SINGLE",
"UINTEGER","TAG","UINTEGER_CONST","SINGLE_CONST","HEXA_CONST","CADENA",
"MENOR_IGUAL","ASIGNACION","MAYOR_IGUAL","DISTINTO",
};
final static String yyrule[] = {
"$accept : programa",
"programa : header_programa lista_sentencias END",
"programa : header_programa lista_sentencias error",
"header_programa : ID BEGIN",
"header_programa : ID error",
"header_programa : error BEGIN",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_variables ';'",
"sentencia_declarativa : TAG ';'",
"sentencia_declarativa : TAG error",
"sentencia_declarativa : tipo ID ';'",
"sentencia_declarativa : tipo ID error",
"sentencia_declarativa : ID ';'",
"sentencia_declarativa : ID error",
"sentencia_declarativa : lista_variables ';'",
"sentencia_declarativa : header_funcion '(' parametro ')' BEGIN lista_sentencias END",
"sentencia_declarativa : header_funcion '(' parametro ')' BEGIN error END",
"sentencia_declarativa : header_funcion '(' error ')' BEGIN lista_sentencias END",
"sentencia_declarativa : error '(' parametro ')' BEGIN lista_sentencias END",
"sentencia_declarativa : struct ';'",
"sentencia_declarativa : struct error",
"sentencia_declarativa : tipo lista_variables error",
"sentencia_declarativa : lista_variables error",
"header_funcion : tipo FUN ID",
"header_funcion : tipo FUN error",
"tipo : UINTEGER",
"tipo : SINGLE",
"tipo : ID",
"parametro : tipo ID",
"parametro : tipo error",
"parametro : error ID",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : retorno",
"sentencia_ejecutable : invocacion_funcion",
"sentencia_ejecutable : seleccion_if",
"sentencia_ejecutable : imprimir",
"sentencia_ejecutable : repeat_until",
"sentencia_ejecutable : goto",
"asignacion : asignacion_simple",
"asignacion : asignacion_multiple",
"asignacion_simple : ID ASIGNACION expresion ';'",
"asignacion_simple : ID ASIGNACION expresion error",
"asignacion_simple : ID ASIGNACION error ';'",
"asignacion_simple : ID '.' ID ASIGNACION expresion ';'",
"asignacion_simple : ID '.' ID ASIGNACION error ';'",
"asignacion_simple : ID '.' ID ASIGNACION expresion error",
"asignacion_multiple : lista_variables ASIGNACION lista_expresiones ';'",
"asignacion_multiple : lista_variables ASIGNACION lista_expresiones error",
"lista_variables : ID ',' ID",
"lista_variables : ID '.' ID ',' ID '.' ID",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ',' ID '.' ID",
"lista_variables : ID '.' ID ID '.' ID",
"lista_variables : lista_variables ID",
"lista_variables : lista_variables ID '.' ID",
"lista_expresiones : expresion ',' expresion",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion error expresion",
"lista_expresiones : error ',' expresion",
"lista_expresiones : expresion ',' error",
"retorno : RET '(' expresion ')' ';'",
"retorno : RET '(' expresion ')' error",
"retorno : RET '(' error ')' ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : expresion '+' error",
"expresion : expresion '-' error",
"expresion : error '+' termino",
"expresion : error '-' termino",
"expresion : error '+' error",
"expresion : error '-' error",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : termino '*' error",
"termino : termino '/' error",
"termino : error '*' factor",
"termino : error '/' factor",
"termino : error '*' error",
"termino : error '/' error",
"termino : factor",
"factor : ID",
"factor : UINTEGER_CONST",
"factor : SINGLE_CONST",
"factor : HEXA_CONST",
"factor : ID '.' ID",
"factor : invocacion_funcion",
"factor : conversion_explicita",
"factor : '-' ID",
"factor : '-' SINGLE_CONST",
"factor : '-' error",
"invocacion_funcion : ID '(' expresion ')'",
"invocacion_funcion : ID '(' error ')'",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias END_IF ';'",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias END_IF error",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF error",
"seleccion_if : IF condicion ')' THEN bloque_sentencias END_IF ';'",
"seleccion_if : IF '(' condicion THEN bloque_sentencias END_IF ';'",
"seleccion_if : IF condicion THEN bloque_sentencias END_IF ';'",
"seleccion_if : IF condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'",
"seleccion_if : IF '(' condicion THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'",
"seleccion_if : IF condicion THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'",
"seleccion_if : IF '(' condicion ')' THEN error END_IF ';'",
"seleccion_if : IF '(' condicion ')' THEN error ELSE error END_IF ';'",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias ';'",
"seleccion_if : IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias ';'",
"bloque_sentencias : BEGIN lista_sentencias_ejecutables END",
"bloque_sentencias : sentencia_ejecutable",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"condicion : expresion comparador expresion",
"condicion : expresion error expresion",
"condicion : error comparador expresion",
"condicion : expresion comparador error",
"comparador : '='",
"comparador : DISTINTO",
"comparador : '<'",
"comparador : '>'",
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"imprimir : OUTF '(' expresion ')' ';'",
"imprimir : OUTF '(' CADENA ')' ';'",
"imprimir : OUTF '(' expresion ')' error",
"imprimir : OUTF '(' CADENA ')' error",
"imprimir : OUTF '(' ')' ';'",
"imprimir : OUTF '(' error ')' ';'",
"repeat_until : REPEAT bloque_sentencias UNTIL '(' condicion ')' ';'",
"repeat_until : REPEAT bloque_sentencias '(' condicion ')' ';'",
"repeat_until : REPEAT bloque_sentencias UNTIL '(' condicion ')' error",
"repeat_until : REPEAT bloque_sentencias UNTIL condicion ')' ';'",
"repeat_until : REPEAT bloque_sentencias UNTIL '(' condicion ';'",
"repeat_until : REPEAT bloque_sentencias UNTIL condicion ';'",
"repeat_until : REPEAT error UNTIL '(' condicion ')' ';'",
"struct : TYPEDEF bloque_struct_multiple ID",
"struct : TYPEDEF bloque_struct_simple ID",
"struct : TYPEDEF bloque_struct_multiple error",
"struct : TYPEDEF bloque_struct_simple error",
"bloque_struct_multiple : STRUCT '<' lista_tipos '>' BEGIN lista_variables END",
"bloque_struct_multiple : '<' lista_tipos '>' BEGIN lista_variables END",
"bloque_struct_multiple : STRUCT lista_tipos '>' BEGIN lista_variables END",
"bloque_struct_multiple : STRUCT '<' lista_tipos BEGIN lista_variables END",
"bloque_struct_simple : STRUCT '<' tipo '>' BEGIN ID END",
"bloque_struct_simple : '<' tipo '>' BEGIN ID END",
"bloque_struct_simple : tipo '>' BEGIN ID END",
"bloque_struct_simple : '<' tipo BEGIN ID END",
"lista_tipos : lista_tipos ',' tipo",
"lista_tipos : tipo ',' tipo",
"goto : GOTO TAG ';'",
"goto : GOTO TAG error",
"goto : GOTO error ';'",
"conversion_explicita : TOS '(' expresion ')'",
"conversion_explicita : TOS '(' error ')'",
};

//#line 467 "gramatica.y"
  
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
            FileHandler.appendToFile(filePathParser, "Redeclaracion de variable "+nombre+" como UINTEGER. Linea "+lex.getNumeroLinea());
        } else if ((nombre.charAt(0) == 'u' || nombre.charAt(0) == 'v' || nombre.charAt(0) == 'w') && tipo.equals("single") ) {
            ts.actualizarTipo(nombre, "SINGLE");
            FileHandler.appendToFile(filePathParser, "Redeclaracion de variable "+nombre+" como SINGLE. Linea "+lex.getNumeroLinea());
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
//#line 831 "Parser.java"
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
case 1:
//#line 17 "gramatica.y"
{
              Nodo programa = new NodoCompuesto("programa",(Nodo)val_peek(1).obj,null);
              FileHandler.appendToFile("salida_arbol_sintactico.txt",programa.toString()); /* Salida del arbol sintactico a un archivo*/
              yyval.obj = programa; programaFinal = programa;
              actualizarTipo(val_peek(2).sval, "NOMBRE_PROGRAMA"); /* Actualiza el tipo de la variable que se genera con el nombre del programa, puede servir a futuro..*/
              actualizarUso(val_peek(2).sval, "NOMBRE_PROGRAMA");
              System.out.println("-------- FIN DEL PROGRAMA --------");
          }
break;
case 2:
//#line 25 "gramatica.y"
{ yyerror(ERROR_END); }
break;
case 3:
//#line 28 "gramatica.y"
{mangling.add(val_peek(1).sval);
                /*$$.obj = new NodoConcreto($1.sval);  // Nodo para una variable*/
}
break;
case 4:
//#line 31 "gramatica.y"
{yyerror(ERROR_BEGIN);}
break;
case 5:
//#line 32 "gramatica.y"
{yyerror(ERROR_NOMBRE_PROGRAMA);}
break;
case 6:
//#line 36 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 37 "gramatica.y"
{ yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj); }
break;
case 8:
//#line 40 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 9:
//#line 41 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 10:
//#line 44 "gramatica.y"
{String[] lista = (val_peek(1).sval).split(",");
                                                   for (String s : lista){
                                                        if(ts.buscar(actualizarAmbito(s)) == null){
                                                            String s_mangling = nameMangling(s);
                                                            actualizarTipo(s_mangling, val_peek(2).sval);
                                                        }else{
                                                            yyerror(VARIABLE_REDECLARADA);
                                                            borrarSimbolos(s);
                                                        }
                                                   }
                                                  }
break;
case 11:
//#line 55 "gramatica.y"
{
                                if(ts.buscar(actualizarAmbito(val_peek(1).sval)) == null){
                                    actualizarUso(val_peek(1).sval, "TAG"); nameMangling(val_peek(1).sval);
                                }else{
                                    yyerror(VARIABLE_REDECLARADA);
                                    borrarSimbolos(val_peek(1).sval);
                                }
                                }
break;
case 12:
//#line 63 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 13:
//#line 64 "gramatica.y"
{
                                    if(ts.buscar(actualizarAmbito(val_peek(1).sval)) == null){
                                        actualizarUso(val_peek(1).sval, "Variable");
                                        if (tipoEmbebido(val_peek(1).sval))
                                            chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                        else
                                            actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                        /*SE BUSCA EN LA TABLA DE SIMBOLOS SI EL TIPO DE LA VARIABLE ES UN STRUCT, SE DESCARTA LA BUSQUEDA SI EL TIPO ES UINTEGER O SINGLE*/
                                        if(!(val_peek(2).sval.equalsIgnoreCase("UINTEGER") || val_peek(2).sval.equalsIgnoreCase("SINGLE"))){
                                            Token estructura = estaDeclarado(val_peek(2).sval);
                                            if (estructura == null){
                                                yyerror(TIPO_NO_DEFINIDO);
                                            }else{
                                                NavigableMap<String,String> variables = ((TokenStruct)estructura).getVariables();                                       /*obtengo las variables del struct*/
                                                for (Map.Entry<String, String> entry : variables.entrySet()) {                                                          /*recorro las variables del struct*/
                                                    ts.insertar(new Token(257,nameMangling(entry.getKey()+":"+val_peek(1).sval),"",estructura.getType(entry.getKey()), ""));     /*las agrego a la tabla de simbolos*/
                                                }
                                                FileHandler.appendToFile(filePathParser,"DECLARACION DE STRUCT. Linea "+lex.getNumeroLinea() );
                                            }
                                            borrarSimbolos(val_peek(2).sval);
                                        }
                                        nameMangling(val_peek(1).sval);
                                    }else{
                                        yyerror(VARIABLE_REDECLARADA);
                                    }
                                }
break;
case 14:
//#line 90 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 15:
//#line 91 "gramatica.y"
{
                                if(ts.buscar(actualizarAmbito(val_peek(1).sval)) == null){
                                    actualizarUso(val_peek(1).sval, "Variable"); nameMangling(val_peek(1).sval);
                                }else{
                                    yyerror(VARIABLE_REDECLARADA);
                                    borrarSimbolos(val_peek(1).sval);
                                }
                               }
break;
case 16:
//#line 99 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 17:
//#line 100 "gramatica.y"
{
                                            yyval = val_peek(1); yyval.obj = null;
                                            String[] lista = (val_peek(1).sval).split(",");
                                                   for (String s : lista){
                                                        if(ts.buscar(actualizarAmbito(s)) == null){
                                                            nameMangling(s);
                                                        }else{
                                                            yyerror(VARIABLE_REDECLARADA);
                                                            borrarSimbolos(s);
                                                        }
                                                   }
                                            }
break;
case 18:
//#line 112 "gramatica.y"
{
                                                                                    if (yyval.ival == 1){
                                                                                        FileHandler.appendToFile(filePathParser,"DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                        actualizarTipoParamEsperado(val_peek(6).sval, val_peek(4).sval);
                                                                                        FileHandler.appendToFile(filePathParser,"FUNCION: "+val_peek(6).sval);
                                                                                        Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);
                                                                                        yyval.obj = new NodoFuncion(val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador, ts.devolverTipo(val_peek(6).sval));
                                                                                        mangling.remove(mangling.size() - 1);
                                                                                    }
                                                                                    }
break;
case 19:
//#line 122 "gramatica.y"
{yyerror(ERROR_RET);}
break;
case 20:
//#line 123 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 21:
//#line 124 "gramatica.y"
{yyerror(ERROR_HEADER_FUNC);}
break;
case 23:
//#line 126 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 127 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 128 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 26:
//#line 131 "gramatica.y"
{
                                if(ts.buscar(actualizarAmbito(val_peek(0).sval)) == null){
                                    actualizarUso(val_peek(0).sval, "Funcion"); actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                                    errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                                    this.nuevoNombre = nameMangling(val_peek(0).sval); mangling.add(val_peek(0).sval); yyval.sval = this.nuevoNombre; 
                                    yyval.ival = 1;
                                }else{
                                    yyerror(FUNCION_REDECLARADA);
                                    borrarSimbolos(val_peek(0).sval);
                                    yyval.ival = 0;
                                }
                              }
break;
case 27:
//#line 144 "gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 31:
//#line 153 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      nameMangling(val_peek(0).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      yyval.sval = val_peek(1).sval;
                     }
break;
case 32:
//#line 158 "gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 33:
//#line 159 "gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 43:
//#line 175 "gramatica.y"
{
                      borrarSimbolos(val_peek(3).sval);
                      Token simbolo = estaDeclarado(val_peek(3).sval);
                      if(simbolo != null){
                        yyval.obj = new NodoAsignacion(":=",new NodoConcreto(val_peek(3).sval, simbolo.getType()),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                        FileHandler.appendToFile(filePathParser,"ASIGNACION");
                      }else{
                        yyerror(VARIABLE_NO_DECLARADA);
                      }
                   }
break;
case 44:
//#line 185 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 45:
//#line 186 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 46:
//#line 187 "gramatica.y"
{
                                borrarSimbolos(val_peek(5).sval);
                                borrarSimbolos(val_peek(3).sval);
                                Token simbolo = estaDeclarado(val_peek(3).sval + ":" + val_peek(5).sval);
                                if(simbolo != null){
                                    yyval.obj = new NodoAsignacion(":=",new NodoConcreto(val_peek(3).sval + ":" + val_peek(5).sval, simbolo.getType()),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                                    FileHandler.appendToFile(filePathParser,"ASIGNACION");
                                }else{
                                    yyerror(VARIABLE_NO_DECLARADA);
                                }
                   }
break;
case 47:
//#line 198 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 48:
//#line 199 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 49:
//#line 202 "gramatica.y"
{FileHandler.appendToFile(filePathParser,"ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoAsignacionMultiple(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
                                                                         System.out.println("CANTIDAD DE ELEMENTOS: " + val_peek(3).sval);
                                                                         if (!igualCantElementos(val_peek(3).sval,val_peek(1).sval)) 
                                                                            yyerror(ERROR_CANTIDAD_ASIGNACION);
                                                                         borrarSimbolos(val_peek(3).sval);
                                                                        }
break;
case 50:
//#line 209 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 51:
//#line 212 "gramatica.y"
{
                                                          Token hijoIzq = estaDeclarado(val_peek(2).sval);
                                                          Token hijoDer = estaDeclarado(val_peek(0).sval);
                                                          actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                                          if (hijoIzq != null && hijoDer != null)
                                                            yyval.obj = new NodoLista(",",new NodoConcreto(hijoIzq.getLexema(), hijoIzq.getType()),new NodoConcreto(hijoDer.getLexema(), hijoDer.getType()));
                                                          else
                                                            yyval.obj = new NodoLista(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));
                                                         }
break;
case 53:
//#line 223 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            Token simbolo = estaDeclarado(val_peek(0).sval);
                                            if (simbolo != null)
                                                yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,new NodoConcreto(simbolo.getLexema(), simbolo.getType()));
                                            else
                                                yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(2).sval));}
break;
case 55:
//#line 232 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 233 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 234 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 237 "gramatica.y"
{yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 59:
//#line 238 "gramatica.y"
{yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 60:
//#line 239 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 61:
//#line 241 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 62:
//#line 242 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 246 "gramatica.y"
{ FileHandler.appendToFile(filePathParser,"RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoRet("RET",(Nodo)val_peek(2).obj,null);
                                    }
break;
case 64:
//#line 249 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 65:
//#line 250 "gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 66:
//#line 253 "gramatica.y"
{
                yyval.obj = new NodoSuma("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                FileHandler.appendToFile(filePathParser, "SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 67:
//#line 257 "gramatica.y"
{
            yyval.obj = new NodoResta("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            FileHandler.appendToFile(filePathParser, "RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 68:
//#line 261 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 69:
//#line 262 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 263 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 264 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 265 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 266 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 267 "gramatica.y"
{ yyval = val_peek(0);  }
break;
case 75:
//#line 270 "gramatica.y"
{
              yyval.obj = new NodoMultiplicacion("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              FileHandler.appendToFile(filePathParser, "MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 76:
//#line 274 "gramatica.y"
{
              yyval.obj = new NodoDivision("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              FileHandler.appendToFile(filePathParser, "DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 278 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 78:
//#line 279 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 280 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 281 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 282 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 283 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 284 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 84:
//#line 287 "gramatica.y"
{
            Token simbolo = estaDeclarado(val_peek(0).sval);
            if (simbolo == null){
                yyerror(VARIABLE_NO_DECLARADA);
                yyval.obj = new NodoConcreto("N/D", "N/D");  /* Nodo para una variable no declarada*/
            }
            else
                yyval.obj = new NodoConcreto(val_peek(0).sval, simbolo.getType());  /* Nodo para una variable*/
            
            borrarSimbolos(val_peek(0).sval);
         }
break;
case 85:
//#line 298 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"UINTEGER");  /* Nodo para constante UINTEGER*/
         }
break;
case 86:
//#line 301 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"SINGLE");  /* Nodo para constante SINGLE*/
         }
break;
case 87:
//#line 304 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"HEXA");  /* Nodo para constante HEXA*/
         }
break;
case 88:
//#line 307 "gramatica.y"
{   /* Nodo para una variable struct*/
                        yyval.obj = new NodoConcreto(val_peek(2).sval + "." + val_peek(0).sval,ts.buscar(val_peek(0).sval+":"+val_peek(2).sval).getType());
                        borrarSimbolos(val_peek(0).sval);
                        }
break;
case 91:
//#line 313 "gramatica.y"
{
            Token simbolo = estaDeclarado(val_peek(0).sval);
            if (simbolo == null)
                yyerror(VARIABLE_NO_DECLARADA);
            else{
                yyval.obj = new NodoConcreto("-" + val_peek(0).sval, simbolo.getType());  /* Nodo para una variable negativa*/
                borrarSimbolos(val_peek(0).sval);
            }
        }
break;
case 92:
//#line 322 "gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval,"SINGLE");}
break;
case 93:
//#line 323 "gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 94:
//#line 326 "gramatica.y"
{
                                                Nodo nodoExpresion = (Nodo)val_peek(1).obj; /* N/D si no hay nada*/

                                                if ((estaDeclarado(val_peek(3).sval) != null) && paramRealIgualFormal(val_peek(3).sval,nodoExpresion.devolverTipo(mangling))){
                                                    String ambitoFuncion = estaDeclarado(val_peek(3).sval).getLexema();
                                                    yyval.obj = new NodoInvocacionFuncion("INVOCACION_FUNCION_" + actualizarAmbito(val_peek(3).sval),nodoExpresion,null, ts.buscar(ambitoFuncion).getType());
                                                }
                                                else if (estaDeclarado(val_peek(3).sval) == null){
                                                    yyerror(FUNCION_NO_DECLARADA);
                                                    yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + "N/D",nodoExpresion,null);
                                                }
                                                else if(!paramRealIgualFormal(val_peek(3).sval,nodoExpresion.devolverTipo(mangling))) {
                                                    yyerror(ERROR_TIPO_PARAMETRO);
                                                    yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(3).sval,nodoExpresion,null);
                                                } 
                                                    

                                                borrarSimbolos(val_peek(3).sval);
                                               }
break;
case 95:
//#line 345 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 96:
//#line 348 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCuerpo("CUERPO",(Nodo)val_peek(2).obj,null));
                  FileHandler.appendToFile(filePathParser,"DECLARACION DE IF. Linea " + lex.getNumeroLinea() );
              }
break;
case 97:
//#line 352 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCuerpo("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  FileHandler.appendToFile(filePathParser,"DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 98:
//#line 356 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 99:
//#line 357 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 100:
//#line 358 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 101:
//#line 359 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 102:
//#line 360 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 361 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 362 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 363 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 364 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 107:
//#line 365 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 108:
//#line 366 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 109:
//#line 367 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 110:
//#line 370 "gramatica.y"
{yyval = val_peek(1);}
break;
case 111:
//#line 371 "gramatica.y"
{yyval = val_peek(0);}
break;
case 112:
//#line 374 "gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 113:
//#line 375 "gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 378 "gramatica.y"
{yyval.obj = new NodoCondicion(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 115:
//#line 379 "gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 116:
//#line 380 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 117:
//#line 381 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 124:
//#line 392 "gramatica.y"
{yyval.obj = new NodoOUTF("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 125:
//#line 393 "gramatica.y"
{yyval.obj = new NodoOUTF("OUTF",new NodoConcreto(val_peek(2).sval, "CADENA"),null);}
break;
case 126:
//#line 394 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 127:
//#line 395 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 128:
//#line 396 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 129:
//#line 397 "gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 130:
//#line 400 "gramatica.y"
{  FileHandler.appendToFile(filePathParser,"SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                        yyval.obj = new NodoRepeat("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 131:
//#line 403 "gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 132:
//#line 404 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 133:
//#line 405 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 134:
//#line 406 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 135:
//#line 407 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 408 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 137:
//#line 411 "gramatica.y"
{
                                            if (estaDeclarado(val_peek(0).sval) == null){
                                                actualizarUso(val_peek(0).sval, "Struct"); ts.insertar(new TokenStruct( 257, nameMangling(val_peek(0).sval), val_peek(1).sval ));
                                            }else{
                                                yyerror(TIPO_REDEFINIDO);
                                                borrarSimbolos(val_peek(0).sval);
                                            }
                                            }
break;
case 138:
//#line 419 "gramatica.y"
{
                                            if (estaDeclarado(val_peek(0).sval) == null){
                                                actualizarUso(val_peek(0).sval, "Struct"); ts.insertar(new TokenStruct( 257, nameMangling(val_peek(0).sval), val_peek(1).sval ));
                                            }else{
                                                yyerror(TIPO_REDEFINIDO);
                                                borrarSimbolos(val_peek(0).sval);
                                            }
                                           }
break;
case 139:
//#line 427 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 140:
//#line 428 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 141:
//#line 431 "gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);  yyval.sval = val_peek(1).sval+"."+val_peek(4).sval;}
break;
case 142:
//#line 432 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 143:
//#line 433 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 144:
//#line 434 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 145:
//#line 437 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); yyval.sval = val_peek(1).sval+"."+ val_peek(4).sval;}
break;
case 146:
//#line 438 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 147:
//#line 439 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 148:
//#line 440 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 149:
//#line 445 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 150:
//#line 446 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 151:
//#line 449 "gramatica.y"
{
                        FileHandler.appendToFile(filePathParser, "SENTENCIA GOTO. Linea "+lex.getNumeroLinea());
                        errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:");
                        if (estaDeclarado(val_peek(1).sval) != null)
                            yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);
                        else
                            yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto("N/D"),null);
                        borrarSimbolos(val_peek(1).sval);
                     }
break;
case 152:
//#line 458 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 153:
//#line 459 "gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 154:
//#line 462 "gramatica.y"
{yyval.obj = new NodoTOS("TOS",(Nodo)val_peek(1).obj,null);}
break;
case 155:
//#line 463 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1730 "Parser.java"
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
