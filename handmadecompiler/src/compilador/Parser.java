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
    3,    3,    3,    3,    5,    5,    5,    7,    7,    7,
    4,    4,    4,    4,    4,    4,    4,    4,    9,    9,
   17,   17,   17,   17,   17,   17,   18,   18,    6,    6,
    6,    6,    6,    6,    6,    6,   20,   20,   20,   20,
   20,   10,   10,   10,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   11,   11,   11,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   24,
   24,   25,   25,   23,   23,   23,   23,   26,   26,   26,
   26,   26,   26,   13,   13,   13,   13,   13,   13,   14,
   14,   14,   14,   14,   14,   14,    8,    8,    8,    8,
   27,   27,   27,   27,   28,   28,   28,   28,   29,   29,
   15,   15,   15,   16,   16,   16,
};
final static short yylen[] = {                            2,
    4,    4,    4,    4,    1,    2,    1,    1,    3,    2,
    3,    2,    2,    9,    2,    3,    3,    2,    2,    9,
    9,    2,    9,    2,    1,    1,    1,    2,    2,    2,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    4,    4,    6,    6,    6,    4,    4,    3,    7,
    3,    5,    2,    6,    2,    4,    3,    3,    3,    3,
    3,    5,    5,    5,    3,    3,    3,    3,    3,    3,
    3,    3,    1,    3,    3,    3,    3,    3,    3,    3,
    3,    1,    1,    3,    1,    1,    1,    1,    2,    4,
    2,    2,    5,    5,    5,    8,   10,    8,   10,    7,
    7,    6,    9,    9,    8,    8,   10,    7,    9,    3,
    1,    2,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    1,    1,    5,    5,    5,    5,    4,    5,    7,
    6,    7,    6,    6,    5,    7,    3,    3,    3,    3,
    7,    6,    6,    6,    7,    6,    5,    5,    3,    3,
    3,    3,    3,    5,    5,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   26,   25,    0,    0,    0,    5,
    7,    8,    0,    0,    0,   31,   32,   33,   34,   35,
   36,   37,   38,   39,   40,    0,    0,   18,   53,    0,
   12,    0,    0,    0,    0,   85,   86,   87,    0,    0,
    0,   88,    0,    0,   82,    0,    0,    0,    0,   27,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  111,
    0,    0,    0,    0,   24,   10,    0,    4,    6,    0,
    0,    0,    0,   19,   55,    0,    0,   13,    0,   22,
   15,    3,    2,    1,    0,    0,    0,    0,   49,  122,
  123,  119,    0,    0,    0,    0,  118,  120,  121,    0,
    0,    0,   92,   89,   91,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  139,  137,  140,  138,
    0,    0,    0,  113,    0,    0,    0,  153,  152,  151,
    0,   17,   11,    0,    0,    0,   16,    9,    0,    0,
    0,    0,   51,    0,   43,   42,   41,    0,    0,    0,
    0,    0,    0,   80,   78,   81,   79,    0,    0,   84,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   76,   74,   77,   75,    0,    0,    0,    0,    0,    0,
  128,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  110,  112,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   48,   47,
    0,   56,    0,   94,   95,   93,    0,    0,   90,    0,
    0,    0,  156,  155,  154,  129,  127,  125,  126,  124,
    0,    0,    0,  150,  149,    0,    0,    0,    0,    0,
   64,   63,   62,    0,    0,  135,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,    0,    0,    0,    0,    0,  102,    0,
    0,    0,    0,    0,    0,    0,  148,    0,    0,  147,
    0,  134,    0,  133,  131,   45,   46,   44,   54,    0,
   30,   29,   28,    0,    0,    0,    0,  101,    0,    0,
    0,    0,  108,    0,    0,  100,    0,  144,    0,  143,
  146,  142,  136,  132,  130,   50,    0,    0,    0,    0,
    0,  106,    0,   98,   96,  105,    0,  145,  141,    0,
    0,    0,    0,  104,    0,    0,  109,  103,   20,   23,
   21,   14,  107,   99,   97,
};
final static short yydgoto[] = {                          3,
   19,   20,   21,   22,   23,   24,  275,   25,   26,   27,
   52,   29,   30,   31,   32,   33,   34,   35,   53,  161,
   54,   55,   56,   72,  145,  110,   63,   64,  133,
};
final static short yysindex[] = {                      -180,
 -238, -135,    0,  606,  606,  606,    6,  -19,   -9,   -6,
  193,    4,  440, -136,    0,    0,  -21,  -18,  494,    0,
    0,    0, -218,  131,   34,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  520,  402,    0,    0,  173,
    0,  203, -209,   36,   91,    0,    0,    0,   90,  206,
  257,    0,  119,  381,    0,  -33,  215,   22,    8,    0,
  234,  -11,  -45,  121,  219, -123,  -27,  646,  -18,    0,
   73,  -28,   98,   54,    0,    0,  -94,    0,    0,   55,
  163,  120,  169,    0,    0,  244,  142,    0, -169,    0,
    0,    0,    0,    0,  728,  355,  884,  504,    0,    0,
    0,    0,  249,  259,  275,  305,    0,    0,    0,  308,
  -62,   -1,    0,    0,    0,  156,  308,  317,  321,  331,
  347,  360,  626,  -55,  893,  638,  900,  229,  330,  650,
  234,  320,  -25,  -26,  -17,  139,    0,    0,    0,    0,
  907,  731,  370,    0,  640,  -10,  206,    0,    0,    0,
  -41,    0,    0,  391,  412,  179,    0,    0,  913,  389,
  134,  197,    0,  367,    0,    0,    0,  399,   59,  414,
  381,  414,  381,    0,    0,    0,    0,  919,  401,    0,
  626,  208,  221,  401,  414,  381,  414,  381,  919,  401,
    0,    0,    0,    0,  222,  626,  406,  128,  415,  213,
    0,  217,   18,   -3,  234,  234,  240,  247,  251,  253,
  262,  471,  254,  206,    0,    0,  206,   70,  507,  363,
  512,  266, -231, -147,  -40,  308,  308,  373,    0,    0,
  308,    0,  306,    0,    0,    0,  225,  480,    0,  626,
  509,  293,    0,    0,    0,    0,    0,    0,    0,    0,
  314, -162,  318,    0,    0, -162,  307,  322, -162,  332,
    0,    0,    0,  555,  135,    0,  543,  547,  874,  396,
  356,  572,  369,  397,  581,  -34,  592,  401,  401,  919,
  401,  401,    0,  626,  587,  409,  132,  384,    0,  626,
  596,  403,   27,   48, -162,   68,    0,  405,   81,    0,
  607,    0,  282,    0,    0,    0,    0,    0,    0,  408,
    0,    0,    0,  411,  424,  427,  428,    0,  438,  643,
  626,  311,    0,  644,  435,    0,  445,    0,  101,    0,
    0,    0,    0,    0,    0,    0,  606,  606,  460,  652,
  450,    0,   45,    0,    0,    0,  659,    0,    0,  546,
  566,  463,  586,    0,  660,  344,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  245,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   13,    0,    0,    0,    0,    0,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,   46,
   89,   99,  111,    0,    0,    0,    0,    0,  -12,    0,
    0,    0,    0,   11,  124,  155,  160,  165,   69,   94,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  212,  304,  315,
  324,  365,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    5,  -14,    0,    1,  483,   -7,  499,    0,    0,    0,
   -4,    0,    0,    0,    0,    0,    0,    0,  570,    0,
  451,  418,  496,  394,    0,  674,    0,    0,  -37,
};
final static int YYTABLESIZE=966;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
   28,   28,  222,  222,   79,   71,  315,  124,   28,   36,
   37,  147,   42,   70,   28,   83,   43,  205,  206,    4,
   50,   79,   79,  135,  273,   51,  206,   77,  116,  217,
   57,   28,   28,   58,   51,  209,  207,   76,   80,  182,
  206,   15,   16,   65,  210,   42,  116,   99,   81,   43,
  136,  115,   60,   83,   83,   83,   83,   83,  253,   83,
   71,  205,  129,   28,   41,   82,   51,  131,  144,  115,
   43,   83,   83,   83,   83,    1,    2,  105,  103,  251,
  104,   73,  106,   73,   73,   73,   71,  163,   71,   71,
   71,   89,   91,  204,  293,  108,  107,  109,   43,   73,
   73,   73,   73,  357,   71,   71,   71,   71,  276,  117,
  267,   89,  150,  153,  164,   71,   89,  236,   28,   73,
    5,   82,    6,   70,   89,   15,   16,  117,  266,   69,
   42,   69,   69,   69,  114,  111,   60,   71,   74,   72,
   28,   72,   72,   72,   89,  216,  143,   69,   69,   69,
   69,   70,  114,   70,   70,   70,  148,   72,   72,   72,
   72,  118,  151,  119,   67,  156,   67,   67,   67,   70,
   70,   70,   70,   71,   89,  303,   28,  231,  108,  107,
  109,   70,   67,   67,   67,   67,  245,  162,   71,   88,
  323,   28,  230,  302,  180,   65,   70,   65,   65,   65,
   68,  183,   68,   68,   68,   66,  196,   66,   66,   66,
  137,  138,   89,   65,   65,   65,   65,   51,   68,   68,
   68,   68,  311,   66,   66,   66,   66,  158,  123,   39,
   71,  208,   71,   28,   75,   28,   44,   45,   70,  220,
   70,  146,  221,  221,  294,   44,   45,   51,  296,  116,
   51,  299,   61,   40,  252,   60,   46,   47,   48,   51,
  181,   38,   39,   51,   49,   46,   47,   48,   83,  200,
   60,  248,  115,   49,   83,  250,   71,  127,   45,   28,
   15,   16,   71,   39,   70,   28,   40,  329,   51,   90,
   70,   60,   83,   51,   83,   83,   73,   46,   47,   48,
  128,   71,   73,   51,   85,   49,  328,   71,  356,  149,
  152,   39,  263,   71,  235,  100,   28,  101,  102,   51,
   73,   70,   73,   73,   85,   71,  330,   71,   71,   85,
  117,   87,   28,   28,   28,   79,   79,   85,   79,  332,
  335,  350,  351,  353,   69,   28,   28,   59,   28,   51,
   69,   87,   51,   86,   72,  114,   87,   85,   61,  349,
   72,   51,   59,  205,   87,   51,   70,   57,   69,  345,
   69,   69,   70,   61,  117,   51,  139,  140,   72,   67,
   72,   72,   57,  244,   87,   67,   84,   85,  201,  229,
   70,   51,   70,   70,  321,  322,  211,  118,  100,  119,
  101,  102,  365,   67,   51,   67,   67,   51,   58,  214,
   65,   86,  233,  167,   87,   68,   65,   51,  154,  155,
   66,   68,  121,   58,  157,   85,   66,  122,   95,   45,
  223,  118,  228,  119,   65,  225,   65,   65,  118,   68,
  119,   68,   68,  118,   66,  119,   66,   66,   46,   47,
   48,  224,   87,  232,  308,  105,   49,  234,   97,   45,
  106,   44,   45,   59,  243,   15,   16,   60,  247,  238,
  125,   45,  249,  246,  141,   45,   60,  239,   46,   47,
   48,   46,   47,   48,  240,  241,   49,  284,  285,   49,
   46,   47,   48,   62,   46,   47,   48,  256,   49,  159,
   45,   27,   49,  257,  170,   45,   15,   16,  258,  262,
  259,   27,  113,  114,  172,   45,  195,   60,  260,   46,
   47,   48,  175,  177,   46,   47,   48,   49,   27,  261,
  174,   45,   49,  115,   46,   47,   48,  334,  192,  194,
  116,  132,   49,  134,  169,  112,  118,  268,  119,  272,
   46,   47,   48,  171,  173,  290,  291,  271,   49,   59,
  176,   45,  283,  178,   45,  297,  344,  289,  186,  188,
   61,  292,  185,   45,  237,  295,  187,   45,  298,   57,
   46,   47,   48,   46,   47,   48,  189,   45,   49,  242,
  300,   49,   46,   47,   48,  301,   46,   47,   48,  364,
   49,  304,  191,   45,   49,  305,   46,   47,   48,   96,
  166,   98,  309,  203,   49,  193,   45,  310,  269,   45,
   58,  314,   46,   47,   48,  311,  126,  130,  280,   45,
   49,  287,  316,  288,  142,   46,   47,   48,   46,   47,
   48,  218,  219,   49,  227,  318,   49,  324,   46,   47,
   48,  307,  312,  313,  326,  160,   49,   93,    7,  327,
   94,    8,    9,  331,  336,  333,   10,   11,  337,   12,
   13,  319,  320,   14,   15,   16,   17,  317,  198,  179,
  118,  338,  119,  325,  339,   18,  184,  254,  255,  190,
  202,  340,  118,  341,  119,   66,   67,   68,  347,    8,
    9,  342,  346,  348,   10,  274,  274,   12,   13,  264,
  354,   14,  265,  355,  343,  352,    7,  358,  363,    8,
    9,  361,  277,   69,   10,   11,  120,   12,   13,    0,
    0,   14,   15,   16,   17,  286,   67,   68,    0,    8,
    9,    0,    0,   18,   10,    0,    0,   12,   13,    0,
    7,   14,   78,    8,    9,    0,    0,    0,   10,   11,
    0,   12,   13,   69,    0,   14,   15,   16,   17,  105,
  103,  213,  104,  118,  106,  119,    7,   18,   92,    8,
    9,    0,    0,    0,   10,   11,  165,   12,   13,  270,
    0,   14,   15,   16,   17,  278,  279,  281,    0,    0,
  282,    0,    7,   18,  359,    8,    9,    0,    0,    0,
   10,   11,    0,   12,   13,    0,    0,   14,   15,   16,
   17,    0,    7,    0,  360,    8,    9,    0,    0,   18,
   10,   11,    0,   12,   13,    0,    0,   14,   15,   16,
   17,    0,    7,    0,  362,    8,    9,    0,    0,   18,
   10,   11,    0,   12,   13,    0,    0,   14,   15,   16,
   17,    0,    7,    0,    0,    8,    9,    0,    0,   18,
   10,   11,    0,   12,   13,    0,    0,   14,   15,   16,
   17,    0,   67,   68,    0,    8,    9,    0,    0,   18,
   10,    0,    0,   12,   13,    0,   67,   14,  215,    8,
    9,    0,   67,    0,   10,    8,    9,   12,   13,   69,
   10,   14,    0,   12,   13,  105,  103,   14,  104,    0,
  106,    0,    0,   69,  168,  105,  103,    0,  104,   69,
  106,    0,  306,  197,  105,  103,    0,  104,    0,  106,
  199,  105,  103,    0,  104,    0,  106,  212,  105,  103,
    0,  104,    0,  106,  105,  103,  226,  104,    0,  106,
  105,  103,    0,  104,    0,  106,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
    5,    6,   44,   44,   19,   13,   41,   41,   13,    5,
    6,   40,   40,   13,   19,   23,   44,   44,   44,  258,
   40,   36,   37,   61,  256,   45,   44,   46,   41,   40,
   40,   36,   37,   40,   45,   62,   62,   59,  257,   41,
   44,  273,  274,   40,   62,   40,   59,  257,  267,   44,
   62,   41,  284,   41,   42,   43,   44,   45,   62,   47,
   68,   44,   41,   68,   59,  284,   45,   60,   68,   59,
   44,   59,   60,   61,   62,  256,  257,   42,   43,   62,
   45,   41,   47,   43,   44,   45,   41,  257,   43,   44,
   45,   44,   59,  131,  257,   60,   61,   62,   44,   59,
   60,   61,   62,   59,   59,   60,   61,   62,  256,   41,
   41,   44,   59,   59,  284,  123,   44,   59,  123,  256,
  256,  284,  258,  123,   44,  273,  274,   59,   59,   41,
   40,   43,   44,   45,   41,   46,  284,  145,  275,   41,
  145,   43,   44,   45,   44,  145,  270,   59,   60,   61,
   62,   41,   59,   43,   44,   45,   59,   59,   60,   61,
   62,   43,  257,   45,   41,   46,   43,   44,   45,   59,
   60,   61,   62,  181,   44,   41,  181,   44,   60,   61,
   62,  181,   59,   60,   61,   62,   59,   46,  196,   59,
   59,  196,   59,   59,  257,   41,  196,   43,   44,   45,
   41,   46,   43,   44,   45,   41,  262,   43,   44,   45,
  256,  257,   44,   59,   60,   61,   62,   45,   59,   60,
   61,   62,  257,   59,   60,   61,   62,   59,  262,  257,
  238,  258,  240,  238,  256,  240,  256,  257,  238,  281,
  240,  270,  284,  284,  252,  256,  257,   45,  256,  262,
   45,  259,   60,  281,  258,   44,  276,  277,  278,   45,
  262,  256,  257,   45,  284,  276,  277,  278,  256,   41,
   59,   59,  262,  284,  262,   59,  284,  256,  257,  284,
  273,  274,  290,  257,  284,  290,  281,  295,   45,  256,
  290,  284,  280,   45,  282,  283,  256,  276,  277,  278,
  279,  256,  262,   45,  257,  284,  259,  262,  264,  256,
  256,  257,   59,  321,  256,  280,  321,  282,  283,   45,
  280,  321,  282,  283,  257,  280,  259,  282,  283,  257,
  262,  284,  337,  338,  339,  350,  351,  257,  353,  259,
   59,  337,  338,  339,  256,  350,  351,   44,  353,   45,
  262,  284,   45,  281,  256,  262,  284,  257,   44,  259,
  262,   45,   59,   44,  284,   45,  256,   44,  280,   59,
  282,  283,  262,   59,  256,   45,  256,  257,  280,  256,
  282,  283,   59,  256,  284,  262,  256,  257,   59,  256,
  280,   45,  282,  283,  263,  264,  258,   43,  280,   45,
  282,  283,   59,  280,   45,  282,  283,   45,   44,   40,
  256,  281,   46,   59,  284,  256,  262,   45,  256,  257,
  256,  262,   42,   59,  256,  257,  262,   47,  256,  257,
   40,   43,   44,   45,  280,  257,  282,  283,   43,  280,
   45,  282,  283,   43,  280,   45,  282,  283,  276,  277,
  278,   40,  284,  257,   59,   42,  284,   59,  256,  257,
   47,  256,  257,  271,   59,  273,  274,  256,  256,  262,
  256,  257,  256,   59,  256,  257,  284,  257,  276,  277,
  278,  276,  277,  278,  263,  264,  284,  263,  264,  284,
  276,  277,  278,   11,  276,  277,  278,  258,  284,  256,
  257,  257,  284,  257,  256,  257,  273,  274,  258,  256,
  258,  267,  256,  257,  256,  257,  123,  284,  257,  276,
  277,  278,  105,  106,  276,  277,  278,  284,  284,   59,
  256,  257,  284,  277,  276,  277,  278,  256,  121,  122,
  284,   59,  284,   61,   41,   50,   43,   41,   45,  284,
  276,  277,  278,  103,  104,  263,  264,   46,  284,  256,
  256,  257,  257,  256,  257,  259,  256,   59,  118,  119,
  256,  258,  256,  257,  181,  258,  256,  257,  257,  256,
  276,  277,  278,  276,  277,  278,  256,  257,  284,  196,
  259,  284,  276,  277,  278,   41,  276,  277,  278,  256,
  284,   59,  256,  257,  284,   59,  276,  277,  278,   40,
  256,   42,  257,  131,  284,  256,  257,   46,  256,  257,
  256,   41,  276,  277,  278,  257,   57,   58,  256,  257,
  284,  238,   41,  240,   65,  276,  277,  278,  276,  277,
  278,  146,  147,  284,  256,   59,  284,  264,  276,  277,
  278,  256,  256,  257,   59,   86,  284,  256,  257,  257,
  259,  260,  261,  259,  257,   59,  265,  266,  258,  268,
  269,  263,  264,  272,  273,  274,  275,  284,   41,  110,
   43,  258,   45,  290,  258,  284,  117,  205,  206,  120,
   41,  264,   43,  256,   45,  256,  257,  258,  264,  260,
  261,   59,   59,  259,  265,  223,  224,  268,  269,  214,
   59,  272,  217,  264,  321,  256,  257,   59,   59,  260,
  261,  259,  224,  284,  265,  266,   53,  268,  269,   -1,
   -1,  272,  273,  274,  275,  256,  257,  258,   -1,  260,
  261,   -1,   -1,  284,  265,   -1,   -1,  268,  269,   -1,
  257,  272,  259,  260,  261,   -1,   -1,   -1,  265,  266,
   -1,  268,  269,  284,   -1,  272,  273,  274,  275,   42,
   43,   41,   45,   43,   47,   45,  257,  284,  259,  260,
  261,   -1,   -1,   -1,  265,  266,   59,  268,  269,  220,
   -1,  272,  273,  274,  275,  226,  227,  228,   -1,   -1,
  231,   -1,  257,  284,  259,  260,  261,   -1,   -1,   -1,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   -1,  257,   -1,  259,  260,  261,   -1,   -1,  284,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   -1,  257,   -1,  259,  260,  261,   -1,   -1,  284,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   -1,  257,   -1,   -1,  260,  261,   -1,   -1,  284,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   -1,  257,  258,   -1,  260,  261,   -1,   -1,  284,
  265,   -1,   -1,  268,  269,   -1,  257,  272,  259,  260,
  261,   -1,  257,   -1,  265,  260,  261,  268,  269,  284,
  265,  272,   -1,  268,  269,   42,   43,  272,   45,   -1,
   47,   -1,   -1,  284,   41,   42,   43,   -1,   45,  284,
   47,   -1,   59,   41,   42,   43,   -1,   45,   -1,   47,
   41,   42,   43,   -1,   45,   -1,   47,   41,   42,   43,
   -1,   45,   -1,   47,   42,   43,   44,   45,   -1,   47,
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
"sentencia_declarativa : ID ';'",
"sentencia_declarativa : lista_variables ';'",
"sentencia_declarativa : tipo FUN ID '(' parametro ')' BEGIN lista_sentencias END",
"sentencia_declarativa : struct ';'",
"sentencia_declarativa : tipo lista_variables error",
"sentencia_declarativa : tipo ID error",
"sentencia_declarativa : ID error",
"sentencia_declarativa : lista_variables error",
"sentencia_declarativa : tipo FUN error '(' parametro ')' BEGIN lista_sentencias END",
"sentencia_declarativa : tipo FUN ID '(' parametro ')' BEGIN error END",
"sentencia_declarativa : struct error",
"sentencia_declarativa : tipo FUN ID '(' error ')' BEGIN lista_sentencias END",
"sentencia_declarativa : TAG error",
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

//#line 278 ".\gramatica.y"
  
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

    void chequeoTipo(String tipo, String nombre) {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        nombre = nombre.toLowerCase();
        System.out.println(nombre.charAt(0) + " " + tipo);
        if ((nombre.charAt(0) == 's') && tipo.equals("uinteger") ) {
            ts.actualizarTipo(nombre, "UINTEGER");
            System.out.println("Redeclaracion de variable "+nombre+" como UINTEGER. Linea "+lex.getNumeroLinea());
        } else if ((nombre.charAt(0) == 'u' || nombre.charAt(0) == 'v' || nombre.charAt(0) == 'w') && tipo.equals("single") ) {
            ts.actualizarTipo(nombre, "SINGLE");
            System.out.println("Redeclaracion de variable "+nombre+" como SINGLE. Linea "+lex.getNumeroLinea());
        }
        
    }

    void chequeoError (String id){
        if (id.charAt(0) == 'u' || id.charAt(0) == 'v' || id.charAt(0) == 'w' || id.charAt(0) == 's') {
            System.out.println("Error en la redeclaracion del parametro de la funcion "+id);
        }
    }

    void errorEtiqueta(String etiqueta) {
        if (etiqueta.charAt(0) == 'u' || etiqueta.charAt(0) == 'v' || etiqueta.charAt(0) == 'w' || etiqueta.charAt(0) == 's') {
            System.out.println("Error en la etiqueta de GOTO "+etiqueta);
        }
    }
//#line 760 "Parser.java"
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
          }
break;
case 2:
//#line 16 ".\gramatica.y"
{ yyerror(ERROR_END); }
break;
case 3:
//#line 17 ".\gramatica.y"
{ yyerror(ERROR_BEGIN); }
break;
case 4:
//#line 18 ".\gramatica.y"
{ yyerror(ERROR_NOMBRE_PROGRAMA); }
break;
case 5:
//#line 23 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 6:
//#line 24 ".\gramatica.y"
{ yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj); }
break;
case 7:
//#line 27 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 8:
//#line 28 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 10:
//#line 32 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "TAG");}
break;
case 11:
//#line 33 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 12:
//#line 34 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 14:
//#line 36 ".\gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(6).sval, "Funcion"); actualizarTipo(val_peek(6).sval, val_peek(8).sval);
                                                                                    Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval); /* Uso delimitador para las funciones*/
                                                                                    yyval.obj = new NodoCompuesto("FUNCION",(Nodo)val_peek(1).obj,delimitador);
                                                                                    chequeoError(val_peek(6).sval); }
break;
case 16:
//#line 41 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 17:
//#line 42 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 18:
//#line 43 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 19:
//#line 44 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 20:
//#line 45 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 21:
//#line 46 ".\gramatica.y"
{yyerror(ERROR_RET);}
break;
case 22:
//#line 47 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 23:
//#line 48 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 24:
//#line 49 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 28:
//#line 57 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro");
                        chequeoError(val_peek(0).sval); }
break;
case 29:
//#line 59 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 30:
//#line 60 ".\gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 41:
//#line 77 ".\gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 42:
//#line 81 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 43:
//#line 82 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 45:
//#line 84 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 46:
//#line 85 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 47:
//#line 88 ".\gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);}
break;
case 48:
//#line 90 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 49:
//#line 93 ".\gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 51:
//#line 96 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 53:
//#line 99 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 54:
//#line 100 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 55:
//#line 101 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 102 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 105 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 58:
//#line 106 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 59:
//#line 107 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 60:
//#line 109 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 61:
//#line 110 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 62:
//#line 114 ".\gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 63:
//#line 116 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 64:
//#line 117 ".\gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 65:
//#line 120 ".\gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 66:
//#line 124 ".\gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 67:
//#line 128 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 68:
//#line 129 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 69:
//#line 130 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 131 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 132 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 133 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 134 ".\gramatica.y"
{ yyval = val_peek(0);  }
break;
case 74:
//#line 137 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 75:
//#line 141 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 76:
//#line 145 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 77:
//#line 146 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 78:
//#line 147 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 148 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 149 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 150 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 151 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 83:
//#line 154 ".\gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 85:
//#line 158 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante UINTEGER*/
         }
break;
case 86:
//#line 161 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante SINGLE*/
         }
break;
case 87:
//#line 164 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante HEXA*/
         }
break;
case 91:
//#line 170 ".\gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval);}
break;
case 92:
//#line 171 ".\gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 93:
//#line 174 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);}
break;
case 94:
//#line 175 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 95:
//#line 176 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 96:
//#line 179 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null)); /* No es necesario nodo de control "CUERPO" porque el camino es solo del THEN. MIRAR FILMINAS 14 DEL PAQUETE 08.3 (basado en eso para crear la estructura del arbol adecuado reutilizando clases por patron composite)*/
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 97:
//#line 183 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 98:
//#line 187 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 99:
//#line 188 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 100:
//#line 189 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 101:
//#line 190 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 102:
//#line 191 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 192 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 193 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 194 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 195 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 107:
//#line 196 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 108:
//#line 197 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 109:
//#line 198 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 110:
//#line 201 ".\gramatica.y"
{yyval = val_peek(1);}
break;
case 111:
//#line 202 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 112:
//#line 205 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 113:
//#line 206 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 209 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 115:
//#line 210 ".\gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 116:
//#line 211 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 117:
//#line 212 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 124:
//#line 223 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 125:
//#line 224 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 126:
//#line 225 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 127:
//#line 226 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 128:
//#line 227 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 129:
//#line 228 ".\gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 130:
//#line 231 ".\gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 131:
//#line 235 ".\gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 132:
//#line 236 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 133:
//#line 237 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 134:
//#line 238 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 135:
//#line 239 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 240 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 137:
//#line 243 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 138:
//#line 244 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 139:
//#line 245 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 140:
//#line 246 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 142:
//#line 250 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 143:
//#line 251 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 144:
//#line 252 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 145:
//#line 255 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 146:
//#line 256 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 147:
//#line 257 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 148:
//#line 258 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 151:
//#line 267 ".\gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorEtiqueta(val_peek(1).sval); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 152:
//#line 268 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 153:
//#line 269 ".\gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 154:
//#line 272 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(2).obj,null);}
break;
case 155:
//#line 273 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 156:
//#line 274 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1449 "Parser.java"
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
