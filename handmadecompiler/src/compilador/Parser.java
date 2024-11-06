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
    import java.util.List;
    import java.util.ArrayList;
  
//#line 23 "Parser.java"




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
    0,    0,    1,    1,    1,    2,    2,    3,    3,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    8,    8,    6,    6,    9,
    9,    9,    5,    5,    5,    5,    5,    5,    5,   11,
   11,   18,   18,   18,   18,   18,   18,   19,   19,    7,
    7,    7,    7,    7,    7,    7,    7,   21,   21,   21,
   21,   21,   12,   12,   12,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   13,   13,   13,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   26,   26,   27,   27,   25,   25,   25,   25,   28,
   28,   28,   28,   28,   28,   15,   15,   15,   15,   15,
   15,   16,   16,   16,   16,   16,   16,   16,   10,   10,
   10,   10,   29,   29,   29,   29,   30,   30,   30,   30,
   31,   31,   17,   17,   17,   24,   24,
};
final static short yylen[] = {                            2,
    3,    3,    2,    2,    2,    1,    2,    1,    1,    3,
    2,    2,    3,    3,    2,    2,    2,    7,    7,    7,
    7,    2,    2,    3,    2,    3,    3,    1,    1,    2,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    4,    4,    6,    6,    6,    4,    4,    3,
    7,    3,    5,    2,    6,    2,    4,    3,    3,    3,
    3,    3,    5,    5,    5,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    1,    1,    3,    1,    1,    1,    1,    1,
    2,    4,    2,    2,    5,    5,    5,    8,   10,    8,
   10,    7,    7,    6,    9,    9,    8,    8,   10,    7,
    9,    3,    1,    2,    1,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    1,    5,    5,    5,    5,    4,
    5,    7,    6,    7,    6,    6,    5,    7,    3,    3,
    3,    3,    7,    6,    6,    6,    7,    6,    5,    5,
    3,    3,    3,    3,    3,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    5,    4,    3,    0,    0,    0,
    0,    0,    0,    0,    0,   29,   28,    0,    0,    0,
    6,    8,    9,    0,    0,    0,    0,   33,   34,   35,
   36,   37,   38,   39,   40,   41,    0,   16,   54,    0,
   15,    0,    0,    0,    0,    0,    0,   86,   87,   88,
    0,    0,    0,   89,    0,    0,   83,   90,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  113,
    0,    0,    0,    0,   12,   11,    0,    0,    1,    7,
    0,    0,    0,    0,   25,    0,    0,   17,    0,    0,
   23,   22,    0,    0,    0,    0,    0,    0,    0,    0,
   50,  124,  125,  121,    0,    0,    0,    0,  120,  122,
  123,    0,    0,    0,    0,   94,   91,   93,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  141,  139,  142,
  140,    0,    0,    0,  115,    0,    0,    0,  155,  154,
  153,    0,   14,   13,   27,   26,    0,   24,   10,    0,
    0,    0,    0,   52,    0,    0,    0,   32,   31,   30,
    0,   44,   43,   42,    0,    0,    0,    0,    0,    0,
    0,   81,   79,   82,   80,    0,    0,    0,    0,   85,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   77,   75,   78,   76,    0,    0,    0,    0,  130,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  112,  114,    0,    0,    0,    0,    0,    0,
   57,    0,    0,    0,   49,   48,    0,    0,    0,    0,
    0,   96,   97,   95,    0,  157,  156,    0,    0,   92,
    0,    0,    0,  131,  129,  127,  128,  126,    0,    0,
    0,  152,  151,    0,    0,    0,    0,    0,   65,   64,
   63,    0,    0,  137,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   53,    0,    0,    0,   55,    0,
    0,    0,    0,    0,  104,    0,    0,    0,    0,    0,
    0,    0,  150,    0,    0,  149,    0,  136,    0,  135,
  133,   46,   47,   45,    0,    0,    0,    0,   21,    0,
  103,    0,    0,    0,    0,  110,    0,    0,  102,    0,
  146,    0,  145,  148,  144,  138,  134,  132,   51,   20,
   19,   18,    0,    0,  108,    0,  100,   98,  107,    0,
  147,  143,  106,    0,    0,  111,  105,  109,  101,   99,
};
final static short yydgoto[] = {                          3,
    4,   20,   21,   22,   70,   24,   71,   26,   95,   27,
   28,   29,   54,   31,   32,   33,   34,   35,   36,   55,
  163,   56,   57,   58,   59,   72,  146,  112,   64,   65,
  134,
};
final static short yysindex[] = {                      -209,
 -234, -197,    0,  554,    0,    0,    0,   32,   -1,  -38,
   80,  122,  114,  505, -239,    0,    0,  -50,   -5,  443,
    0,    0,    0, -213,  -40,  120,  -47,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -89,    0,    0,  169,
    0,  179, -161, -133,   21,  125,  245,    0,    0,    0,
   85,  182,  167,    0,  138,   65,    0,    0,   29,   -3,
  -52,  -69,  139,   86,  192,  214, -101,  -14,  630,    0,
  -29,  -37,  148,  -25,    0,    0,   33,   32,    0,    0,
  220,  205,  289,  294,    0,  309,  217,    0, -211,  141,
    0,    0,  126,  232,  281,  645,   93,  479,  540,  132,
    0,    0,    0,    0,  226,  251,  262,  276,    0,    0,
    0,  288,  302,  149,   36,    0,    0,    0,  362,  288,
  312,  336,  354,  372,  383,  607,  115,  561,  375,  368,
  576,  -69,  385,   -7,  -22,   40,  184,    0,    0,    0,
    0,  862,  652,  379,    0,  624,  -27,  182,    0,    0,
    0,  -21,    0,    0,    0,    0,  193,    0,    0,  197,
  826,   74,  -30,    0,  419,  -13,  428,    0,    0,    0,
  222,    0,    0,    0,  413,   62,  440,  161,   65,  161,
   65,    0,    0,    0,    0,  736,   17,  870,  723,    0,
  607,  244,  252,   17,  161,   65,  161,   65,  736,   17,
    0,    0,    0,    0,  233,  607,  452,  105,    0,  107,
  131,  124,  -69,  -69,  259,  268,  285,  297,  277,  477,
  112,  182,    0,    0,  182,  156,  516,  396,  286,  517,
    0,  288,  288,  407,    0,    0,  288,  314,  316,  317,
  554,    0,    0,    0,  327,    0,    0,  267,  594,    0,
  607,  532,  278,    0,    0,    0,    0,    0,  337, -204,
  349,    0,    0, -204,  335,  344, -204,  350,    0,    0,
    0,  581,  351,    0,  568,  577,  762,  311,  589,   17,
   17,  736,   17,   17,    0,  554,  574,  465,    0,  607,
  578,  284,  250,  382,    0,  607,  583,  386,    6,   56,
 -204,   82,    0,  399,   91,    0,  596,    0,  117,    0,
    0,    0,    0,    0,  412,  485,  -34,  528,    0,  411,
    0,  426,  627,  607,  249,    0,  637,  425,    0,  439,
    0,  103,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  647,  446,    0,   64,    0,    0,    0,  654,
    0,    0,    0,  655,  306,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   44,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   49,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  719,    0,    0,
    0,    0,    0,    0,    0,  420,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   54,   84,   89,
   96,    0,    0,    0,    0,    0,    8,    0,    0,    0,
    0,    0,    0,   10,  118,  129,  151,  343,   15,   16,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -24,
  176,  300,  331,  378,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0, -208,  -19,    0,    7,  604,    1,    0,  633,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,  558,
    0,  394,  360,    0,  429,  456,    0,  665,    0,    0,
  -31,
};
final static int YYTABLESIZE=917;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
   80,   52,  148,   89,   25,   37,   53,  132,   76,   30,
   23,   92,  225,  237,   89,   30,   73,   53,   88,   61,
   25,  213,  229,    5,   84,   42,   23,  239,  236,   44,
  136,   43,  288,  151,   61,   74,  214,  130,   42,  217,
   77,   53,   44,   81,   43,  164,    1,    2,  118,   44,
  117,   43,  299,   82,  215,  119,  116,   41,    6,  121,
    7,  122,  107,  105,   30,  106,  118,  108,  117,  127,
   83,   37,  165,  119,  116,  145,  192,  316,  318,   83,
  110,  109,  111,  214,   84,   84,   84,   84,   84,   74,
   84,   74,   74,   74,   72,  100,   72,   72,   72,   89,
  212,  218,   84,   84,   84,   84,  124,   74,   74,   74,
   74,  125,   72,   72,   72,   72,  121,  234,  122,   60,
  244,   30,  356,  101,   70,   89,   70,   70,   70,   73,
  114,   73,   73,   73,   89,  121,   71,  122,   71,   71,
   71,   30,   70,   70,   70,   70,   89,   73,   73,   73,
   73,  174,  224,   66,   71,   71,   71,   71,   68,   90,
   68,   68,   68,  256,   42,  258,   93,  214,  144,   66,
  271,   66,   66,   66,  213,  338,   68,   68,   68,   68,
  121,   62,  122,   16,   17,  261,   30,   66,   66,   66,
   66,   69,  259,   69,   69,   69,  275,  110,  109,  111,
  137,   30,  107,   16,   17,   75,  149,  108,   91,   69,
   69,   69,   69,   53,  274,   85,   86,   45,   46,   60,
   16,   17,   47,   53,  341,  235,   53,   86,   45,   46,
  150,   61,  147,   47,   60,  216,   30,   48,   49,   50,
   87,   25,   39,  168,   30,   51,   30,   23,   48,   49,
   50,   87,  128,   46,   38,   39,   51,   47,   53,  228,
  300,   53,   39,   44,  302,   43,   40,  305,   80,  118,
   53,  117,   48,   49,   50,  129,  119,  116,  154,   40,
   51,   30,   30,   30,  113,   30,   25,   25,   25,  152,
  126,   30,   23,   23,   23,   53,   80,  191,   80,   84,
  102,  332,  103,  104,   74,   84,   53,  348,  326,   72,
   74,   30,   86,   30,  331,   72,   25,  243,   25,   30,
   53,  171,   23,   84,   23,   84,   84,  355,   74,  233,
   74,   74,   53,   72,  157,   72,   72,   89,   86,   70,
  333,  138,  139,   62,   73,   70,   53,   86,  173,  335,
   73,   71,  159,  121,  160,  122,   53,   71,   62,   86,
  255,  352,  257,   70,  360,   70,   70,  270,   73,  314,
   73,   73,  337,   68,   58,   71,  206,   71,   71,   68,
   53,  260,  168,   67,   66,   67,   67,   67,  177,   58,
   66,  309,   61,  120,   16,   17,  166,   68,   53,   68,
   68,   67,   67,   67,   67,  190,   69,  193,   66,  308,
   66,   66,   69,   16,   17,  208,   53,  102,  222,  103,
  104,   59,  116,  117,   96,   46,  209,   53,  213,   47,
   69,   60,   69,   69,   98,   46,   59,   45,   46,   47,
   53,  219,   47,  118,   48,   49,   50,  140,  141,  230,
  119,   53,   51,  231,   48,   49,   50,   48,   49,   50,
  155,  156,   51,   56,  238,   51,  183,  185,  240,  142,
   46,  242,  161,   46,   47,  153,   39,   47,   56,  241,
  115,  178,   46,  202,  204,  245,   47,  169,  170,   48,
   49,   50,   48,   49,   50,  251,  252,   51,  179,  181,
   51,   48,   49,   50,  347,  249,  180,   46,  250,   51,
  254,   47,  324,  325,  196,  198,  264,  182,   46,  175,
  107,  105,   47,  106,  265,  108,   48,   49,   50,  290,
  291,  184,   46,  268,   51,  269,   47,   48,   49,   50,
  296,  297,  266,  186,   46,   51,  322,  323,   47,  158,
   86,   48,   49,   50,  267,   62,  276,  188,   46,   51,
  229,  359,   47,   48,   49,   50,  313,  195,   46,  279,
  285,   51,   47,  286,  287,  226,  227,   48,   49,   50,
  176,  205,  121,  289,  122,   51,   58,   48,   49,   50,
  295,  197,   46,  303,  298,   51,   47,   97,   67,   99,
  304,  207,  107,  105,   67,  106,  301,  108,  306,  199,
   46,   48,   49,   50,   47,   63,  210,  131,  121,   51,
  122,  307,   67,  143,   67,   67,  310,  201,   46,   48,
   49,   50,   47,   59,  315,  311,  321,   51,  203,   46,
   94,  329,  330,   47,  162,  327,  248,   48,   49,   50,
  272,  277,   46,  273,  336,   51,   47,  334,   48,   49,
   50,  253,  282,   46,  133,  135,   51,   47,  339,  187,
  189,   48,   49,   50,  343,   56,   56,  194,   56,   51,
  200,  344,   48,   49,   50,  345,  107,  105,  350,  106,
   51,  108,  221,   94,  121,  349,  122,  351,   78,    9,
   56,   79,   10,  172,  293,  353,  294,   11,   12,  354,
   13,   14,  357,  358,   15,   16,   17,   18,    2,  123,
    8,    9,  167,  319,   10,    0,   19,    0,    0,   11,
   12,    0,   13,   14,    0,  211,   15,   16,   17,   18,
    8,    9,    0,  340,   10,  320,    0,    0,   19,   11,
   12,  328,   13,   14,    0,    0,   15,   16,   17,   18,
   67,   68,   69,  247,   10,  121,    0,  122,   19,   11,
    0,    0,   13,   14,    0,    0,   15,  107,  105,  346,
  106,    0,  108,    8,    9,  278,  342,   10,   19,  280,
  281,  283,   11,   12,  284,   13,   14,    0,    0,   15,
   16,   17,   18,  107,  105,    0,  106,    0,  108,    8,
    9,   19,    0,   10,    0,    0,  262,  263,   11,   12,
  312,   13,   14,    0,    0,   15,   16,   17,   18,  317,
    9,    0,    0,   10,    0,    0,    0,   19,   11,   12,
    0,   13,   14,    0,    0,   15,   16,   17,   18,  292,
   68,   69,    0,   10,    0,    0,    0,   19,   11,    0,
    0,   13,   14,   68,   69,   15,   10,  107,  105,  232,
  106,   11,  108,    0,   13,   14,    0,   19,   15,    0,
   68,    0,  223,   10,    0,    0,   68,    0,   11,   10,
   19,   13,   14,    0,   11,   15,    0,   13,   14,    0,
    0,   15,  220,  107,  105,    0,  106,   19,  108,    0,
  246,  107,  105,   19,  106,    0,  108,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
   20,   40,   40,   44,    4,   40,   45,   60,   59,   14,
    4,   59,   40,   44,   44,   20,  256,   45,   59,   44,
   20,   44,   44,  258,   24,   40,   20,   41,   59,   44,
   62,   46,  241,   59,   59,  275,   44,   41,   40,   62,
   46,   45,   44,  257,   46,  257,  256,  257,   41,   44,
   41,   46,  257,  267,   62,   41,   41,   59,  256,   43,
  258,   45,   42,   43,   69,   45,   59,   47,   59,   41,
  284,   40,  284,   59,   59,   69,   41,  286,  287,  284,
   60,   61,   62,   44,   41,   42,   43,   44,   45,   41,
   47,   43,   44,   45,   41,  257,   43,   44,   45,   44,
  132,   62,   59,   60,   61,   62,   42,   59,   60,   61,
   62,   47,   59,   60,   61,   62,   43,   44,   45,   40,
   59,  126,   59,  257,   41,   44,   43,   44,   45,   41,
   46,   43,   44,   45,   44,   43,   41,   45,   43,   44,
   45,  146,   59,   60,   61,   62,   44,   59,   60,   61,
   62,   59,  146,   40,   59,   60,   61,   62,   41,   40,
   43,   44,   45,   59,   40,   59,  256,   44,  270,   41,
   59,   43,   44,   45,   44,   59,   59,   60,   61,   62,
   43,   60,   45,  273,  274,   62,  191,   59,   60,   61,
   62,   41,   62,   43,   44,   45,   41,   60,   61,   62,
   62,  206,   42,  273,  274,  256,   59,   47,  256,   59,
   60,   61,   62,   45,   59,  256,  257,  256,  257,   44,
  273,  274,  261,   45,  259,  256,   45,  257,  256,  257,
  256,  256,  270,  261,   59,  258,  241,  276,  277,  278,
  281,  241,  257,  257,  249,  284,  251,  241,  276,  277,
  278,  281,  256,  257,  256,  257,  284,  261,   45,  281,
  260,   45,  257,   44,  264,   46,  281,  267,  288,  262,
   45,  262,  276,  277,  278,  279,  262,  262,   59,  281,
  284,  286,  287,  288,   40,  290,  286,  287,  288,  257,
  262,  296,  286,  287,  288,   45,  316,  262,  318,  256,
  280,  301,  282,  283,  256,  262,   45,   59,   59,  256,
  262,  316,  257,  318,  259,  262,  316,  256,  318,  324,
   45,   41,  316,  280,  318,  282,  283,  264,  280,  256,
  282,  283,   45,  280,   46,  282,  283,   44,  257,  256,
  259,  256,  257,   44,  256,  262,   45,  257,  256,  259,
  262,  256,   59,   43,   46,   45,   45,  262,   59,  257,
  256,  259,  256,  280,   59,  282,  283,  256,  280,   59,
  282,  283,  256,  256,   44,  280,  262,  282,  283,  262,
   45,  258,  257,   41,  256,   43,   44,   45,  257,   59,
  262,   41,  271,  256,  273,  274,  256,  280,   45,  282,
  283,   59,   60,   61,   62,  257,  256,   46,  280,   59,
  282,  283,  262,  273,  274,   41,   45,  280,   40,  282,
  283,   44,  256,  257,  256,  257,   59,   45,   44,  261,
  280,  256,  282,  283,  256,  257,   59,  256,  257,  261,
   45,  258,  261,  277,  276,  277,  278,  256,  257,  257,
  284,   45,  284,  257,  276,  277,  278,  276,  277,  278,
  256,  257,  284,   44,   46,  284,  107,  108,   41,  256,
  257,   59,  256,  257,  261,  256,  257,  261,   59,  258,
   52,  256,  257,  124,  125,   46,  261,  256,  257,  276,
  277,  278,  276,  277,  278,  263,  264,  284,  105,  106,
  284,  276,  277,  278,  256,  262,  256,  257,  257,  284,
   59,  261,  263,  264,  121,  122,  258,  256,  257,   41,
   42,   43,  261,   45,  257,   47,  276,  277,  278,  263,
  264,  256,  257,  257,  284,   59,  261,  276,  277,  278,
  263,  264,  258,  256,  257,  284,  263,  264,  261,  256,
  257,  276,  277,  278,  258,  256,   41,  256,  257,  284,
   44,  256,  261,  276,  277,  278,  256,  256,  257,  284,
  257,  284,  261,  258,  258,  147,  148,  276,  277,  278,
   41,  126,   43,  257,   45,  284,  256,  276,  277,  278,
   59,  256,  257,  259,  258,  284,  261,   40,  256,   42,
  257,   41,   42,   43,  262,   45,  258,   47,  259,  256,
  257,  276,  277,  278,  261,   12,   41,   60,   43,  284,
   45,   41,  280,   66,  282,  283,   59,  256,  257,  276,
  277,  278,  261,  256,   46,   59,   59,  284,  256,  257,
   37,   59,  257,  261,   87,  264,  191,  276,  277,  278,
  222,  256,  257,  225,   59,  284,  261,  259,  276,  277,
  278,  206,  256,  257,   61,   62,  284,  261,  257,  112,
  113,  276,  277,  278,  264,  256,  257,  120,  259,  284,
  123,  256,  276,  277,  278,   59,   42,   43,  264,   45,
  284,   47,   41,   90,   43,   59,   45,  259,  256,  257,
  281,  259,  260,   59,  249,   59,  251,  265,  266,  264,
  268,  269,   59,   59,  272,  273,  274,  275,    0,   55,
  256,  257,   90,  259,  260,   -1,  284,   -1,   -1,  265,
  266,   -1,  268,  269,   -1,  132,  272,  273,  274,  275,
  256,  257,   -1,  259,  260,  290,   -1,   -1,  284,  265,
  266,  296,  268,  269,   -1,   -1,  272,  273,  274,  275,
  256,  257,  258,   41,  260,   43,   -1,   45,  284,  265,
   -1,   -1,  268,  269,   -1,   -1,  272,   42,   43,  324,
   45,   -1,   47,  256,  257,  228,  259,  260,  284,  232,
  233,  234,  265,  266,  237,  268,  269,   -1,   -1,  272,
  273,  274,  275,   42,   43,   -1,   45,   -1,   47,  256,
  257,  284,   -1,  260,   -1,   -1,  213,  214,  265,  266,
   59,  268,  269,   -1,   -1,  272,  273,  274,  275,  256,
  257,   -1,   -1,  260,   -1,   -1,   -1,  284,  265,  266,
   -1,  268,  269,   -1,   -1,  272,  273,  274,  275,  256,
  257,  258,   -1,  260,   -1,   -1,   -1,  284,  265,   -1,
   -1,  268,  269,  257,  258,  272,  260,   42,   43,   44,
   45,  265,   47,   -1,  268,  269,   -1,  284,  272,   -1,
  257,   -1,  259,  260,   -1,   -1,  257,   -1,  265,  260,
  284,  268,  269,   -1,  265,  272,   -1,  268,  269,   -1,
   -1,  272,   41,   42,   43,   -1,   45,  284,   47,   -1,
   41,   42,   43,  284,   45,   -1,   47,
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
"factor : conversion_explicita",
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
"conversion_explicita : TOS '(' expresion ')'",
"conversion_explicita : TOS '(' error ')'",
};

//#line 322 "gramatica.y"
  
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

    static AnalizadorLexico lex = null;
    static TablaSimbolos ts = TablaSimbolos.getInstance();
  
    void main(String filePath) {
        // Código principal del compilador
        System.out.println("Iniciando análisis sintáctico...");
        lex = AnalizadorLexico.getInstance(filePath);
        run();
        System.out.println("Fin del análisis sintáctico.");
    }
  
    public static void yyerror(String s) {
        if (!s.equalsIgnoreCase("syntax error"))
            System.err.println("Error: " + s + " en la linea "+lex.getNumeroLinea());
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
            else{
                variable = actualizarAmbito(variable);
                actualizarTipo(variable, tipo);
            }
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

    String actualizarAmbito(String lexema){
        for (String mangle : mangling) {
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
        Token token = ts.buscar(funcion);
        
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

    // void borrarSimbolosDuplicados() {
    //     ts.borrarSimbolosDuplicados();
    // }
//#line 824 "Parser.java"
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
//#line 13 "gramatica.y"
{
              Nodo programa = new NodoCompuesto("programa",(Nodo)val_peek(2).obj, (Nodo)val_peek(1).obj);
              System.out.println(programa.toString());  /* Imprime el árbol sintáctico completo*/
              yyval.obj = programa;  /* Almacena el nodo en ParserVal*/
              actualizarTipo(val_peek(2).sval, "NOMBRE_PROGRAMA"); /* Actualiza el tipo de la variable que se genera con el nombre del programa, puede servir a futuro..*/
              actualizarUso(val_peek(2).sval, "NOMBRE_PROGRAMA");
              /*borrarSimbolosDuplicados();  //ojo con esto :D - No arregla lo que busca en caso de tipos embebidos*/
          }
break;
case 2:
//#line 21 "gramatica.y"
{ yyerror(ERROR_END); }
break;
case 3:
//#line 24 "gramatica.y"
{mangling.add(val_peek(1).sval); yyval = val_peek(1);}
break;
case 4:
//#line 25 "gramatica.y"
{yyerror(ERROR_BEGIN);}
break;
case 5:
//#line 26 "gramatica.y"
{yyerror(ERROR_NOMBRE_PROGRAMA);}
break;
case 6:
//#line 30 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 31 "gramatica.y"
{ yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj); }
break;
case 8:
//#line 34 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 9:
//#line 35 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 10:
//#line 38 "gramatica.y"
{String[] lista = (val_peek(1).sval).split(",");
                                                   for (String s : lista){
                                                        nameMangling(s);
                                                   }
                                                   actualizarTipoDelGrupo(val_peek(2).sval, val_peek(1).sval);
                                                  }
break;
case 12:
//#line 45 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 13:
//#line 46 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");
                                    if (tipoEmbebido(val_peek(1).sval))
                                        chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                    else
                                        actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                    nameMangling(val_peek(1).sval);
                                }
break;
case 14:
//#line 53 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 15:
//#line 54 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); nameMangling(val_peek(1).sval);}
break;
case 16:
//#line 55 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 17:
//#line 56 "gramatica.y"
{yyval = val_peek(1); yyval.obj = null;}
break;
case 18:
//#line 57 "gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                    actualizarTipoParamEsperado(val_peek(6).sval, val_peek(4).sval);
                                                                                    System.out.println("FUNCION: "+val_peek(6).sval);
                                                                                    Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);
                                                                                    yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador);
                                                                                    mangling.remove(mangling.size() - 1);
                                                                                    }
break;
case 19:
//#line 64 "gramatica.y"
{yyerror(ERROR_RET);}
break;
case 20:
//#line 65 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 21:
//#line 66 "gramatica.y"
{yyerror(ERROR_HEADER_FUNC);}
break;
case 23:
//#line 68 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 69 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 70 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 26:
//#line 73 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Funcion"); actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                               errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                               this.nuevoNombre = nameMangling(val_peek(0).sval); mangling.add(val_peek(0).sval); yyval.sval = this.nuevoNombre;
                              }
break;
case 27:
//#line 78 "gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 30:
//#line 86 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      nameMangling(val_peek(0).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      yyval.sval = val_peek(1).sval;
                     }
break;
case 31:
//#line 91 "gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 32:
//#line 92 "gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 42:
//#line 108 "gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 43:
//#line 112 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 44:
//#line 113 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 46:
//#line 115 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 47:
//#line 116 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 48:
//#line 119 "gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
                                                                         if (!igualCantElementos(val_peek(3).sval,val_peek(1).sval)) {yyerror(ERROR_CANTIDAD_ASIGNACION);}
                                                                        }
break;
case 49:
//#line 123 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 50:
//#line 126 "gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 52:
//#line 130 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 54:
//#line 134 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 55:
//#line 135 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 136 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 137 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 140 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 59:
//#line 141 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 60:
//#line 142 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 61:
//#line 144 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 62:
//#line 145 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 149 "gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 64:
//#line 151 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 65:
//#line 152 "gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 66:
//#line 155 "gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 67:
//#line 159 "gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 68:
//#line 163 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 69:
//#line 164 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 165 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 166 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 167 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 168 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 169 "gramatica.y"
{ yyval = val_peek(0);  }
break;
case 75:
//#line 172 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 76:
//#line 176 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 180 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 78:
//#line 181 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 182 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 183 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 184 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 185 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 186 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 84:
//#line 189 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct*/
        }
break;
case 85:
//#line 192 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"UINTEGER");  /* Nodo para constante UINTEGER*/
         }
break;
case 86:
//#line 195 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"UINTEGER");  /* Nodo para constante UINTEGER*/
         }
break;
case 87:
//#line 198 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"SINGLE");  /* Nodo para constante SINGLE*/
         }
break;
case 88:
//#line 201 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"HEXA");  /* Nodo para constante HEXA*/
         }
break;
case 91:
//#line 206 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct negativa*/
        }
break;
case 92:
//#line 209 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct negativa*/
        }
break;
case 93:
//#line 212 "gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval,"SINGLE");}
break;
case 94:
//#line 213 "gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 95:
//#line 216 "gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);
                                                System.out.println("NODO EXPRESION: " + val_peek(2).obj.toString());
                                                if(!paramRealIgualFormal(val_peek(4).sval, ((Nodo)val_peek(2).obj).devolverTipo(mangling))) {yyerror(ERROR_TIPO_PARAMETRO);}
                                               }
break;
case 96:
//#line 220 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 97:
//#line 221 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 98:
//#line 224 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null));
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 99:
//#line 228 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 100:
//#line 232 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 101:
//#line 233 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 102:
//#line 234 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 235 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 236 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 237 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 238 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 107:
//#line 239 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 108:
//#line 240 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 109:
//#line 241 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 110:
//#line 242 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 111:
//#line 243 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 112:
//#line 246 "gramatica.y"
{yyval = val_peek(1);}
break;
case 113:
//#line 247 "gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 250 "gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 115:
//#line 251 "gramatica.y"
{yyval = val_peek(0);}
break;
case 116:
//#line 254 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 117:
//#line 255 "gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 118:
//#line 256 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 119:
//#line 257 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 126:
//#line 268 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 127:
//#line 269 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 128:
//#line 270 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 129:
//#line 271 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 130:
//#line 272 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 131:
//#line 273 "gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 132:
//#line 276 "gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 133:
//#line 280 "gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 134:
//#line 281 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 135:
//#line 282 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 283 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 137:
//#line 284 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 138:
//#line 285 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 139:
//#line 288 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct"); }
break;
case 140:
//#line 289 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 141:
//#line 290 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 142:
//#line 291 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 143:
//#line 294 "gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);}
break;
case 144:
//#line 295 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 145:
//#line 296 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 146:
//#line 297 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 147:
//#line 300 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 148:
//#line 301 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 149:
//#line 302 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 150:
//#line 303 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 151:
//#line 308 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 152:
//#line 309 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 153:
//#line 312 "gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 154:
//#line 313 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 155:
//#line 314 "gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 156:
//#line 317 "gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(1).obj,null);}
break;
case 157:
//#line 318 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1585 "Parser.java"
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
