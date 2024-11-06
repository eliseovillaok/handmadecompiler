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
   23,   23,   13,   13,   13,   14,   14,   14,   14,   14,
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
    3,    3,    1,    1,    1,    1,    1,    1,    1,    2,
    2,    2,    5,    5,    5,    8,   10,    8,   10,    7,
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
   88,    0,    0,   83,   89,    0,    0,   30,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  111,    0,    0,
    0,    0,   12,   11,    0,    1,    7,    0,    0,    0,
   25,    0,    0,   17,    0,    0,   23,   22,    0,    0,
    0,    0,    0,    0,    0,    0,   51,  122,  123,  119,
    0,    0,    0,    0,  118,  120,  121,    0,    0,    0,
   92,   90,   91,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  139,  137,  140,  138,    0,    0,    0,  113,    0,
    0,    0,  153,  152,  151,   14,   13,    0,   27,   26,
   24,   10,    0,    0,    0,    0,    0,    0,    0,   33,
   32,   31,    0,   45,   44,   43,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   81,   79,   82,   80,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   77,   75,   78,   76,    0,    0,    0,    0,
  128,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  110,  112,    0,    0,    0,    0,
   57,    0,    0,    0,   50,   49,    0,    0,    0,    0,
    0,   94,   95,   93,    0,    0,    0,    0,  155,  154,
    0,    0,    0,    0,    0,  129,  127,  125,  126,  124,
    0,    0,    0,  150,  149,    0,    0,    0,    0,    0,
   65,   64,   63,    0,    0,  135,    0,    0,    0,    0,
    0,    0,    0,   54,    0,    0,    0,   55,   47,   48,
   46,    0,    0,    0,    0,    0,    0,  102,    0,    0,
    0,    0,    0,    0,    0,  148,    0,    0,  147,    0,
  134,    0,  133,  131,    0,    0,    0,   21,   52,    0,
  101,    0,    0,    0,    0,  108,    0,    0,  100,    0,
  144,    0,  143,  146,  142,  136,  132,  130,   20,   19,
   18,    0,    0,  106,    0,   98,   96,  105,    0,  145,
  141,  104,    0,    0,  109,  103,  107,   99,   97,
};
final static short yydgoto[] = {                          3,
    4,   19,   20,   21,   68,   23,   69,   25,   91,   26,
   27,   28,   51,   30,   31,   32,   33,   34,   35,   52,
  156,   53,   54,   55,   56,   70,  140,  108,   62,   63,
  128,
};
final static short yysindex[] = {                      -220,
 -178, -182,    0,  487,    0,    0,    0,   57,   10,  -28,
  102,  277,  124,  551, -223,    0,    0,    5,  401,    0,
    0,    0, -192,  104,  139,  103,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  230,    0,  164,    0,  170,
 -168,  -58,  -13,  165,  184,    0,    0,    0,  196, -195,
    0,   53,  176,    0,    0,  -27,  156,    0,  193, -206,
  237,  173,  180,  207,   42,   26,  590,    0,  -25,  -39,
  255,  123,    0,    0,   57,    0,    0,  137,  205,  238,
    0,  288,  214,    0,   91,  245,    0,    0,  101,  223,
  329,  587,   74,  748,  169,  -17,    0,    0,    0,    0,
  220,  232,  250,  268,    0,    0,    0,  276,  282,  -20,
    0,    0,    0,  276,  306,  309,  312,  320,  338,  570,
  119,  822,  351,  347,  413, -206,  411,   -9,   68,  149,
  201,    0,    0,    0,    0,  829,  594,  425,    0,  584,
  146,  196,    0,    0,    0,    0,    0,  212,    0,    0,
    0,    0,  225,  789,    1,  -31,  459,  -21,  475,    0,
    0,    0,  263,    0,    0,    0,  464,  141,  495,  346,
  290,  333,  176,  333,  176,    0,    0,    0,    0,  566,
   75,  836,  636,  570,  287,   75,  333,  176,  333,  176,
  566,   75,    0,    0,    0,    0,  267,  570,  496,  145,
    0,  148,  154,  151, -206, -206,  299,  304,  322,  360,
  307,  532,  158,  196,    0,    0,  196,  178,  580,   11,
    0,  276,  276,  349,    0,    0,  276,  363,  373,  375,
  487,    0,    0,    0,  381,  741,  163,  596,    0,    0,
  272,  557,  570,  585,  308,    0,    0,    0,    0,    0,
  387,  391,  392,    0,    0,  391,  390,  397,  391,  394,
    0,    0,    0,  614,  328,    0,  597,  600,   75,   75,
  566,   75,   75,    0,  487,  507,  426,    0,    0,    0,
    0,  406,  570,  605,  311,  175,  404,    0,  570,  606,
  414,  405,   90,  391,   96,    0,  421,  114,    0,  625,
    0,  204,    0,    0,  447,  -38,  467,    0,    0,  433,
    0,  434,  630,  570,  257,    0,  643,  441,    0,  449,
    0,  159,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  650,  446,    0,  -19,    0,    0,    0,  652,    0,
    0,    0,  655,  266,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0, -179,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -36,    0,    0,    0,    0,    0,    0,
    0,    0,   -2,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  717,    0,    0,    0,    0,    0,
    0,   27,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   82,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   40,   49,   62,   84,    0,    0,    0,    0,    0,
   32,    0,    0,    0,    0,   36,   94,  106,  116,  128,
  133,  143,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  126,  256,
  284,  356,  384,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0, -144,  -15,    0,   12,  592,   -1,    0,  639,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,  579,
    0,  477,  396,    0,  303,  358,    0,  676,    0,    0,
  -22,
};
final static int YYTABLESIZE=883;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
  142,   36,   24,   77,   84,   84,   84,   84,   84,   29,
   84,   49,  227,  121,   29,   22,   50,   24,   85,  229,
  185,   80,   84,   84,   84,   84,  171,  226,  103,  101,
   22,  102,   71,  104,  206,    1,    2,  130,   74,  345,
   74,   74,   74,  115,  224,  116,  106,  105,  107,   40,
   58,   72,  207,   42,  171,   41,   74,   74,   74,   74,
  111,  112,   29,   74,   78,   40,   16,   17,   39,   42,
   56,   41,  116,    6,   79,    7,  115,   30,  139,    5,
   72,  113,   72,   72,   72,   56,  277,   30,   96,   70,
  116,   70,   70,   70,  115,  115,   36,  116,   72,   72,
   72,   72,   73,  204,   73,   73,   73,   70,   70,   70,
   70,  205,  106,  105,  107,   29,  115,  115,  116,  116,
   73,   73,   73,   73,   71,   53,   71,   71,   71,  209,
  305,  307,  166,   85,   68,   29,   68,   68,   68,   85,
   53,   57,   71,   71,   71,   71,   66,   85,   66,   66,
   66,  216,   68,   68,   68,   68,   69,   85,   69,   69,
   69,   88,   84,   64,   66,   66,   66,   66,   67,   61,
   67,   67,   67,  117,   69,   69,   69,   69,   86,   29,
   42,  145,  148,  114,   61,  217,   67,   67,   67,   67,
   50,  117,  206,   29,  206,  147,  124,  205,   97,  234,
   50,  114,   85,  248,   40,  115,  250,  116,   50,  168,
  210,  115,  253,  116,   50,  251,  263,  118,  267,   84,
  330,  281,  119,  109,  225,   84,   29,   43,   44,   24,
  141,   82,   45,  316,  120,  160,  266,   29,   29,  169,
   50,  184,   22,   84,  344,   84,   84,   46,   47,   48,
  293,   50,  126,   74,  295,   83,  223,  298,   50,   74,
   73,   77,  328,  170,   50,   37,   98,  169,   99,  100,
   29,   29,   29,   24,   24,   24,   50,   74,   29,   74,
   74,   85,   56,   56,   29,   56,   22,   22,   22,   77,
   38,   77,  322,  116,   50,   72,  152,  115,  131,   60,
   29,   72,   29,   24,   70,   24,   38,   56,  114,   29,
   70,  138,   50,  143,   60,  337,   22,   73,   22,   72,
   50,   72,   72,   73,  349,  208,   50,   62,   70,  165,
   70,   70,   98,  153,   99,  100,   60,   53,   53,   71,
   53,   73,   62,   73,   73,   71,   82,  157,  321,   68,
   50,  110,   82,   50,  323,   68,   50,  160,   87,   81,
   82,   66,   53,   71,   50,   71,   71,   66,  302,  163,
   82,   69,  325,   68,  103,   68,   68,   69,  144,  104,
  198,   61,   50,   67,   83,   66,  301,   66,   66,   67,
   50,  200,  146,   50,  117,   69,  233,   69,   69,   58,
  247,   43,   44,  249,  114,  201,   45,   67,  252,   67,
   67,  122,   44,  262,   58,   82,   45,  341,  280,   92,
   44,   46,   47,   48,   45,   94,   44,   59,  132,  133,
   45,   46,   47,   48,  123,  134,  135,  314,  315,   46,
   47,   48,   59,  218,  219,   46,   47,   48,   42,   58,
  148,   43,   44,  202,  205,  115,   45,  116,  211,  327,
  149,  150,  136,   44,  214,   16,   17,   45,  220,  154,
   44,   46,   47,   48,   45,  172,   44,  197,  161,  162,
   45,  221,   46,   47,   48,   89,   58,  174,   44,   46,
   47,   48,   45,  151,   82,   46,   47,   48,  177,  179,
  158,   58,   16,   17,  228,  176,   44,   46,   47,   48,
   45,   60,  336,  194,  196,  230,  264,   16,   17,  265,
  231,  348,  232,  178,   44,   46,   47,   48,   45,  243,
  244,  180,   44,   58,  283,  284,   45,  182,   44,   62,
  235,  241,   45,   46,   47,   48,  238,   59,  242,   16,
   17,   46,   47,   48,  246,  245,  256,   46,   47,   48,
  257,  187,   44,  260,  189,   44,   45,  191,   44,   45,
  289,  290,   45,  312,  313,  193,   44,  173,  175,  258,
   45,   46,   47,   48,   46,   47,   48,   46,   47,   48,
  261,  188,  190,  195,   44,   46,   47,   48,   45,  286,
  287,  236,   44,   61,  271,   44,   45,  103,  101,   45,
  102,   58,  104,   46,   47,   48,   93,  259,   95,  274,
  268,   46,   47,   48,   46,   47,   48,   90,  103,  101,
  275,  102,  276,  104,  213,  125,  115,  278,  116,   59,
  310,  282,  137,  288,  291,  164,  318,  292,  296,  294,
  127,  129,  299,  297,  300,  303,   75,    9,  304,   76,
   10,  155,  309,  311,  319,   11,   12,  317,   13,   14,
  320,  335,   15,   16,   17,   18,  240,   90,  115,  324,
  116,    8,    9,  326,  308,   10,  181,  183,  334,  333,
   11,   12,  186,   13,   14,  192,  332,   15,   16,   17,
   18,  338,    8,    9,  339,  329,   10,  340,  342,  343,
  346,   11,   12,  347,   13,   14,    2,  203,   15,   16,
   17,   18,    8,    9,  159,  331,   10,  117,    0,    0,
    0,   11,   12,    0,   13,   14,    0,    0,   15,   16,
   17,   18,    8,    9,    0,    0,   10,    0,  237,    0,
    0,   11,   12,    0,   13,   14,    0,    0,   15,   16,
   17,   18,  306,    9,    0,    0,   10,    0,    0,    0,
    0,   11,   12,    0,   13,   14,    0,    0,   15,   16,
   17,   18,  103,  101,    0,  102,    0,  104,  167,  103,
  101,    0,  102,    0,  104,    0,  254,  255,    0,  279,
  269,  270,  272,    0,    0,  273,   65,   66,   67,    0,
   10,    0,  285,   66,   67,   11,   10,    0,   13,   14,
    0,   11,   15,    0,   13,   14,   66,   67,   15,   10,
  103,  101,  222,  102,   11,  104,    0,   13,   14,    0,
   66,   15,  215,   10,    0,    0,   66,    0,   11,   10,
    0,   13,   14,    0,   11,   15,    0,   13,   14,    0,
    0,   15,  199,  103,  101,    0,  102,    0,  104,  212,
  103,  101,    0,  102,    0,  104,  239,  103,  101,    0,
  102,    0,  104,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
   40,   40,    4,   19,   41,   42,   43,   44,   45,   14,
   47,   40,   44,   41,   19,    4,   45,   19,   44,   41,
   41,   23,   59,   60,   61,   62,   44,   59,   42,   43,
   19,   45,  256,   47,   44,  256,  257,   60,   41,   59,
   43,   44,   45,   43,   44,   45,   60,   61,   62,   40,
  257,  275,   62,   44,   44,   46,   59,   60,   61,   62,
  256,  257,   67,   59,  257,   40,  273,  274,   59,   44,
   44,   46,   41,  256,  267,  258,   41,  257,   67,  258,
   41,  277,   43,   44,   45,   59,  231,  267,  257,   41,
   59,   43,   44,   45,   59,   43,   40,   45,   59,   60,
   61,   62,   41,  126,   43,   44,   45,   59,   60,   61,
   62,   44,   60,   61,   62,  120,   43,   43,   45,   45,
   59,   60,   61,   62,   41,   44,   43,   44,   45,   62,
  275,  276,   59,   44,   41,  140,   43,   44,   45,   44,
   59,   40,   59,   60,   61,   62,   41,   44,   43,   44,
   45,  140,   59,   60,   61,   62,   41,   44,   43,   44,
   45,   59,   59,   40,   59,   60,   61,   62,   41,   44,
   43,   44,   45,   41,   59,   60,   61,   62,   40,  184,
   44,   59,   46,   41,   59,   40,   59,   60,   61,   62,
   45,   59,   44,  198,   44,   59,   41,   44,  257,   59,
   45,   59,   44,   59,   40,   43,   59,   45,   45,   41,
   62,   43,   62,   45,   45,   62,   59,   42,   41,  256,
  259,   59,   47,   40,  256,  262,  231,  256,  257,  231,
  270,  257,  261,   59,  262,  257,   59,  242,  243,  257,
   45,  262,  231,  280,  264,  282,  283,  276,  277,  278,
  252,   45,   60,  256,  256,  281,  256,  259,   45,  262,
  256,  277,   59,  281,   45,  256,  280,  257,  282,  283,
  275,  276,  277,  275,  276,  277,   45,  280,  283,  282,
  283,   44,  256,  257,  289,  259,  275,  276,  277,  305,
  281,  307,  294,  262,   45,  256,   59,  262,   62,   44,
  305,  262,  307,  305,  256,  307,  281,  281,  256,  314,
  262,  270,   45,   59,   59,   59,  305,  256,  307,  280,
   45,  282,  283,  262,   59,  258,   45,   44,  280,  256,
  282,  283,  280,   46,  282,  283,   60,  256,  257,  256,
  259,  280,   59,  282,  283,  262,  257,  257,  259,  256,
   45,   49,  257,   45,  259,  262,   45,  257,  256,  256,
  257,  256,  281,  280,   45,  282,  283,  262,   41,   41,
  257,  256,  259,  280,   42,  282,  283,  262,  256,   47,
  262,  256,   45,  256,  281,  280,   59,  282,  283,  262,
   45,   41,  256,   45,  262,  280,  256,  282,  283,   44,
  256,  256,  257,  256,  262,   59,  261,  280,  258,  282,
  283,  256,  257,  256,   59,  257,  261,  259,  256,  256,
  257,  276,  277,  278,  261,  256,  257,   44,  256,  257,
  261,  276,  277,  278,  279,  256,  257,  263,  264,  276,
  277,  278,   59,  141,  142,  276,  277,  278,   44,  257,
   46,  256,  257,   41,   44,   43,  261,   45,  258,  256,
  256,  257,  256,  257,   40,  273,  274,  261,  257,  256,
  257,  276,  277,  278,  261,  256,  257,  120,  256,  257,
  261,  257,  276,  277,  278,  256,  257,  256,  257,  276,
  277,  278,  261,  256,  257,  276,  277,  278,  103,  104,
  256,  257,  273,  274,   46,  256,  257,  276,  277,  278,
  261,  256,  256,  118,  119,   41,  214,  273,  274,  217,
  258,  256,   59,  256,  257,  276,  277,  278,  261,  263,
  264,  256,  257,  257,  263,  264,  261,  256,  257,  256,
   46,  184,  261,  276,  277,  278,  257,  271,  262,  273,
  274,  276,  277,  278,   59,  198,  258,  276,  277,  278,
  257,  256,  257,  257,  256,  257,  261,  256,  257,  261,
  263,  264,  261,  263,  264,  256,  257,  101,  102,  258,
  261,  276,  277,  278,  276,  277,  278,  276,  277,  278,
   59,  115,  116,  256,  257,  276,  277,  278,  261,  242,
  243,  256,  257,   12,  256,  257,  261,   42,   43,  261,
   45,  256,   47,  276,  277,  278,   38,  258,   40,  257,
   41,  276,  277,  278,  276,  277,  278,   36,   42,   43,
  258,   45,  258,   47,   41,   57,   43,  257,   45,  256,
  283,   46,   64,   59,  258,   59,  289,  257,  259,  258,
   59,   60,  259,  257,   41,   59,  256,  257,   59,  259,
  260,   83,  257,   59,   59,  265,  266,  264,  268,  269,
  257,  314,  272,  273,  274,  275,   41,   86,   43,  259,
   45,  256,  257,   59,  259,  260,  108,  109,   59,  256,
  265,  266,  114,  268,  269,  117,  264,  272,  273,  274,
  275,   59,  256,  257,  264,  259,  260,  259,   59,  264,
   59,  265,  266,   59,  268,  269,    0,  126,  272,  273,
  274,  275,  256,  257,   86,  259,  260,   52,   -1,   -1,
   -1,  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,
  274,  275,  256,  257,   -1,   -1,  260,   -1,  170,   -1,
   -1,  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,
  274,  275,  256,  257,   -1,   -1,  260,   -1,   -1,   -1,
   -1,  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,
  274,  275,   42,   43,   -1,   45,   -1,   47,   41,   42,
   43,   -1,   45,   -1,   47,   -1,  205,  206,   -1,   59,
  222,  223,  224,   -1,   -1,  227,  256,  257,  258,   -1,
  260,   -1,  256,  257,  258,  265,  260,   -1,  268,  269,
   -1,  265,  272,   -1,  268,  269,  257,  258,  272,  260,
   42,   43,   44,   45,  265,   47,   -1,  268,  269,   -1,
  257,  272,  259,  260,   -1,   -1,  257,   -1,  265,  260,
   -1,  268,  269,   -1,  265,  272,   -1,  268,  269,   -1,
   -1,  272,   41,   42,   43,   -1,   45,   -1,   47,   41,
   42,   43,   -1,   45,   -1,   47,   41,   42,   43,   -1,
   45,   -1,   47,
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
"factor : invocacion_funcion",
"factor : conversion_explicita",
"factor : '-' ID",
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

//#line 317 "gramatica.y"
  
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
//#line 812 "Parser.java"
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
                                                                                    yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador, ts.devolverTipo(val_peek(6).sval));
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
case 31:
//#line 87 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      nameMangling(val_peek(0).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      yyval.sval = val_peek(1).sval;
                     }
break;
case 32:
//#line 92 "gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 33:
//#line 93 "gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 43:
//#line 109 "gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 44:
//#line 113 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 45:
//#line 114 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 47:
//#line 116 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 48:
//#line 117 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 49:
//#line 120 "gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
                                                                         if (!igualCantElementos(val_peek(3).sval,val_peek(1).sval)) {yyerror(ERROR_CANTIDAD_ASIGNACION);}
                                                                        }
break;
case 50:
//#line 124 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 51:
//#line 127 "gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 53:
//#line 131 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 55:
//#line 136 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 137 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 138 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 141 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 59:
//#line 142 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 60:
//#line 143 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 61:
//#line 145 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 62:
//#line 146 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 150 "gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 64:
//#line 152 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 65:
//#line 153 "gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 66:
//#line 156 "gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 67:
//#line 160 "gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 68:
//#line 164 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 69:
//#line 165 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 166 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 167 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 168 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 169 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 170 "gramatica.y"
{ yyval = val_peek(0);  }
break;
case 75:
//#line 173 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 76:
//#line 177 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 181 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 78:
//#line 182 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 183 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 184 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 185 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 186 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 187 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 84:
//#line 190 "gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 85:
//#line 193 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"UINTEGER");  /* Nodo para constante UINTEGER*/
         }
break;
case 86:
//#line 196 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"SINGLE");  /* Nodo para constante SINGLE*/
         }
break;
case 87:
//#line 199 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"HEXA");  /* Nodo para constante HEXA*/
         }
break;
case 90:
//#line 204 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable negativa*/
        }
break;
case 91:
//#line 207 "gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval,"SINGLE");}
break;
case 92:
//#line 208 "gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 93:
//#line 211 "gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);
                                                System.out.println("NODO EXPRESION: " + val_peek(2).obj.toString());
                                                if(!paramRealIgualFormal(val_peek(4).sval, ((Nodo)val_peek(2).obj).devolverTipo(mangling))) {yyerror(ERROR_TIPO_PARAMETRO);}
                                               }
break;
case 94:
//#line 215 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 95:
//#line 216 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 96:
//#line 219 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null));
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 97:
//#line 223 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 98:
//#line 227 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 99:
//#line 228 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 100:
//#line 229 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 101:
//#line 230 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 102:
//#line 231 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 232 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 233 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 234 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 235 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 107:
//#line 236 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 108:
//#line 237 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 109:
//#line 238 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 110:
//#line 241 "gramatica.y"
{yyval = val_peek(1);}
break;
case 111:
//#line 242 "gramatica.y"
{yyval = val_peek(0);}
break;
case 112:
//#line 245 "gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 113:
//#line 246 "gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 249 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 115:
//#line 250 "gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 116:
//#line 251 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 117:
//#line 252 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 124:
//#line 263 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 125:
//#line 264 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 126:
//#line 265 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 127:
//#line 266 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 128:
//#line 267 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 129:
//#line 268 "gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 130:
//#line 271 "gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 131:
//#line 275 "gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 132:
//#line 276 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 133:
//#line 277 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 134:
//#line 278 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 135:
//#line 279 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 280 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 137:
//#line 283 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct"); ts.insertar(new TokenStruct( 257, nameMangling(val_peek(0).sval), val_peek(1).sval )); }
break;
case 138:
//#line 284 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct"); ts.insertar(new TokenStruct( 257, nameMangling(val_peek(0).sval), val_peek(1).sval ));}
break;
case 139:
//#line 285 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 140:
//#line 286 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 141:
//#line 289 "gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);  yyval.sval = val_peek(1).sval+"."+val_peek(4).sval;}
break;
case 142:
//#line 290 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 143:
//#line 291 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 144:
//#line 292 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 145:
//#line 295 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); yyval.sval = val_peek(1).sval+"."+ val_peek(4).sval;}
break;
case 146:
//#line 296 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 147:
//#line 297 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 148:
//#line 298 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 149:
//#line 303 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 150:
//#line 304 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 151:
//#line 307 "gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 152:
//#line 308 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 153:
//#line 309 "gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 154:
//#line 312 "gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(1).obj,null,"SINGLE");}
break;
case 155:
//#line 313 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1557 "Parser.java"
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
