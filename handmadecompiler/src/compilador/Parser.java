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
    0,    0,    1,    1,    1,    2,    2,    3,    3,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,   11,    8,    6,    6,    6,
    9,    9,    9,    5,    5,    5,    5,    5,    5,    5,
    5,   12,   12,   20,   20,   20,   20,   20,   20,   21,
   21,    7,    7,    7,    7,    7,    7,    7,    7,   23,
   23,   23,   23,   23,   13,   13,   13,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   14,   14,   14,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   27,   27,   28,   28,   26,   26,   26,   26,
   29,   29,   29,   29,   29,   29,   16,   16,   16,   16,
   16,   16,   17,   17,   17,   17,   17,   17,   17,   10,
   10,   10,   10,   30,   30,   30,   30,   31,   31,   31,
   31,   32,   32,   18,   18,   18,   19,   19,   19,
};
final static short yylen[] = {                            2,
    3,    3,    2,    2,    2,    1,    2,    1,    1,    3,
    2,    2,    3,    3,    2,    2,    2,    7,    7,    7,
    7,    2,    2,    3,    2,    0,    7,    1,    1,    1,
    2,    2,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    4,    4,    4,    6,    6,    6,    4,
    4,    3,    7,    3,    5,    2,    6,    2,    4,    3,
    3,    3,    3,    3,    5,    5,    5,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    3,    3,
    3,    3,    3,    3,    1,    1,    3,    1,    1,    1,
    1,    2,    4,    2,    2,    5,    5,    5,    8,   10,
    8,   10,    7,    7,    6,    9,    9,    8,    8,   10,
    7,    9,    3,    1,    2,    1,    3,    3,    3,    3,
    1,    1,    1,    1,    1,    1,    5,    5,    5,    5,
    4,    5,    7,    6,    7,    6,    6,    5,    7,    3,
    3,    3,    3,    7,    6,    6,    6,    7,    6,    5,
    5,    3,    3,    3,    3,    3,    5,    5,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    5,    4,    3,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   29,   28,    0,    0,
    0,    6,    8,    9,    0,    0,    0,    0,   34,   35,
   36,   37,   38,   39,   40,   41,   42,   43,    0,   16,
   56,    0,   15,    0,    0,    0,    0,   88,   89,   90,
    0,    0,    0,   91,    0,    0,   85,    0,    0,    0,
    0,   30,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  114,    0,    0,    0,    0,   12,   11,    0,    0,
    1,    7,    0,    0,    0,    0,   25,   58,    0,    0,
   17,    0,    0,   23,   22,    0,    0,    0,    0,    0,
    0,    0,   52,  125,  126,  122,    0,    0,    0,    0,
  121,  123,  124,    0,    0,    0,   95,   92,   94,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  142,  140,  143,  141,    0,    0,    0,  116,    0,    0,
    0,  156,  155,  154,    0,   14,   13,   26,    0,   24,
   10,    0,    0,    0,    0,   54,    0,    0,    0,   33,
   32,   31,    0,   46,   45,   44,    0,    0,    0,    0,
    0,    0,   83,   81,   84,   82,    0,    0,   87,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   79,
   77,   80,   78,    0,    0,    0,    0,    0,    0,  131,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  113,  115,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   51,   50,    0,   59,
    0,    0,    0,    0,   97,   98,   96,    0,    0,   93,
    0,    0,    0,  159,  158,  157,  132,  130,  128,  129,
  127,    0,    0,    0,  153,  152,    0,    0,    0,    0,
    0,   67,   66,   65,    0,    0,  138,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   55,
    0,    0,    0,    0,    0,    0,    0,    0,  105,    0,
    0,    0,    0,    0,    0,    0,  151,    0,    0,  150,
    0,  137,    0,  136,  134,   48,   49,   47,   57,    0,
    0,    0,    0,    0,   21,    0,  104,    0,    0,    0,
    0,  111,    0,    0,  103,    0,  147,    0,  146,  149,
  145,  139,  135,  133,   53,   27,   20,   19,   18,    0,
    0,  109,    0,  101,   99,  108,    0,  148,  144,  107,
    0,    0,  112,  106,  110,  102,  100,
};
final static short yydgoto[] = {                          3,
    4,   21,   22,   23,   72,   25,   73,   27,   98,   28,
  232,   29,   30,   54,   32,   33,   34,   35,   36,   37,
   38,   55,  164,   56,   57,   58,   74,  149,  114,   65,
   66,  137,
};
final static short yysindex[] = {                       109,
 -230, -208,    0,  515,    0,    0,    0,    2,  -15,  -22,
   17,   28,  262,   36,  204, -213,    0,    0,   68,    5,
  412,    0,    0,    0, -235,  -38,   42,   97,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  197,    0,
    0,  161,    0,  170, -150,  160,  152,    0,    0,    0,
   98,  179,  226,    0,   24,   -2,    0,  -34,  218,  -40,
   10,    0,  213,  117,  194,  202,  222,   53,  -32,  606,
    5,    0,   -9,  -37,  258,  104,    0,    0,   79,    2,
    0,    0,  281,  102,  340,  -25,    0,    0,  236,  356,
    0, -176,  245,    0,    0,  135,  228,  374,  608,   35,
  378,  777,    0,    0,    0,    0,  248,  271,  274,  284,
    0,    0,    0,  300,  165,   20,    0,    0,    0,  398,
  300,  313,  317,  329,  343,  346,  586,  190,  788,  807,
  844,  425,  409,  856,  213,  433,    3,  -29,   43,  232,
    0,    0,    0,    0,  853,  861,  440,    0,  600,    1,
  179,    0,    0,    0,  -20,    0,    0,    0,  254,    0,
    0,  614,   15,   -5,  260,    0,  463,  -27,  493,    0,
    0,    0,  295,    0,    0,    0,  480,  137,  157,   -2,
  157,   -2,    0,    0,    0,    0,  676,   56,    0,  586,
  280,  302,   56,  157,   -2,  157,   -2,  676,   56,    0,
    0,    0,    0,  252,  586,  505,  138,  508,  139,    0,
  168,   54,   -6,  213,  213,  321,  318,  334,  338,  341,
  545,  287,  179,    0,    0,  179,   87,  569,  355,  568,
  331,  213,  -24,  300,  300,  360,    0,    0,  300,    0,
  361,  368,  371,  515,    0,    0,    0,  259,  556,    0,
  586,  576,  324,    0,    0,    0,    0,    0,    0,    0,
    0,  382, -174,  383,    0,    0, -174,  389,  395, -174,
  405,    0,    0,    0,  613,  326,    0,  607,  617,  834,
  125,  408,  633,  415,   56,   56,  676,   56,   56,    0,
  515,  535,  441,  586,  624,  345,  -50,  424,    0,  586,
  632,  437,    8,   11, -174,   95,    0,  445,  123,    0,
  640,    0,  288,    0,    0,    0,    0,    0,    0,  446,
  449,  474,  -36,  494,    0,  447,    0,  452,  653,  586,
  289,    0,  661,  458,    0,  467,    0,  130,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  669,
  465,    0,   92,    0,    0,    0,  673,    0,    0,    0,
  677,  298,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -231,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   30,    0,    0,    0,
    0,    0,    0,    0,    0,   52,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  737,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   59,   81,
   88,   93,    0,    0,    0,    0,    0,   47,    0,    0,
    0,    0,   76,  116,  121,  128,  150,  167,  310,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   71,  141,  369,  372,  390,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0, -228,  -19,    0,    9,  428,    6,    0,  648,    0,
    0,    0,    0,   -4,    0,    0,    0,    0,    0,    0,
    0,  603,    0,  567,  456,  357,  444,    0,  690,    0,
    0,  -26,
};
final static int YYTABLESIZE=906;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
  133,   82,  151,   39,   53,   92,  128,   44,  332,   26,
   31,   45,   24,  242,  214,  293,   31,   52,   92,  231,
   91,   83,   53,  231,   44,   30,   26,    5,   45,   24,
   86,   84,  218,  161,   92,   30,  139,  215,  239,  125,
  226,   39,   75,   43,  126,   53,  215,    6,   85,    7,
   79,   45,   30,  238,   92,  264,   59,  122,  236,  123,
  191,   76,  322,  324,  216,   31,  122,   60,  123,  135,
   86,   86,   86,   86,   86,   67,   86,  122,  148,  123,
  166,   93,  303,  112,  111,  113,  215,  119,   86,   86,
   86,   86,   76,  176,   76,   76,   76,  214,  122,   74,
  123,   74,   74,   74,  219,  119,  103,  167,  213,   85,
   76,   76,   76,   76,   63,  262,  118,   74,   74,   74,
   74,   72,   31,   72,   72,   72,   78,  278,   75,   63,
   75,   75,   75,   73,  118,   73,   73,   73,   92,   72,
   72,   72,   72,  115,   31,  277,   75,   75,   75,   75,
  363,   73,   73,   73,   73,   95,   70,  225,   70,   70,
   70,   68,  154,   68,   68,   68,   92,  122,   71,  123,
   71,   71,   71,   92,   70,   70,   70,   70,  140,   68,
   68,   68,   68,  318,   62,   31,   71,   71,   71,   71,
   69,   44,   69,   69,   69,  247,  256,  259,  109,   62,
   31,  109,  107,  110,  108,   53,  110,  120,   69,   69,
   69,   69,  330,  331,   53,  131,   47,   87,   88,  112,
  111,  113,  348,   53,   41,  120,  261,  127,  217,  170,
  160,   88,  150,   46,   47,   48,   49,   50,  132,   31,
   40,   41,   89,   51,   31,   90,   31,   88,   42,   26,
  237,  263,   24,   48,   49,   50,   46,   47,   90,  230,
  229,   51,   53,  230,   41,   42,   53,   88,  304,  337,
  235,   89,  306,   82,   90,  309,   48,   49,   50,  121,
   53,  190,   17,   18,   51,   86,   31,   31,   31,   31,
  175,   86,   53,   62,   90,   31,   26,   26,   26,   24,
   24,   24,   82,  104,   82,  105,  106,   76,  119,   86,
  338,   86,   86,   76,   74,   53,  152,   31,   53,   31,
   74,   63,  147,   77,   45,   31,   63,   26,   53,   26,
   24,   76,   24,   76,   76,  155,   72,  118,   74,  157,
   74,   74,   72,   75,   53,  274,  344,  355,   73,   75,
  117,   88,   94,  339,   73,  362,  367,   53,  158,  153,
   72,   53,   72,   72,    1,    2,  313,   75,  117,   75,
   75,   70,   73,   53,   73,   73,   68,   70,   90,   88,
  317,  341,   68,   71,  312,  159,   88,   53,  359,   71,
   53,  170,  246,  255,  258,   70,   62,   70,   70,   53,
   68,  165,   68,   68,   53,   69,   90,   71,  116,   71,
   71,   69,   64,   90,  173,   60,   99,   47,  177,  109,
  107,  189,  108,  260,  110,  101,   47,   64,  120,   69,
   60,   69,   69,   61,   46,   47,   48,   49,   50,  104,
   64,  105,  106,  192,   51,   48,   49,   50,   61,  141,
  142,  205,   96,   51,   48,   49,   50,  143,  144,   68,
   69,   70,   51,   10,   11,  209,   97,  210,   12,   17,
   18,   14,   15,  129,   47,   16,  214,  145,   47,  223,
   62,  117,  118,  171,  172,   17,   18,   71,  136,  220,
  138,  162,   47,   48,   49,   50,   62,   48,   49,   50,
  168,   51,  119,  179,   47,   51,  227,  228,  241,  120,
  233,   48,   49,   50,  251,  252,  240,   17,   18,   51,
   97,  294,  295,   48,   49,   50,  181,   47,   62,  183,
   47,   51,   61,  243,   17,   18,  156,   41,  245,  185,
   47,  249,  273,  343,  354,   62,   48,   49,   50,   48,
   49,   50,  244,  366,   51,  187,   47,   51,  250,   48,
   49,   50,  212,  254,  184,  186,  257,   51,  194,   47,
  204,  117,  196,   47,  268,   48,   49,   50,  267,  275,
  201,  203,  276,   51,  198,   47,  300,  301,   48,   49,
   50,  269,   48,   49,   50,  270,   51,  271,  200,   47,
   51,  202,   47,  272,   48,   49,   50,  328,  329,  279,
  280,   47,   51,  282,  283,  287,   47,  290,   48,   49,
   50,   48,   49,   50,   64,  291,   51,   60,  292,   51,
   48,   49,   50,  248,  299,   48,   49,   50,   51,  302,
  305,  265,  266,   51,  100,   61,  102,  307,  253,  109,
  107,  308,  108,  311,  110,  109,  107,  234,  108,  284,
  110,  130,  134,  310,  319,  314,  174,   80,    9,  146,
   81,   10,   11,  180,  182,  315,   12,   13,  320,   14,
   15,  321,  327,   16,   17,   18,   19,  333,  195,  197,
  335,  163,  297,  336,  298,   20,    8,    9,  342,  325,
   10,   11,  345,  340,  346,   12,   13,  351,   14,   15,
  350,  352,   16,   17,   18,   19,  188,  109,  107,  356,
  108,  357,  110,  193,   20,  358,  199,  360,  361,    8,
    9,  364,  347,   10,   11,  365,    2,  326,   12,   13,
  169,   14,   15,  334,  124,   16,   17,   18,   19,    8,
    9,    0,  349,   10,   11,    0,    0,   20,   12,   13,
    0,   14,   15,    0,    0,   16,   17,   18,   19,    0,
    8,    9,    0,  353,   10,   11,    0,   20,    0,   12,
   13,    0,   14,   15,    0,    0,   16,   17,   18,   19,
  323,    9,    0,    0,   10,   11,    0,    0,   20,   12,
   13,    0,   14,   15,    0,    0,   16,   17,   18,   19,
    0,  296,   69,   70,    0,   10,   11,  178,   20,  122,
   12,  123,    0,   14,   15,    0,    0,   16,  206,  109,
  107,  281,  108,    0,  110,    0,  285,  286,  288,   71,
    0,  289,   69,   70,    0,   10,   11,  207,    0,  122,
   12,  123,    0,   14,   15,    0,   69,   16,  224,   10,
   11,    0,   69,    0,   12,   10,   11,   14,   15,   71,
   12,   16,    0,   14,   15,  109,  107,   16,  108,    0,
  110,    0,    0,   71,  208,  109,  107,    0,  108,   71,
  110,    0,  316,  221,  109,  107,  211,  108,  122,  110,
  123,  222,    0,  122,    0,  123,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
   41,   21,   40,   40,   45,   44,   41,   40,   59,    4,
   15,   44,    4,   41,   44,  244,   21,   40,   44,   44,
   59,  257,   45,   44,   40,  257,   21,  258,   44,   21,
   25,  267,   62,   59,   44,  267,   63,   44,   44,   42,
   40,   40,  256,   59,   47,   45,   44,  256,  284,  258,
   46,   44,  284,   59,   44,   62,   40,   43,   44,   45,
   41,  275,  291,  292,   62,   70,   43,   40,   45,   60,
   41,   42,   43,   44,   45,   40,   47,   43,   70,   45,
  257,   40,  257,   60,   61,   62,   44,   41,   59,   60,
   61,   62,   41,   59,   43,   44,   45,   44,   43,   41,
   45,   43,   44,   45,   62,   59,  257,  284,  135,  284,
   59,   60,   61,   62,   44,   62,   41,   59,   60,   61,
   62,   41,  127,   43,   44,   45,   59,   41,   41,   59,
   43,   44,   45,   41,   59,   43,   44,   45,   44,   59,
   60,   61,   62,   46,  149,   59,   59,   60,   61,   62,
   59,   59,   60,   61,   62,   59,   41,  149,   43,   44,
   45,   41,   59,   43,   44,   45,   44,   43,   41,   45,
   43,   44,   45,   44,   59,   60,   61,   62,   62,   59,
   60,   61,   62,   59,   44,  190,   59,   60,   61,   62,
   41,   40,   43,   44,   45,   59,   59,   59,   42,   59,
  205,   42,   43,   47,   45,   45,   47,   41,   59,   60,
   61,   62,  263,  264,   45,  256,  257,  256,  257,   60,
   61,   62,  259,   45,  257,   59,   59,  262,  258,  257,
  256,  257,  270,  256,  257,  276,  277,  278,  279,  244,
  256,  257,  281,  284,  249,  284,  251,  257,  281,  244,
  256,  258,  244,  276,  277,  278,  256,  257,  284,  284,
  281,  284,   45,  284,  257,  281,   45,  257,  263,  259,
  256,  281,  267,  293,  284,  270,  276,  277,  278,  256,
   45,  262,  273,  274,  284,  256,  291,  292,  293,  294,
  256,  262,   45,  284,  284,  300,  291,  292,  293,  291,
  292,  293,  322,  280,  324,  282,  283,  256,  262,  280,
  305,  282,  283,  262,  256,   45,   59,  322,   45,  324,
  262,   60,  270,  256,   44,  330,  256,  322,   45,  324,
  322,  280,  324,  282,  283,  257,  256,  262,  280,   59,
  282,  283,  262,  256,   45,   59,   59,   59,  256,  262,
   41,  257,  256,  259,  262,  264,   59,   45,  257,  256,
  280,   45,  282,  283,  256,  257,   41,  280,   59,  282,
  283,  256,  280,   45,  282,  283,  256,  262,  284,  257,
  256,  259,  262,  256,   59,   46,  257,   45,  259,  262,
   45,  257,  256,  256,  256,  280,  256,  282,  283,   45,
  280,   46,  282,  283,   45,  256,  284,  280,   52,  282,
  283,  262,   44,  284,   41,   44,  256,  257,   41,   42,
   43,  257,   45,  256,   47,  256,  257,   59,  262,  280,
   59,  282,  283,   44,  256,  257,  276,  277,  278,  280,
   13,  282,  283,   46,  284,  276,  277,  278,   59,  256,
  257,  262,  256,  284,  276,  277,  278,  256,  257,  256,
  257,  258,  284,  260,  261,   41,   39,   59,  265,  273,
  274,  268,  269,  256,  257,  272,   44,  256,  257,   40,
  284,  256,  257,  256,  257,  273,  274,  284,   61,  258,
   63,  256,  257,  276,  277,  278,  284,  276,  277,  278,
  256,  284,  277,  256,  257,  284,  150,  151,   46,  284,
  257,  276,  277,  278,  263,  264,  257,  273,  274,  284,
   93,  263,  264,  276,  277,  278,  256,  257,  284,  256,
  257,  284,  271,   41,  273,  274,  256,  257,   59,  256,
  257,  262,  256,  256,  256,  284,  276,  277,  278,  276,
  277,  278,  258,  256,  284,  256,  257,  284,  257,  276,
  277,  278,  135,   59,  109,  110,   59,  284,  256,  257,
  127,  262,  256,  257,  257,  276,  277,  278,  258,  223,
  125,  126,  226,  284,  256,  257,  263,  264,  276,  277,
  278,  258,  276,  277,  278,  258,  284,  257,  256,  257,
  284,  256,  257,   59,  276,  277,  278,  263,  264,   41,
  256,  257,  284,   46,  284,  256,  257,  257,  276,  277,
  278,  276,  277,  278,  256,  258,  284,  256,  258,  284,
  276,  277,  278,  190,   59,  276,  277,  278,  284,  258,
  258,  214,  215,  284,   42,  256,   44,  259,  205,   42,
   43,  257,   45,   41,   47,   42,   43,   44,   45,  232,
   47,   59,   60,  259,  257,   59,   59,  256,  257,   67,
  259,  260,  261,  107,  108,   59,  265,  266,   46,  268,
  269,  267,   59,  272,  273,  274,  275,  264,  122,  123,
   59,   89,  249,  257,  251,  284,  256,  257,   59,  259,
  260,  261,  257,  259,  256,  265,  266,  256,  268,  269,
  264,   59,  272,  273,  274,  275,  114,   42,   43,   59,
   45,  264,   47,  121,  284,  259,  124,   59,  264,  256,
  257,   59,  259,  260,  261,   59,    0,  294,  265,  266,
   93,  268,  269,  300,   55,  272,  273,  274,  275,  256,
  257,   -1,  259,  260,  261,   -1,   -1,  284,  265,  266,
   -1,  268,  269,   -1,   -1,  272,  273,  274,  275,   -1,
  256,  257,   -1,  330,  260,  261,   -1,  284,   -1,  265,
  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,  275,
  256,  257,   -1,   -1,  260,  261,   -1,   -1,  284,  265,
  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,  275,
   -1,  256,  257,  258,   -1,  260,  261,   41,  284,   43,
  265,   45,   -1,  268,  269,   -1,   -1,  272,   41,   42,
   43,  229,   45,   -1,   47,   -1,  234,  235,  236,  284,
   -1,  239,  257,  258,   -1,  260,  261,   41,   -1,   43,
  265,   45,   -1,  268,  269,   -1,  257,  272,  259,  260,
  261,   -1,  257,   -1,  265,  260,  261,  268,  269,  284,
  265,  272,   -1,  268,  269,   42,   43,  272,   45,   -1,
   47,   -1,   -1,  284,   41,   42,   43,   -1,   45,  284,
   47,   -1,   59,   41,   42,   43,   41,   45,   43,   47,
   45,   41,   -1,   43,   -1,   45,
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

//#line 308 "gramatica.y"
  
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
    private static final String ERROR_HEADER_FUNC = "Algo fallo en la declaracion de la funcion";

    private static ArrayList<String> mangling = new ArrayList<String>();
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
//#line 791 "Parser.java"
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
//#line 22 "gramatica.y"
{mangling.add(val_peek(1).sval); }
break;
case 4:
//#line 23 "gramatica.y"
{yyerror(ERROR_BEGIN);}
break;
case 5:
//#line 24 "gramatica.y"
{yyerror(ERROR_NOMBRE_PROGRAMA);}
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
{yyerror(ERROR_PUNTOCOMA);}
break;
case 13:
//#line 39 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");
                                    nameMangling(val_peek(1).sval);
                                    if (tipoEmbebido(val_peek(1).sval))
                                        chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                    else
                                        actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                }
break;
case 14:
//#line 46 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 15:
//#line 47 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); nameMangling(val_peek(1).sval);}
break;
case 16:
//#line 48 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 18:
//#line 50 "gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                    System.out.println("FUNCION: "+val_peek(6).sval);
                                                                                    Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);
                                                                                    yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador);
                                                                                    }
break;
case 19:
//#line 55 "gramatica.y"
{yyerror(ERROR_RET);}
break;
case 20:
//#line 56 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 21:
//#line 57 "gramatica.y"
{yyerror(ERROR_HEADER_FUNC);}
break;
case 23:
//#line 59 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 60 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 61 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 26:
//#line 64 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Funcion"); actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                               errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                               this.nuevoNombre = nameMangling(val_peek(0).sval); mangling.add(val_peek(0).sval); yyval.sval = this.nuevoNombre;
                              }
break;
case 27:
//#line 68 "gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 31:
//#line 77 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      nameMangling(val_peek(0).sval); System.out.println("HOLA SOY UN PARAMETRO: " + val_peek(0).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                     }
break;
case 32:
//#line 81 "gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 33:
//#line 82 "gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 44:
//#line 99 "gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 45:
//#line 103 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 46:
//#line 104 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 48:
//#line 106 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 49:
//#line 107 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 50:
//#line 110 "gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);}
break;
case 51:
//#line 112 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 52:
//#line 115 "gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval; 
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 54:
//#line 119 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 56:
//#line 123 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 124 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 125 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 59:
//#line 126 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 60:
//#line 129 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 61:
//#line 130 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 62:
//#line 131 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 63:
//#line 133 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 64:
//#line 134 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 65:
//#line 138 "gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 66:
//#line 140 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 67:
//#line 141 "gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 68:
//#line 144 "gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 69:
//#line 148 "gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 70:
//#line 152 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 153 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 154 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 155 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 156 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 75:
//#line 157 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 76:
//#line 158 "gramatica.y"
{ yyval = val_peek(0);  }
break;
case 77:
//#line 161 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 78:
//#line 165 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 79:
//#line 169 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 170 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 171 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 172 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 173 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 84:
//#line 174 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 85:
//#line 175 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 86:
//#line 178 "gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 87:
//#line 181 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct*/
        }
break;
case 88:
//#line 184 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante UINTEGER*/
         }
break;
case 89:
//#line 187 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante SINGLE*/
         }
break;
case 90:
//#line 190 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante HEXA*/
         }
break;
case 92:
//#line 194 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable negativa*/
        }
break;
case 93:
//#line 197 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct negativa*/
        }
break;
case 94:
//#line 200 "gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval); System.out.println("CONSTANTE SINGLE NEGATIVA: " + "-"+val_peek(0).sval);}
break;
case 95:
//#line 201 "gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 96:
//#line 204 "gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);}
break;
case 97:
//#line 205 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 98:
//#line 206 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 99:
//#line 209 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null)); /* No es necesario nodo de control "CUERPO" porque el camino es solo del THEN. MIRAR FILMINAS 14 DEL PAQUETE 08.3 (basado en eso para crear la estructura del arbol adecuado reutilizando clases por patron composite)*/
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 100:
//#line 213 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 101:
//#line 217 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 102:
//#line 218 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 103:
//#line 219 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 220 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 221 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 222 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 107:
//#line 223 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 108:
//#line 224 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 109:
//#line 225 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 110:
//#line 226 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 111:
//#line 227 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 112:
//#line 228 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 113:
//#line 231 "gramatica.y"
{yyval = val_peek(1);}
break;
case 114:
//#line 232 "gramatica.y"
{yyval = val_peek(0);}
break;
case 115:
//#line 235 "gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 116:
//#line 236 "gramatica.y"
{yyval = val_peek(0);}
break;
case 117:
//#line 239 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 118:
//#line 240 "gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 119:
//#line 241 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 120:
//#line 242 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 127:
//#line 253 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 128:
//#line 254 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 129:
//#line 255 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 130:
//#line 256 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 131:
//#line 257 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 132:
//#line 258 "gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 133:
//#line 261 "gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 134:
//#line 265 "gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 135:
//#line 266 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 136:
//#line 267 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 137:
//#line 268 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 138:
//#line 269 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 139:
//#line 270 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 140:
//#line 273 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 141:
//#line 274 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 142:
//#line 275 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 143:
//#line 276 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 144:
//#line 279 "gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);}
break;
case 145:
//#line 280 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 146:
//#line 281 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 147:
//#line 282 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 148:
//#line 285 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 149:
//#line 286 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 150:
//#line 287 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 151:
//#line 288 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 152:
//#line 293 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 153:
//#line 294 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 154:
//#line 297 "gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 155:
//#line 298 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 156:
//#line 299 "gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 157:
//#line 302 "gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(2).obj,null);}
break;
case 158:
//#line 303 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 159:
//#line 304 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1538 "Parser.java"
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
