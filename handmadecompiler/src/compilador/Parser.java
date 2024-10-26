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
    import java.util.ArrayList;
  
//#line 22 "Parser.java"




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
    0,    0,    0,    0,    1,    2,    2,    3,    3,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,   11,    8,    6,    6,    6,    9,
    9,    9,    5,    5,    5,    5,    5,    5,    5,    5,
   12,   12,   20,   20,   20,   20,   20,   20,   21,   21,
    7,    7,    7,    7,    7,    7,    7,    7,   23,   23,
   23,   23,   23,   13,   13,   13,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   14,   14,   14,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   27,   27,   28,   28,   26,   26,   26,   26,   29,
   29,   29,   29,   29,   29,   16,   16,   16,   16,   16,
   16,   17,   17,   17,   17,   17,   17,   17,   10,   10,
   10,   10,   30,   30,   30,   30,   31,   31,   31,   31,
   32,   32,   18,   18,   18,   19,   19,   19,
};
final static short yylen[] = {                            2,
    3,    3,    4,    4,    2,    1,    2,    1,    1,    3,
    2,    3,    2,    2,    7,    7,    7,    2,    3,    3,
    2,    2,    2,    2,    0,    7,    1,    1,    1,    2,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    4,    4,    6,    6,    6,    4,    4,
    3,    7,    3,    5,    2,    6,    2,    4,    3,    3,
    3,    3,    3,    5,    5,    5,    3,    3,    3,    3,
    3,    3,    3,    3,    1,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    1,    3,    1,    1,    1,    1,
    2,    4,    2,    2,    5,    5,    5,    8,   10,    8,
   10,    7,    7,    6,    9,    9,    8,    8,   10,    7,
    9,    3,    1,    2,    1,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    1,    5,    5,    5,    5,    4,
    5,    7,    6,    7,    6,    6,    5,    7,    3,    3,
    3,    3,    7,    6,    6,    6,    7,    6,    5,    5,
    3,    3,    3,    3,    3,    5,    5,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    5,    0,    0,    0,
    0,    0,    0,    0,    0,   28,   27,    0,    0,    0,
    6,    8,    9,    0,    0,    0,    0,   33,   34,   35,
   36,   37,   38,   39,   40,   41,   42,    0,    0,   21,
   55,    0,   13,    0,    0,    0,    0,   87,   88,   89,
    0,    0,    0,   90,    0,    0,   84,    0,    0,    0,
    0,   29,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  113,    0,    0,    0,    0,   24,   11,    0,    2,
    1,    7,    0,    0,    0,    0,   22,   57,    0,    0,
   14,    0,    0,   23,   18,    4,    3,    0,    0,    0,
    0,   51,  124,  125,  121,    0,    0,    0,    0,  120,
  122,  123,    0,    0,    0,   94,   91,   93,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  141,
  139,  142,  140,    0,    0,    0,  115,    0,    0,    0,
  155,  154,  153,    0,   20,   12,   25,    0,   19,   10,
    0,    0,    0,    0,   53,    0,    0,    0,    0,   45,
   44,   43,    0,    0,    0,    0,    0,    0,   82,   80,
   83,   81,    0,    0,   86,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   78,   76,   79,   77,    0,
    0,    0,    0,    0,    0,  130,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  112,
  114,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   50,   49,    0,   58,    0,   32,    0,   31,
   30,    0,   96,   97,   95,    0,    0,   92,    0,    0,
    0,  158,  157,  156,  131,  129,  127,  128,  126,    0,
    0,    0,  152,  151,    0,    0,    0,    0,    0,   66,
   65,   64,    0,    0,  137,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   54,    0,    0,
    0,    0,    0,    0,    0,  104,    0,    0,    0,    0,
    0,    0,    0,  150,    0,    0,  149,    0,  136,    0,
  135,  133,   47,   48,   46,   56,    0,    0,    0,    0,
    0,    0,  103,    0,    0,    0,    0,  110,    0,    0,
  102,    0,  146,    0,  145,  148,  144,  138,  134,  132,
   52,   26,   17,   16,   15,    0,    0,  108,    0,  100,
   98,  107,    0,  147,  143,  106,    0,    0,  111,  105,
  109,  101,   99,
};
final static short yydgoto[] = {                          3,
    4,   20,   21,   22,   23,   24,   73,   26,  169,   27,
  228,   28,   29,   54,   31,   32,   33,   34,   35,   36,
   37,   55,  163,   56,   57,   58,   74,  148,  113,   65,
   66,  136,
};
final static short yysindex[] = {                      -127,
 -226, -210,    0,  618,  618,  618,    0,   13,  -27,    2,
    7,  120,   12,  510, -135,    0,    0,  -38,   34,  455,
    0,    0,    0, -170,  151,   96,  -19,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  536,  556,    0,
    0,  174,    0,  207, -221,  166,  132,    0,    0,    0,
   74,  211, -202,    0,  -17,  418,    0,  -21,  237,   47,
  127,    0,  142,  124,  -42,  -10,  246, -231,  -13,  658,
   34,    0,   54,  -11,  117,  -18,    0,    0,  -64,    0,
    0,    0,  172,  -53,  186,   24,    0,    0,  269,  256,
    0, -158,  197,    0,    0,    0,    0,  886,   22,  463,
  306,    0,    0,    0,    0,  282,  292,  308,  321,    0,
    0,    0,  334,  -45,    9,    0,    0,    0,  263,  334,
  338,  350,  364,  374,  388,  638,   67,  905,  499,  912,
  295,  280,  509,  142,  304,   46,   40,   56,  110,    0,
    0,    0,    0,  919,  530,  325,    0,  652,  -23,  211,
    0,    0,    0,   29,    0,    0,    0,  149,    0,    0,
  490,   60,  344,  177,    0,  335,  -32,  181,  358,    0,
    0,    0,  395,   27,  435,  418,  435,  418,    0,    0,
    0,    0,  714,  153,    0,  638,  204,  212,  153,  435,
  418,  435,  418,  714,  153,    0,    0,    0,    0,  176,
  638,  416,   90,  427,  101,    0,  119,   80,   63,  142,
  142,  232,  250,  234,  251,  271,  477,  158,  211,    0,
    0,  211,  118,  479,  398,  495,  259,  142,  -36,  334,
  334,  411,    0,    0,  334,    0,  294,    0,  297,    0,
    0,  299,    0,    0,    0,  215,  516,    0,  638,  504,
  235,    0,    0,    0,    0,    0,    0,    0,    0,  309,
 -156,  314,    0,    0, -156,  315,  322, -156,  329,    0,
    0,    0,  540,  129,    0,  534,  537,  892,  382,  332,
  555,  336,  153,  153,  714,  153,  153,    0,  618,  476,
  638,  545,  255,   11,  345,    0,  638,  549,  356,    5,
   83, -156,  113,    0,  360,  114,    0,  558,    0,  161,
    0,    0,    0,    0,    0,    0,  366,  373,  576,  376,
  596,  368,    0,  380,  578,  638,  199,    0,  580,  379,
    0,  387,    0,  121,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  590,  389,    0,  -29,    0,
    0,    0,  597,    0,    0,    0,  598,  217,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -161,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,   50,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   72,   94,  102,  107,    0,    0,
    0,    0,    0,  -26,    0,    0,    0,    0,   10,  130,
  140,  162,  300,   15,   30,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  377,  403,  413,  415,  417,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    6,  -14,    0,   -7,  468,   -1,    0,    0,    0,
    0,    0,    0,   -4,    0,    0,    0,    0,    0,    0,
    0,  642,    0,  390,  392,  475,  461,    0,  605,    0,
    0,  -49,
};
final static int YYTABLESIZE=966;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
   30,   30,   25,   25,   25,   82,   72,  227,  239,   30,
   38,   39,   52,  138,  118,   30,  222,   53,   25,  127,
   78,   53,   86,   82,   82,  121,   44,  122,  150,  359,
   45,    5,  118,   30,   30,  102,   25,   25,  146,   95,
  153,   59,  111,  110,  112,    6,   60,    7,   45,  187,
  117,   67,   44,  116,  117,  119,   45,   85,   85,   85,
   85,   85,  147,   85,  121,   30,  122,   92,  117,  328,
  116,   43,  227,  119,  118,   85,   85,   85,   85,   79,
  172,  119,  160,  210,  209,  245,   83,  132,  116,  211,
   75,   53,   75,   75,   75,   29,   84,   92,  165,  211,
  300,  214,  121,  232,  122,   29,  211,  212,   75,   75,
   75,   75,   73,   85,   73,   73,   73,  215,   72,  114,
   75,   30,   29,  210,  262,  166,   92,   85,    1,    2,
   73,   73,   73,   73,   71,   93,   71,   71,   71,   76,
  221,  260,   74,   30,   74,   74,   74,   72,  254,   72,
   72,   72,   71,   71,   71,   71,   92,   92,  276,  257,
   74,   74,   74,   74,   92,   72,   72,   72,   72,  310,
   69,   44,   69,   69,   69,  151,  275,  259,   72,   63,
   67,   30,   67,   67,   67,  139,  134,  309,   69,   69,
   69,   69,  154,   72,   92,  121,   30,  122,   67,   67,
   67,   67,   70,  157,   70,   70,   70,  108,  106,   91,
  107,  185,  109,  140,  141,   45,  272,   77,   53,  340,
   70,   70,   70,   70,  238,  111,  110,  112,   46,   47,
  156,  158,   46,   47,  358,  118,   94,  152,  120,   72,
  126,   72,   30,   41,   30,  142,  143,  226,   48,   49,
   50,   53,   48,   49,   50,   53,   51,  351,  149,  301,
   51,   41,  103,  303,  104,  105,  306,   42,   40,   41,
  186,  117,   85,  326,  327,  363,  119,  171,   85,  159,
   88,   53,  244,   72,   30,   30,   30,   25,   25,   72,
   53,  116,   30,   42,  319,  321,   85,  213,   85,   85,
  334,  164,  130,   47,   82,   75,   82,   90,  188,  225,
   88,   75,  226,   53,   30,  231,   30,   25,   72,   25,
  261,   30,   48,   49,   50,  131,   53,   73,  201,   75,
   51,   75,   75,   73,   89,  205,   53,   90,  206,   88,
   68,  333,   68,   68,   68,  253,  174,  210,  121,   71,
  122,   73,   53,   73,   73,   71,  256,   74,   68,   68,
   68,   68,   72,   74,  219,   53,   90,  216,   72,   88,
   88,  335,  337,   71,  258,   71,   71,   88,   53,  355,
  237,   74,   53,   74,   74,   69,   72,  235,   72,   72,
   61,   69,   16,   17,   53,   67,   90,   90,  242,   16,
   17,   67,  234,   62,   90,  229,   87,   88,   53,   69,
   62,   69,   69,  271,   16,   17,  339,   70,   53,   67,
   62,   67,   67,   70,  121,   62,  122,  155,   41,   98,
   47,   89,   53,  236,   90,   62,  240,  241,  249,  250,
  315,   70,   53,   70,   70,  103,   61,  104,  105,   48,
   49,   50,  167,  243,  350,   53,   63,   51,   59,  124,
   60,   61,  100,   47,  125,  247,   46,   47,  248,   16,
   17,   63,  362,   59,  252,   60,  108,  291,  292,   64,
   62,  109,   48,   49,   50,  255,   48,   49,   50,  265,
   51,  267,  128,   47,   51,  176,  178,  297,  298,  180,
  182,  144,   47,  173,  108,  106,  266,  107,  268,  109,
  191,  193,   48,   49,   50,  197,  199,  324,  325,  277,
   51,   48,   49,   50,  161,   47,  115,  269,  135,   51,
  137,  108,  106,  230,  107,  270,  109,  175,   47,  203,
  280,  121,  281,  122,   48,   49,   50,  177,   47,  207,
  288,  121,   51,  122,  289,   68,  290,   48,   49,   50,
  168,   68,  296,  179,   47,   51,  299,   48,   49,   50,
  218,  302,  121,  304,  122,   51,  181,   47,  305,   68,
  308,   68,   68,   48,   49,   50,  200,  307,  316,  183,
   47,   51,  311,  190,   47,  312,   48,   49,   50,  233,
  317,  208,  318,  323,   51,  192,   47,  331,  329,   48,
   49,   50,  332,   48,   49,   50,  338,   51,  336,  194,
   47,   51,  341,  223,  224,   48,   49,   50,  342,  196,
   47,  346,   62,   51,  344,  347,  348,  314,  352,   48,
   49,   50,  353,  198,   47,  354,  246,   51,  356,   48,
   49,   50,  357,  278,   47,  360,  361,   51,   61,  123,
    0,  251,    0,   48,   49,   50,  285,   47,   63,    0,
   59,   51,   60,   48,   49,   50,    0,  263,  264,    0,
    0,   51,    0,   99,    0,  101,   48,   49,   50,    0,
    0,    0,    0,  273,   51,  282,  274,    0,    0,    0,
  129,  133,    0,    0,    0,    0,    0,  294,  145,  295,
   80,    8,    0,   81,    9,   10,    0,    0,    0,   11,
   12,    0,   13,   14,    0,    0,   15,   16,   17,   18,
  162,  320,    8,    0,    0,    9,   10,    0,   19,    0,
   11,   12,    0,   13,   14,    0,    0,   15,   16,   17,
   18,  322,    0,    0,  184,  108,  106,  330,  107,   19,
  109,  189,    0,    0,  195,   68,   69,   70,    0,    9,
   10,  293,   69,   70,   11,    9,   10,   13,   14,    0,
   11,   15,    0,   13,   14,    0,  349,   15,    0,    0,
    0,    0,    8,   71,   96,    9,   10,    0,    0,   71,
   11,   12,    0,   13,   14,    0,    0,   15,   16,   17,
   18,    0,    8,    0,   97,    9,   10,    0,    0,   19,
   11,   12,    0,   13,   14,    0,    0,   15,   16,   17,
   18,    0,    8,    0,  343,    9,   10,    0,    0,   19,
   11,   12,    0,   13,   14,    0,    0,   15,   16,   17,
   18,    0,    8,    0,  345,    9,   10,    0,    0,   19,
   11,   12,    0,   13,   14,    0,  279,   15,   16,   17,
   18,  283,  284,  286,    8,    0,  287,    9,   10,   19,
    0,    0,   11,   12,    0,   13,   14,    0,    0,   15,
   16,   17,   18,    0,   69,   70,    0,    9,   10,    0,
    0,   19,   11,    0,    0,   13,   14,    0,   69,   15,
  220,    9,   10,    0,   69,    0,   11,    9,   10,   13,
   14,   71,   11,   15,    0,   13,   14,  108,  106,   15,
  107,    0,  109,  108,  106,   71,  107,    0,  109,    0,
    0,   71,    0,    0,  170,  202,  108,  106,    0,  107,
  313,  109,  204,  108,  106,    0,  107,    0,  109,  217,
  108,  106,    0,  107,    0,  109,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
    5,    6,    4,    5,    6,   20,   14,   44,   41,   14,
    5,    6,   40,   63,   41,   20,   40,   45,   20,   41,
   59,   45,   24,   38,   39,   43,   40,   45,   40,   59,
   44,  258,   59,   38,   39,  257,   38,   39,  270,   59,
   59,   40,   60,   61,   62,  256,   40,  258,   44,   41,
   41,   40,   40,  256,  257,   41,   44,   41,   42,   43,
   44,   45,   70,   47,   43,   70,   45,   44,   59,   59,
   41,   59,   44,   59,  277,   59,   60,   61,   62,   46,
   59,  284,   59,   44,  134,   59,  257,   41,   59,   44,
   41,   45,   43,   44,   45,  257,  267,   44,  257,   44,
  257,   62,   43,   44,   45,  267,   44,   62,   59,   60,
   61,   62,   41,  284,   43,   44,   45,   62,  126,   46,
  256,  126,  284,   44,   62,  284,   44,  284,  256,  257,
   59,   60,   61,   62,   41,   40,   43,   44,   45,  275,
  148,   62,   41,  148,   43,   44,   45,   41,   59,   43,
   44,   45,   59,   60,   61,   62,   44,   44,   41,   59,
   59,   60,   61,   62,   44,   59,   60,   61,   62,   41,
   41,   40,   43,   44,   45,   59,   59,   59,  186,   60,
   41,  186,   43,   44,   45,   62,   60,   59,   59,   60,
   61,   62,  257,  201,   44,   43,  201,   45,   59,   60,
   61,   62,   41,  257,   43,   44,   45,   42,   43,   59,
   45,  257,   47,  256,  257,   44,   59,  256,   45,   59,
   59,   60,   61,   62,  257,   60,   61,   62,  256,  257,
   59,   46,  256,  257,  264,  262,  256,  256,  256,  247,
  262,  249,  247,  257,  249,  256,  257,  284,  276,  277,
  278,   45,  276,  277,  278,   45,  284,   59,  270,  261,
  284,  257,  280,  265,  282,  283,  268,  281,  256,  257,
  262,  262,  256,  263,  264,   59,  262,  256,  262,  256,
  257,   45,  256,  291,  289,  290,  291,  289,  290,  297,
   45,  262,  297,  281,  289,  290,  280,  258,  282,  283,
  302,   46,  256,  257,  319,  256,  321,  284,   46,  281,
  257,  262,  284,   45,  319,  256,  321,  319,  326,  321,
  258,  326,  276,  277,  278,  279,   45,  256,  262,  280,
  284,  282,  283,  262,  281,   41,   45,  284,   59,  257,
   41,  259,   43,   44,   45,  256,   41,   44,   43,  256,
   45,  280,   45,  282,  283,  262,  256,  256,   59,   60,
   61,   62,  256,  262,   40,   45,  284,  258,  262,  257,
  257,  259,  259,  280,  256,  282,  283,  257,   45,  259,
   46,  280,   45,  282,  283,  256,  280,   44,  282,  283,
  271,  262,  273,  274,   45,  256,  284,  284,   41,  273,
  274,  262,   59,  284,  284,  257,  256,  257,   45,  280,
  284,  282,  283,  256,  273,  274,  256,  256,   45,  280,
   44,  282,  283,  262,   43,  284,   45,  256,  257,  256,
  257,  281,   45,  257,  284,   59,  256,  257,  263,  264,
   59,  280,   45,  282,  283,  280,   44,  282,  283,  276,
  277,  278,  256,   59,  256,   45,   44,  284,   44,   42,
   44,   59,  256,  257,   47,  262,  256,  257,  257,  273,
  274,   59,  256,   59,   59,   59,   42,  263,  264,   12,
  284,   47,  276,  277,  278,   59,  276,  277,  278,  258,
  284,  258,  256,  257,  284,  106,  107,  263,  264,  108,
  109,  256,  257,   41,   42,   43,  257,   45,  258,   47,
  121,  122,  276,  277,  278,  124,  125,  263,  264,   41,
  284,  276,  277,  278,  256,  257,   52,  257,   61,  284,
   63,   42,   43,   44,   45,   59,   47,  256,  257,   41,
   46,   43,  284,   45,  276,  277,  278,  256,  257,   41,
  257,   43,  284,   45,  258,  256,  258,  276,  277,  278,
   93,  262,   59,  256,  257,  284,  258,  276,  277,  278,
   41,  258,   43,  259,   45,  284,  256,  257,  257,  280,
   41,  282,  283,  276,  277,  278,  126,  259,  257,  256,
  257,  284,   59,  256,  257,   59,  276,  277,  278,  256,
   46,  134,  267,   59,  284,  256,  257,   59,  264,  276,
  277,  278,  257,  276,  277,  278,   59,  284,  259,  256,
  257,  284,  257,  149,  150,  276,  277,  278,  256,  256,
  257,  264,  256,  284,  259,  256,   59,  256,   59,  276,
  277,  278,  264,  256,  257,  259,  186,  284,   59,  276,
  277,  278,  264,  256,  257,   59,   59,  284,  256,   55,
   -1,  201,   -1,  276,  277,  278,  256,  257,  256,   -1,
  256,  284,  256,  276,  277,  278,   -1,  210,  211,   -1,
   -1,  284,   -1,   42,   -1,   44,  276,  277,  278,   -1,
   -1,   -1,   -1,  219,  284,  228,  222,   -1,   -1,   -1,
   59,   60,   -1,   -1,   -1,   -1,   -1,  247,   67,  249,
  256,  257,   -1,  259,  260,  261,   -1,   -1,   -1,  265,
  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,  275,
   89,  256,  257,   -1,   -1,  260,  261,   -1,  284,   -1,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,  291,   -1,   -1,  113,   42,   43,  297,   45,  284,
   47,  120,   -1,   -1,  123,  256,  257,  258,   -1,  260,
  261,  256,  257,  258,  265,  260,  261,  268,  269,   -1,
  265,  272,   -1,  268,  269,   -1,  326,  272,   -1,   -1,
   -1,   -1,  257,  284,  259,  260,  261,   -1,   -1,  284,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   -1,  257,   -1,  259,  260,  261,   -1,   -1,  284,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   -1,  257,   -1,  259,  260,  261,   -1,   -1,  284,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   -1,  257,   -1,  259,  260,  261,   -1,   -1,  284,
  265,  266,   -1,  268,  269,   -1,  225,  272,  273,  274,
  275,  230,  231,  232,  257,   -1,  235,  260,  261,  284,
   -1,   -1,  265,  266,   -1,  268,  269,   -1,   -1,  272,
  273,  274,  275,   -1,  257,  258,   -1,  260,  261,   -1,
   -1,  284,  265,   -1,   -1,  268,  269,   -1,  257,  272,
  259,  260,  261,   -1,  257,   -1,  265,  260,  261,  268,
  269,  284,  265,  272,   -1,  268,  269,   42,   43,  272,
   45,   -1,   47,   42,   43,  284,   45,   -1,   47,   -1,
   -1,  284,   -1,   -1,   59,   41,   42,   43,   -1,   45,
   59,   47,   41,   42,   43,   -1,   45,   -1,   47,   41,
   42,   43,   -1,   45,   -1,   47,
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
"programa : header_programa lista_sentencias END",
"programa : header_programa lista_sentencias error",
"programa : ID error lista_sentencias END",
"programa : error BEGIN lista_sentencias END",
"header_programa : ID BEGIN",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_variables ';'",
"sentencia_declarativa : TAG ';'",
"sentencia_declarativa : tipo ID ';'",
"sentencia_declarativa : ID ';'",
"sentencia_declarativa : lista_variables ';'",
"sentencia_declarativa : header_funcion '(' parametro ')' BEGIN lista_sentencias END",
"sentencia_declarativa : header_funcion '(' parametro ')' BEGIN error END",
"sentencia_declarativa : header_funcion '(' error ')' BEGIN lista_sentencias END",
"sentencia_declarativa : struct ';'",
"sentencia_declarativa : tipo lista_variables error",
"sentencia_declarativa : tipo ID error",
"sentencia_declarativa : ID error",
"sentencia_declarativa : lista_variables error",
"sentencia_declarativa : struct error",
"sentencia_declarativa : TAG error",
"$$1 :",
"header_funcion : tipo FUN ID $$1 tipo FUN error",
"tipo : UINTEGER",
"tipo : SINGLE",
"tipo : ID_STRUCT",
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
"lista_variables : ID_STRUCT '.' ID ',' ID_STRUCT '.' ID",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ',' ID_STRUCT '.' ID",
"lista_variables : ID ID",
"lista_variables : ID_STRUCT '.' ID ID_STRUCT '.' ID",
"lista_variables : lista_variables ID",
"lista_variables : lista_variables ID_STRUCT '.' ID",
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

//#line 305 "gramatica.y"
  
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

    private static ArrayList<String> mangling = new ArrayList<String>();

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

    String nameMangling(String lexema){
        if (lexema.isEmpty())
            return null;
        String lexema_viejo = lexema;
        for (String mangle : mangling) {
            System.out.println("MANGLE: "+mangle);
        }
        for (String mangle : mangling) {
            lexema = lexema + ":" + mangle;
        }
        ts.actualizarSimbolo(lexema, lexema_viejo);
        return lexema;
    }
//#line 800 "Parser.java"
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
//#line 12 "gramatica.y"
{
              Nodo programa = new NodoCompuesto("programa",(Nodo)val_peek(2).obj, (Nodo)val_peek(0).obj);
              System.out.println(programa.toString());  /* Imprime el árbol sintáctico completo*/
              yyval.obj = programa;  /* Almacena el nodo en ParserVal*/
              actualizarTipo(val_peek(2).sval, "NOMBRE_PROGRAMA"); /* Actualiza el tipo de la variable que se genera con el nombre del programa, puede servir a futuro..*/
              actualizarUso(val_peek(2).sval, "NOMBRE_PROGRAMA");
          }
break;
case 2:
//#line 19 "gramatica.y"
{ yyerror(ERROR_END); }
break;
case 3:
//#line 20 "gramatica.y"
{ yyerror(ERROR_BEGIN); }
break;
case 4:
//#line 21 "gramatica.y"
{ yyerror(ERROR_NOMBRE_PROGRAMA); }
break;
case 5:
//#line 24 "gramatica.y"
{mangling.add(val_peek(1).sval); }
break;
case 6:
//#line 28 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 29 "gramatica.y"
{ yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj); }
break;
case 8:
//#line 32 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 9:
//#line 33 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 10:
//#line 36 "gramatica.y"
{actualizarTipoDelGrupo(val_peek(2).sval, val_peek(1).sval);}
break;
case 12:
//#line 38 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");
                                    nameMangling(val_peek(1).sval);
                                    if (tipoEmbebido(val_peek(1).sval))
                                        chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                    else
                                        actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                }
break;
case 13:
//#line 45 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); nameMangling(val_peek(1).sval);}
break;
case 15:
//#line 47 "gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                    System.out.println("HOLA SOY UN PARAMETRO: " + val_peek(4).sval);
                                                                                    Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);
                                                                                    yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador);
                                                                                    mangling.remove(mangling.size() - 1);
                                                                                    }
break;
case 16:
//#line 53 "gramatica.y"
{yyerror(ERROR_RET);}
break;
case 17:
//#line 54 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 19:
//#line 56 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 20:
//#line 57 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 21:
//#line 58 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 22:
//#line 59 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 23:
//#line 60 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 61 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 64 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Funcion"); actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                               errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                               String nuevoNombre = nameMangling(val_peek(0).sval); mangling.add(val_peek(0).sval); yyval.sval = nuevoNombre;
                              }
break;
case 26:
//#line 68 "gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 30:
//#line 77 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");}
break;
case 31:
//#line 78 "gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 32:
//#line 79 "gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 43:
//#line 96 "gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 44:
//#line 100 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 45:
//#line 101 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 47:
//#line 103 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 48:
//#line 104 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 49:
//#line 107 "gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);}
break;
case 50:
//#line 109 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 51:
//#line 112 "gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval; 
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 53:
//#line 116 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 55:
//#line 120 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 121 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 122 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 123 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 59:
//#line 126 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 60:
//#line 127 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 61:
//#line 128 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 62:
//#line 130 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 131 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 64:
//#line 135 "gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 65:
//#line 137 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 66:
//#line 138 "gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 67:
//#line 141 "gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 68:
//#line 145 "gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 69:
//#line 149 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 150 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 151 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 152 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 153 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 154 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 75:
//#line 155 "gramatica.y"
{ yyval = val_peek(0);  }
break;
case 76:
//#line 158 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 162 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 78:
//#line 166 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 167 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 168 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 169 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 170 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 171 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 84:
//#line 172 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 85:
//#line 175 "gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 86:
//#line 178 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct*/
        }
break;
case 87:
//#line 181 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante UINTEGER*/
         }
break;
case 88:
//#line 184 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante SINGLE*/
         }
break;
case 89:
//#line 187 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante HEXA*/
         }
break;
case 91:
//#line 191 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable negativa*/
        }
break;
case 92:
//#line 194 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct negativa*/
        }
break;
case 93:
//#line 197 "gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval); System.out.println("CONSTANTE SINGLE NEGATIVA: " + "-"+val_peek(0).sval);}
break;
case 94:
//#line 198 "gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 95:
//#line 201 "gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);}
break;
case 96:
//#line 202 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 97:
//#line 203 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 98:
//#line 206 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null)); /* No es necesario nodo de control "CUERPO" porque el camino es solo del THEN. MIRAR FILMINAS 14 DEL PAQUETE 08.3 (basado en eso para crear la estructura del arbol adecuado reutilizando clases por patron composite)*/
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 99:
//#line 210 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 100:
//#line 214 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 101:
//#line 215 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 102:
//#line 216 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 217 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 218 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 219 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 220 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 107:
//#line 221 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 108:
//#line 222 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 109:
//#line 223 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 110:
//#line 224 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 111:
//#line 225 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 112:
//#line 228 "gramatica.y"
{yyval = val_peek(1);}
break;
case 113:
//#line 229 "gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 232 "gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 115:
//#line 233 "gramatica.y"
{yyval = val_peek(0);}
break;
case 116:
//#line 236 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 117:
//#line 237 "gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 118:
//#line 238 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 119:
//#line 239 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 126:
//#line 250 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 127:
//#line 251 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 128:
//#line 252 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 129:
//#line 253 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 130:
//#line 254 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 131:
//#line 255 "gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 132:
//#line 258 "gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 133:
//#line 262 "gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 134:
//#line 263 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 135:
//#line 264 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 265 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 137:
//#line 266 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 138:
//#line 267 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 139:
//#line 270 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 140:
//#line 271 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 141:
//#line 272 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 142:
//#line 273 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 143:
//#line 276 "gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);}
break;
case 144:
//#line 277 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 145:
//#line 278 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 146:
//#line 279 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 147:
//#line 282 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 148:
//#line 283 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 149:
//#line 284 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 150:
//#line 285 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 151:
//#line 290 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 152:
//#line 291 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 153:
//#line 294 "gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 154:
//#line 295 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 155:
//#line 296 "gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 156:
//#line 299 "gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(2).obj,null);}
break;
case 157:
//#line 300 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 158:
//#line 301 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1541 "Parser.java"
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
