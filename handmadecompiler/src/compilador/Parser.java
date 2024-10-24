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






//#line 2 ".\gramatica.y"
    package compilador;
    import estructura_arbol.*;
  
//#line 21 "Parser.java"




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
public final static short ID_STRUCT=284;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    2,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    7,    7,    5,    5,    8,    8,
    8,    4,    4,    4,    4,    4,    4,    4,    4,   10,
   10,   18,   18,   18,   18,   18,   18,   19,   19,    6,
    6,    6,    6,    6,    6,    6,    6,   21,   21,   21,
   21,   21,   11,   11,   11,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   12,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   25,   25,   26,   26,   24,   24,   24,   24,   27,   27,
   27,   27,   27,   27,   14,   14,   14,   14,   14,   14,
   15,   15,   15,   15,   15,   15,   15,    9,    9,    9,
    9,   28,   28,   28,   28,   29,   29,   29,   29,   30,
   30,   16,   16,   16,   17,   17,   17,
};
final static short yylen[] = {                            2,
    4,    4,    4,    4,    1,    2,    1,    1,    3,    2,
    3,    3,    2,    2,    7,    2,    2,    3,    3,    2,
    2,    7,    7,    2,    3,    3,    1,    1,    2,    2,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    4,    4,    6,    6,    6,    4,    4,    3,
    7,    3,    5,    2,    6,    2,    4,    3,    3,    3,
    3,    3,    5,    5,    5,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    1,    1,    3,    1,    1,    1,    1,    2,
    4,    2,    2,    5,    5,    5,    8,   10,    8,   10,
    7,    7,    6,    9,    9,    8,    8,   10,    7,    9,
    3,    1,    2,    1,    3,    3,    3,    3,    1,    1,
    1,    1,    1,    1,    5,    5,    5,    5,    4,    5,
    7,    6,    7,    6,    6,    5,    7,    3,    3,    3,
    3,    7,    6,    6,    6,    7,    6,    5,    5,    3,
    3,    3,    3,    3,    5,    5,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   28,   27,    0,    0,    0,    5,
    7,    8,    0,    0,    0,    0,   32,   33,   34,   35,
   36,   37,   38,   39,   40,   41,    0,    0,   20,    0,
    0,   13,    0,    0,    0,    0,    0,    0,   86,   87,
   88,    0,    0,    0,   89,    0,    0,   83,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  112,    0,    0,    0,    0,   24,   10,    0,    4,    6,
    0,    0,    0,   21,    0,    0,   14,    0,    0,   17,
   16,    3,    2,    1,   54,    0,    0,    0,    0,    0,
   50,   12,  123,  124,  120,    0,    0,    0,    0,  119,
  121,  122,    0,    0,    0,   93,   90,   92,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  140,
  138,  141,  139,    0,    0,    0,  114,    0,    0,    0,
  154,  153,  152,    0,   19,   11,   26,   25,   18,    9,
    0,    0,    0,    0,    0,    0,    0,    0,   44,   43,
   42,    0,    0,    0,    0,    0,    0,    0,    0,   81,
   79,   82,   80,    0,    0,   85,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   77,   75,   78,   76,
    0,    0,    0,    0,    0,    0,  129,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  111,  113,    0,    0,    0,    0,   57,    0,    0,    0,
   49,   48,    0,    0,   31,    0,   30,   29,    0,   95,
   96,   94,    0,    0,    0,    0,   91,    0,    0,    0,
  157,  156,  155,  130,  128,  126,  127,  125,    0,    0,
    0,  151,  150,    0,    0,    0,    0,    0,   65,   64,
   63,    0,    0,  136,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   53,    0,    0,   55,    0,    0,    0,
    0,    0,    0,  103,    0,    0,    0,    0,    0,    0,
    0,  149,    0,    0,  148,    0,  135,    0,  134,  132,
   46,   47,   45,    0,    0,    0,   51,    0,  102,    0,
    0,    0,    0,  109,    0,    0,  101,    0,  145,    0,
  144,  147,  143,  137,  133,  131,   23,   22,   15,    0,
    0,  107,    0,   99,   97,  106,    0,  146,  142,  105,
    0,    0,  110,  104,  108,  100,   98,
};
final static short yydgoto[] = {                          3,
   19,   20,   21,   22,   23,   72,   25,  168,   26,   27,
   28,   55,   30,   31,   32,   33,   34,   35,   36,   56,
  164,   57,   58,   59,   73,  148,  113,   65,   66,  136,
};
final static short yysindex[] = {                       157,
 -227, -216,    0,  619,  619,  619,   14,  -27,    3,   15,
  334,   17,  484, -252,    0,    0,  -34,   28,  515,    0,
    0,    0, -206,  303,   36,   67,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  559,  415,    0,   59,
   79,    0,  109, -171, -160,  138,  217,   74,    0,    0,
    0,   86,  140, -228,    0,  163,   73,    0,    7,  181,
   50,  -46,  187,   61,  236,  253,  191, -131,  -14,  645,
    0,   -9,  -30,   84,   87,    0,    0, -104,    0,    0,
  376,  261,  324,    0,  129,  207,    0, -102, -237,    0,
    0,    0,    0,    0,    0,  607,   20,   42,   49,   63,
    0,    0,    0,    0,    0,  230,  245,  248,  263,    0,
    0,    0,  277,  -80,   40,    0,    0,    0,  153,  277,
  280,  293,  309,  319,  322,  537,  -78,  651,  581,  883,
  155,  146,  612,  187,  170,  -17,   31,    2,  103,    0,
    0,    0,    0,  892,  668,  329,    0,  639,   27,  140,
    0,    0,    0,  107,    0,    0,    0,    0,    0,    0,
  125,  123,  382,   77,  346,    6,  274,  357,    0,    0,
    0,  341,   89,  363,  196,  302,   73,  302,   73,    0,
    0,    0,    0,  898,   65,    0,  537,  200,  215,   65,
  302,   73,  302,   73,  898,   65,    0,    0,    0,    0,
  288,  537,  420,  115,  423,  233,    0,  364,    8,  270,
  187,  187,  232,  255,  257,  269,  278,  479,  372,  140,
    0,    0,  140,    9,  504,  333,    0,  277,  277,  358,
    0,    0,  277,  285,    0,  290,    0,    0,  305,    0,
    0,    0,  315,  536,  310,  501,    0,  537,  524,  328,
    0,    0,    0,    0,    0,    0,    0,    0,  330,  355,
  360,    0,    0,  355,  354,  359,  355,  362,    0,    0,
    0,  582,   21,    0,  570,  571,  873,  435,   65,   65,
  898,   65,   65,    0,  619,  462,    0,  374,  537,  588,
  338,   78,  379,    0,  537,  597,  383,   59,  101,  355,
  117,    0,  401,  132,    0,  602,    0,  381,    0,    0,
    0,    0,    0,  579,  403,  599,    0,  400,    0,  411,
  609,  537,  385,    0,  610,  409,    0,  418,    0,  287,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  620,
  431,    0,  -18,    0,    0,    0,  623,    0,    0,    0,
  644,  390,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -15,
    0,    0,    0,    0,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,    0,   68,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  148,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  214,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   90,   97,  119,  128,    0,
    0,    0,    0,    0,   12,    0,    0,    0,    0,   18,
  150,  159,  172,  194,   37,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  392,  422,
  444,  452,  454,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   11,  -16,    0,   -1,  505,    1,    0,    0,    0,    0,
    0,   -4,    0,    0,    0,    0,    0,    0,    0,  584,
    0,  657,  577,  509,  417,    0,  649,    0,    0,  -52,
};
final static int YYTABLESIZE=945;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
   29,   29,   80,   74,   24,   24,   24,   46,   29,  150,
  138,   71,   53,  134,   29,   37,   38,   54,  166,   24,
   80,   80,   75,   83,   77,   43,  212,  116,  117,   45,
    4,   44,   29,   29,   88,   15,   16,   24,   24,    5,
  353,    6,   60,   54,  213,  212,  236,  127,  118,  275,
   81,  211,  117,   43,   61,  119,   67,   45,  116,   44,
   82,  308,  121,  216,  122,   29,  223,  274,  147,  259,
  117,   54,   42,   78,  211,   89,  116,  118,  171,  307,
  188,  210,  172,  108,  106,  100,  107,  115,  109,  173,
  132,  121,  215,  122,   54,  118,  101,   84,   84,   84,
   84,   84,   45,   84,   44,  115,  175,  121,   74,  122,
   74,   74,   74,   43,  124,   84,   84,   84,   84,  125,
  233,   29,  139,   54,   71,   91,   74,   74,   74,   74,
   72,  114,   72,   72,   72,  232,  324,   70,  146,   70,
   70,   70,  151,   29,   88,  153,  222,  242,   72,   72,
   72,   72,  154,   54,  165,   70,   70,   70,   70,   73,
   88,   73,   73,   73,  108,  106,  228,  107,   71,  109,
   71,   71,   71,  253,  161,   88,  186,   73,   73,   73,
   73,   88,   29,  202,   54,   71,   71,   71,   71,   71,
   68,   56,   68,   68,   68,  206,  102,   29,  189,   66,
   71,   66,   66,   66,  207,  121,   56,  122,   68,   68,
   68,   68,   69,  211,   69,   69,   69,   66,   66,   66,
   66,   76,  111,  110,  112,   54,   15,   16,   47,   48,
   69,   69,   69,   69,   67,   54,   67,   67,   67,  149,
   54,   29,   95,   29,   71,  352,   71,   85,   49,   50,
   51,   54,   67,   67,   67,   67,   52,   52,  108,  106,
  299,  107,  235,  109,  301,   54,   41,  304,  126,   39,
   40,   86,   52,  117,   54,  170,  111,  110,  112,  116,
   29,   29,   47,   48,   29,   24,   24,   71,  214,   54,
   29,  256,   54,   71,   41,  314,  316,   80,  118,   80,
  330,  187,   49,   50,   51,  130,   48,   54,  115,   29,
   52,   29,   84,  212,   24,   95,   24,   29,   84,  174,
   71,   54,   90,   74,   54,   49,   50,   51,  131,   74,
   88,  261,  231,   52,   96,   48,   84,   54,   84,   84,
  322,  323,  152,  108,  241,   72,   88,   74,  109,   74,
   74,   72,   70,   54,   49,   50,   51,   85,   70,  329,
  217,   87,   52,   54,   98,   48,   54,   88,  220,   72,
  252,   72,   72,   85,   73,  331,   70,   54,   70,   70,
   73,  227,  160,   71,   49,   50,   51,  226,   85,   71,
  333,  234,   52,   63,   85,   47,   48,  239,   73,  240,
   73,   73,   54,   56,   56,   68,   56,   71,  243,   71,
   71,   68,    1,    2,   66,   49,   50,   51,  120,   45,
   66,   44,  258,   52,  121,  230,  122,   69,   56,   68,
  271,   68,   68,   69,  156,   61,  128,   48,   66,  336,
   66,   66,  103,  345,  104,  105,  144,   48,  357,   67,
   61,   69,  244,   69,   69,   67,   49,   50,   51,   15,
   16,  246,  162,   48,   52,   60,   49,   50,   51,   52,
   52,  247,   52,   67,   52,   67,   67,  121,  251,  122,
   60,  254,   49,   50,   51,  176,   48,   62,  255,  264,
   52,  140,  141,  313,   52,   58,  103,   59,  104,  105,
  178,   48,   62,  180,   48,   49,   50,   51,  142,  143,
   58,  265,   59,   52,  266,   64,  157,  158,  182,   48,
   49,   50,   51,   49,   50,   51,  267,  260,   52,  237,
  238,   52,  184,   48,  268,  191,   48,  269,   49,   50,
   51,  284,  201,   85,  276,  349,   52,  285,  193,   48,
  248,  249,   49,   50,   51,   49,   50,   51,   84,   85,
   52,  115,  286,   52,  195,   48,  135,  137,   49,   50,
   51,  287,  289,  290,  197,   48,   52,  199,   48,  159,
   85,  288,  294,   86,   49,   50,   51,  297,  277,   48,
  295,  296,   52,  167,   49,   50,   51,   49,   50,   51,
  320,  321,   52,  245,   62,   52,   15,   16,   49,   50,
   51,  298,  302,  281,   48,  303,   52,  300,  250,  257,
  305,  204,  306,  121,   97,  122,   99,  270,  309,  310,
  317,  155,   95,   49,   50,   51,  335,  229,  209,  328,
  344,   52,  325,  129,  133,  356,  319,   61,  108,  106,
  145,  107,  208,  109,  121,  327,  122,  224,  225,  332,
  334,  338,  292,  340,  293,  169,  341,  342,  346,  163,
   93,    7,  347,   94,    8,    9,  348,   60,  350,   10,
   11,  354,   12,   13,  181,  183,   14,   15,   16,   17,
  312,  203,  108,  106,  351,  107,  185,  109,   18,   62,
  198,  200,  355,  190,  123,  318,  196,   58,  219,   59,
  121,  326,  122,    0,    0,  262,  263,  315,    7,    0,
    0,    8,    9,    0,    0,    0,   10,   11,  272,   12,
   13,  273,    0,   14,   15,   16,   17,    0,  343,   68,
   69,   70,    0,    8,    9,   18,    0,    0,   10,    0,
    0,   12,   13,    0,    0,   14,  291,   69,   70,    0,
    8,    9,  177,  179,    0,   10,    0,   18,   12,   13,
    0,    7,   14,   79,    8,    9,    0,  192,  194,   10,
   11,    0,   12,   13,   18,    0,   14,   15,   16,   17,
    0,    0,    0,   69,   70,    0,    8,    9,   18,    0,
    0,   10,    0,    0,   12,   13,    0,    0,   14,  278,
    0,  279,  280,  282,    0,    7,  283,   92,    8,    9,
   18,    0,    0,   10,   11,    0,   12,   13,    0,    0,
   14,   15,   16,   17,    0,    7,    0,  337,    8,    9,
    0,    0,   18,   10,   11,    0,   12,   13,    0,    0,
   14,   15,   16,   17,    0,    7,    0,  339,    8,    9,
    0,    0,   18,   10,   11,    0,   12,   13,    0,    0,
   14,   15,   16,   17,    0,    7,    0,    0,    8,    9,
    0,    0,   18,   10,   11,    0,   12,   13,    0,    0,
   14,   15,   16,   17,    0,   69,    0,  221,    8,    9,
    0,   69,   18,   10,    8,    9,   12,   13,    0,   10,
   14,    0,   12,   13,  108,  106,   14,  107,    0,  109,
    0,    0,   18,  205,  108,  106,    0,  107,   18,  109,
    0,  311,  218,  108,  106,    0,  107,    0,  109,  108,
  106,    0,  107,    0,  109,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
    5,    6,   19,  256,    4,    5,    6,    7,   13,   40,
   63,   13,   40,   60,   19,    5,    6,   45,  256,   19,
   37,   38,  275,   23,   59,   40,   44,  256,  257,   44,
  258,   46,   37,   38,   44,  273,  274,   37,   38,  256,
   59,  258,   40,   59,   62,   44,   41,   41,  277,   41,
  257,   44,   41,   40,   40,  284,   40,   44,   41,   46,
  267,   41,   43,   62,   45,   70,   40,   59,   70,   62,
   59,   45,   59,   46,   44,   40,   59,   41,   59,   59,
   41,  134,   41,   42,   43,  257,   45,   41,   47,   41,
   41,   43,   62,   45,   45,   59,  257,   41,   42,   43,
   44,   45,   44,   47,   46,   59,   44,   43,   41,   45,
   43,   44,   45,   40,   42,   59,   60,   61,   62,   47,
   44,  126,   62,   45,  126,   59,   59,   60,   61,   62,
   41,   46,   43,   44,   45,   59,   59,   41,  270,   43,
   44,   45,   59,  148,   44,   59,  148,   59,   59,   60,
   61,   62,  257,   45,  257,   59,   60,   61,   62,   41,
   44,   43,   44,   45,   42,   43,   44,   45,   41,   47,
   43,   44,   45,   59,   46,   44,  257,   59,   60,   61,
   62,   44,  187,  262,   45,  187,   59,   60,   61,   62,
   41,   44,   43,   44,   45,   41,   59,  202,   46,   41,
  202,   43,   44,   45,   59,   43,   59,   45,   59,   60,
   61,   62,   41,   44,   43,   44,   45,   59,   60,   61,
   62,  256,   60,   61,   62,   45,  273,  274,  256,  257,
   59,   60,   61,   62,   41,   45,   43,   44,   45,  270,
  256,  246,  257,  248,  246,  264,  248,  257,  276,  277,
  278,   45,   59,   60,   61,   62,  284,   44,   42,   43,
  260,   45,  257,   47,  264,  281,  281,  267,  262,  256,
  257,  281,   59,  262,   45,  256,   60,   61,   62,  262,
  285,  286,  256,  257,  289,  285,  286,  289,  258,   45,
  295,   59,   45,  295,  281,  285,  286,  314,  262,  316,
  300,  262,  276,  277,  278,  256,  257,   45,  262,  314,
  284,  316,  256,   44,  314,  257,  316,  322,  262,  257,
  322,   45,  256,  256,   45,  276,  277,  278,  279,  262,
   44,   62,  256,  284,  256,  257,  280,   45,  282,  283,
  263,  264,  256,   42,  256,  256,   44,  280,   47,  282,
  283,  262,  256,   45,  276,  277,  278,  257,  262,  259,
  258,   59,  284,   45,  256,  257,   45,   44,   40,  280,
  256,  282,  283,  257,  256,  259,  280,   45,  282,  283,
  262,  257,   59,  256,  276,  277,  278,  281,  257,  262,
  259,   46,  284,   60,  257,  256,  257,   41,  280,   59,
  282,  283,   45,  256,  257,  256,  259,  280,   46,  282,
  283,  262,  256,  257,  256,  276,  277,  278,  256,   44,
  262,   46,   59,  284,   43,   44,   45,  256,  281,  280,
   59,  282,  283,  262,   59,   44,  256,  257,  280,   59,
  282,  283,  280,   59,  282,  283,  256,  257,   59,  256,
   59,  280,  257,  282,  283,  262,  276,  277,  278,  273,
  274,  262,  256,  257,  284,   44,  276,  277,  278,  256,
  257,  257,  259,  280,  284,  282,  283,   43,   59,   45,
   59,   59,  276,  277,  278,  256,  257,   44,  256,  258,
  284,  256,  257,   59,  281,   44,  280,   44,  282,  283,
  256,  257,   59,  256,  257,  276,  277,  278,  256,  257,
   59,  257,   59,  284,  258,   11,  256,  257,  256,  257,
  276,  277,  278,  276,  277,  278,  258,  258,  284,  256,
  257,  284,  256,  257,  257,  256,  257,   59,  276,  277,
  278,  257,  126,  257,   41,  259,  284,  258,  256,  257,
  263,  264,  276,  277,  278,  276,  277,  278,  256,  257,
  284,   53,  258,  284,  256,  257,   62,   63,  276,  277,
  278,  257,  263,  264,  256,  257,  284,  256,  257,  256,
  257,   46,   59,  281,  276,  277,  278,  258,  256,  257,
  263,  264,  284,   89,  276,  277,  278,  276,  277,  278,
  263,  264,  284,  187,  271,  284,  273,  274,  276,  277,
  278,  257,  259,  256,  257,  257,  284,  258,  202,  256,
  259,   41,   41,   43,   41,   45,   43,  256,   59,   59,
  257,  256,  257,  276,  277,  278,  256,  256,  134,  257,
  256,  284,  264,   60,   61,  256,   59,  256,   42,   43,
   67,   45,   41,   47,   43,   59,   45,  149,  150,  259,
   59,  259,  246,  264,  248,   59,  256,   59,   59,   86,
  256,  257,  264,  259,  260,  261,  259,  256,   59,  265,
  266,   59,  268,  269,  108,  109,  272,  273,  274,  275,
  256,   41,   42,   43,  264,   45,  113,   47,  284,  256,
  124,  125,   59,  120,   56,  289,  123,  256,   41,  256,
   43,  295,   45,   -1,   -1,  211,  212,  256,  257,   -1,
   -1,  260,  261,   -1,   -1,   -1,  265,  266,  220,  268,
  269,  223,   -1,  272,  273,  274,  275,   -1,  322,  256,
  257,  258,   -1,  260,  261,  284,   -1,   -1,  265,   -1,
   -1,  268,  269,   -1,   -1,  272,  256,  257,  258,   -1,
  260,  261,  106,  107,   -1,  265,   -1,  284,  268,  269,
   -1,  257,  272,  259,  260,  261,   -1,  121,  122,  265,
  266,   -1,  268,  269,  284,   -1,  272,  273,  274,  275,
   -1,   -1,   -1,  257,  258,   -1,  260,  261,  284,   -1,
   -1,  265,   -1,   -1,  268,  269,   -1,   -1,  272,  226,
   -1,  228,  229,  230,   -1,  257,  233,  259,  260,  261,
  284,   -1,   -1,  265,  266,   -1,  268,  269,   -1,   -1,
  272,  273,  274,  275,   -1,  257,   -1,  259,  260,  261,
   -1,   -1,  284,  265,  266,   -1,  268,  269,   -1,   -1,
  272,  273,  274,  275,   -1,  257,   -1,  259,  260,  261,
   -1,   -1,  284,  265,  266,   -1,  268,  269,   -1,   -1,
  272,  273,  274,  275,   -1,  257,   -1,   -1,  260,  261,
   -1,   -1,  284,  265,  266,   -1,  268,  269,   -1,   -1,
  272,  273,  274,  275,   -1,  257,   -1,  259,  260,  261,
   -1,  257,  284,  265,  260,  261,  268,  269,   -1,  265,
  272,   -1,  268,  269,   42,   43,  272,   45,   -1,   47,
   -1,   -1,  284,   41,   42,   43,   -1,   45,  284,   47,
   -1,   59,   41,   42,   43,   -1,   45,   -1,   47,   42,
   43,   -1,   45,   -1,   47,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=284;
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
"MENOR_IGUAL","ASIGNACION","MAYOR_IGUAL","DISTINTO","ID_STRUCT",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID BEGIN lista_sentencias END",
"programa : ID BEGIN lista_sentencias error",
"programa : ID error lista_sentencias END",
"programa : error BEGIN lista_sentencias END",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_variables ';'",
"sentencia_declarativa : TAG ';'",
"sentencia_declarativa : tipo ID ';'",
"sentencia_declarativa : ID lista_variables ';'",
"sentencia_declarativa : ID ';'",
"sentencia_declarativa : lista_variables ';'",
"sentencia_declarativa : header '(' parametro ')' BEGIN lista_sentencias END",
"sentencia_declarativa : struct ';'",
"sentencia_declarativa : struct error",
"sentencia_declarativa : tipo lista_variables error",
"sentencia_declarativa : tipo ID error",
"sentencia_declarativa : ID error",
"sentencia_declarativa : lista_variables error",
"sentencia_declarativa : header '(' parametro ')' BEGIN error END",
"sentencia_declarativa : header '(' error ')' BEGIN lista_sentencias END",
"sentencia_declarativa : TAG error",
"header : tipo FUN ID",
"header : tipo FUN error",
"tipo : UINTEGER",
"tipo : SINGLE",
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
"sentencia_ejecutable : conversion_explicita",
"asignacion : asignacion_simple",
"asignacion : asignacion_multiple",
"asignacion_simple : ID ASIGNACION expresion ';'",
"asignacion_simple : ID ASIGNACION expresion error",
"asignacion_simple : ID ASIGNACION error ';'",
"asignacion_simple : ID_STRUCT '.' ID ASIGNACION expresion ';'",
"asignacion_simple : ID_STRUCT '.' ID ASIGNACION error ';'",
"asignacion_simple : ID_STRUCT '.' ID ASIGNACION expresion error",
"asignacion_multiple : lista_variables ASIGNACION lista_expresiones ';'",
"asignacion_multiple : lista_variables ASIGNACION lista_expresiones error",
"lista_variables : ID ',' ID",
"lista_variables : ID '.' ID ',' ID '.' ID",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ',' ID '.' ID",
"lista_variables : ID ID",
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
"factor : ID_STRUCT '.' ID",
"factor : UINTEGER_CONST",
"factor : SINGLE_CONST",
"factor : HEXA_CONST",
"factor : invocacion_funcion",
"factor : '-' ID",
"factor : '-' ID_STRUCT '.' ID",
"factor : '-' SINGLE_CONST",
"factor : '-' error",
"invocacion_funcion : ID '(' expresion ')' ';'",
"invocacion_funcion : ID '(' error ')' ';'",
"invocacion_funcion : ID '(' expresion ')' error",
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
"conversion_explicita : TOS '(' expresion ')' ';'",
"conversion_explicita : TOS '(' expresion ')' error",
"conversion_explicita : TOS '(' error ')' ';'",
};

//#line 299 ".\gramatica.y"
  
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

    void actualizarTipoDelGrupo(String tipo, String grupoVariable) {
        String[] variables = grupoVariable.split(",");
        for (String variable : variables) {
            if (tipoEmbebido(variable)) // Si es tipo embebido, chequeamos redeclaracion de tipos
                chequeoTipo(variable,tipo);
            else
                actualizarTipo(variable, tipo);
        }
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
            System.err.println(""+mensajeError + lexema);
    }

    void chequeoTipo(String nombre, String tipo) {
        if ((nombre.charAt(0) == 's') && tipo.equals("uinteger") ) {
            ts.actualizarTipo(nombre, "UINTEGER");
            System.out.println("Redeclaracion de variable "+nombre+" como UINTEGER. Linea "+lex.getNumeroLinea());
        } else if ((nombre.charAt(0) == 'u' || nombre.charAt(0) == 'v' || nombre.charAt(0) == 'w') && tipo.equals("single") ) {
            ts.actualizarTipo(nombre, "SINGLE");
            System.out.println("Redeclaracion de variable "+nombre+" como SINGLE. Linea "+lex.getNumeroLinea());
        }
    }
//#line 774 "Parser.java"
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
//#line 11 ".\gramatica.y"
{
              Nodo programa = new NodoCompuesto("programa",(Nodo)val_peek(3).obj, (Nodo)val_peek(1).obj);
              System.out.println(programa.toString());  /* Imprime el árbol sintáctico completo*/
              yyval.obj = programa;  /* Almacena el nodo en ParserVal*/
              actualizarTipo(val_peek(3).sval, "NOMBRE_PROGRAMA"); /* Actualiza el tipo de la variable que se genera con el nombre del programa, puede servir a futuro..*/
              actualizarUso(val_peek(3).sval, "NOMBRE_PROGRAMA");
          }
break;
case 2:
//#line 18 ".\gramatica.y"
{ yyerror(ERROR_END); }
break;
case 3:
//#line 19 ".\gramatica.y"
{ yyerror(ERROR_BEGIN); }
break;
case 4:
//#line 20 ".\gramatica.y"
{ yyerror(ERROR_NOMBRE_PROGRAMA); }
break;
case 5:
//#line 25 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 6:
//#line 26 ".\gramatica.y"
{ yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj); }
break;
case 7:
//#line 29 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 8:
//#line 30 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 9:
//#line 33 ".\gramatica.y"
{actualizarTipoDelGrupo(val_peek(2).sval, val_peek(1).sval);}
break;
case 11:
//#line 35 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");
                                    if (tipoEmbebido(val_peek(1).sval))
                                        chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                    else
                                        actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                }
break;
case 13:
//#line 42 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 15:
//#line 44 ".\gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);/* Uso delimitador para las funciones*/
                                                                                yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador);
                                                                                    
                                                                                     }
break;
case 17:
//#line 50 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 18:
//#line 51 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 19:
//#line 52 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 20:
//#line 53 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 21:
//#line 54 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 22:
//#line 55 ".\gramatica.y"
{yyerror(ERROR_RET);}
break;
case 23:
//#line 56 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 24:
//#line 57 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 60 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Funcion"); 
                        errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                        actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                        yyval.sval = val_peek(0).sval;
                        }
break;
case 26:
//#line 65 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 29:
//#line 71 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");}
break;
case 30:
//#line 72 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 31:
//#line 73 ".\gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 42:
//#line 90 ".\gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 43:
//#line 94 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 44:
//#line 95 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 46:
//#line 97 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 47:
//#line 98 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 48:
//#line 101 ".\gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);}
break;
case 49:
//#line 103 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 50:
//#line 106 ".\gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval; 
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 52:
//#line 110 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 54:
//#line 114 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 55:
//#line 115 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 116 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 117 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 120 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 59:
//#line 121 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 60:
//#line 122 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 61:
//#line 124 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 62:
//#line 125 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 129 ".\gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 64:
//#line 131 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 65:
//#line 132 ".\gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 66:
//#line 135 ".\gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 67:
//#line 139 ".\gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 68:
//#line 143 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 69:
//#line 144 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 145 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 146 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 147 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 148 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 149 ".\gramatica.y"
{ yyval = val_peek(0);  }
break;
case 75:
//#line 152 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 76:
//#line 156 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 160 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 78:
//#line 161 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 162 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 163 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 164 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 165 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 166 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 84:
//#line 169 ".\gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 85:
//#line 172 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct*/
        }
break;
case 86:
//#line 175 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante UINTEGER*/
         }
break;
case 87:
//#line 178 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante SINGLE*/
         }
break;
case 88:
//#line 181 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante HEXA*/
         }
break;
case 90:
//#line 185 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable negativa*/
        }
break;
case 91:
//#line 188 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct negativa*/
        }
break;
case 92:
//#line 191 ".\gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval); System.out.println("CONSTANTE SINGLE NEGATIVA: " + "-"+val_peek(0).sval);}
break;
case 93:
//#line 192 ".\gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 94:
//#line 195 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);}
break;
case 95:
//#line 196 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 96:
//#line 197 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 97:
//#line 200 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null)); /* No es necesario nodo de control "CUERPO" porque el camino es solo del THEN. MIRAR FILMINAS 14 DEL PAQUETE 08.3 (basado en eso para crear la estructura del arbol adecuado reutilizando clases por patron composite)*/
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 98:
//#line 204 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 99:
//#line 208 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 100:
//#line 209 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 101:
//#line 210 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 102:
//#line 211 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 212 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 213 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 214 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 215 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 107:
//#line 216 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 108:
//#line 217 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 109:
//#line 218 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 110:
//#line 219 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 111:
//#line 222 ".\gramatica.y"
{yyval = val_peek(1);}
break;
case 112:
//#line 223 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 113:
//#line 226 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 114:
//#line 227 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 115:
//#line 230 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 116:
//#line 231 ".\gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 117:
//#line 232 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 118:
//#line 233 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 125:
//#line 244 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 126:
//#line 245 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 127:
//#line 246 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 128:
//#line 247 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 129:
//#line 248 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 130:
//#line 249 ".\gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 131:
//#line 252 ".\gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 132:
//#line 256 ".\gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 133:
//#line 257 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 134:
//#line 258 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 135:
//#line 259 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 260 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 137:
//#line 261 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 138:
//#line 264 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct"); }
break;
case 139:
//#line 265 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 140:
//#line 266 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 141:
//#line 267 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 142:
//#line 270 ".\gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);}
break;
case 143:
//#line 271 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 144:
//#line 272 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 145:
//#line 273 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 146:
//#line 276 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 147:
//#line 277 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 148:
//#line 278 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 149:
//#line 279 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 150:
//#line 284 ".\gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 151:
//#line 285 ".\gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 152:
//#line 288 ".\gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 153:
//#line 289 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 154:
//#line 290 ".\gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 155:
//#line 293 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(2).obj,null);}
break;
case 156:
//#line 294 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 157:
//#line 295 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1510 "Parser.java"
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
