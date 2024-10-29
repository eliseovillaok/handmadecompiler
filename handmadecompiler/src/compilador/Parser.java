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
    4,    4,    4,    4,    4,    8,    8,    6,    6,    6,
    9,    9,    9,    5,    5,    5,    5,    5,    5,    5,
    5,   11,   11,   19,   19,   19,   19,   19,   19,   20,
   20,    7,    7,    7,    7,    7,    7,    7,    7,   22,
   22,   22,   22,   22,   12,   12,   12,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   13,   13,   13,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   26,   26,   27,   27,   25,   25,   25,   25,
   28,   28,   28,   28,   28,   28,   15,   15,   15,   15,
   15,   15,   16,   16,   16,   16,   16,   16,   16,   10,
   10,   10,   10,   29,   29,   29,   29,   30,   30,   30,
   30,   31,   31,   17,   17,   17,   18,   18,   18,
};
final static short yylen[] = {                            2,
    3,    3,    2,    2,    2,    1,    2,    1,    1,    3,
    2,    2,    3,    3,    2,    2,    2,    7,    7,    7,
    7,    2,    2,    3,    2,    3,    3,    1,    1,    1,
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
    0,  156,  155,  154,    0,   14,   13,   27,   26,    0,
   24,   10,    0,    0,    0,    0,   54,    0,    0,    0,
   33,   32,   31,    0,   46,   45,   44,    0,    0,    0,
    0,    0,    0,   83,   81,   84,   82,    0,    0,   87,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   79,   77,   80,   78,    0,    0,    0,    0,    0,    0,
  131,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  113,  115,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   51,   50,    0,   59,
    0,    0,    0,    0,   97,   98,   96,    0,    0,   93,
    0,    0,    0,  159,  158,  157,  132,  130,  128,  129,
  127,    0,    0,    0,  153,  152,    0,    0,    0,    0,
    0,   67,   66,   65,    0,    0,  138,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   55,    0,
    0,    0,    0,    0,    0,    0,    0,  105,    0,    0,
    0,    0,    0,    0,    0,  151,    0,    0,  150,    0,
  137,    0,  136,  134,   48,   49,   47,   57,    0,    0,
    0,    0,   21,    0,  104,    0,    0,    0,    0,  111,
    0,    0,  103,    0,  147,    0,  146,  149,  145,  139,
  135,  133,   53,   20,   19,   18,    0,    0,  109,    0,
  101,   99,  108,    0,  148,  144,  107,    0,    0,  112,
  106,  110,  102,  100,
};
final static short yydgoto[] = {                          3,
    4,   21,   22,   23,   72,   25,   73,   27,   98,   28,
   29,   30,   54,   32,   33,   34,   35,   36,   37,   38,
   55,  165,   56,   57,   58,   74,  149,  114,   65,   66,
  137,
};
final static short yysindex[] = {                        26,
 -250, -115,    0,  521,    0,    0,    0,  -12,  -39,  -22,
   20,   22,  270,   52,  572, -224,    0,    0,  -23,  117,
  434,    0,    0,    0, -185,  182,   82,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -221,    0,
    0,   84,    0,  184, -216,   -5,  115,    0,    0,    0,
  138,  194, -173,    0,   25,    1,    0,   -2,  218,   35,
  141,    0,  173,  126,  151,  170,  223,  -74,  -38,  609,
  117,    0,  -32,  -24,  153,   83,    0,    0,  -57,  -12,
    0,    0,  -25,  208,  288,   10,    0,    0,  227,  291,
    0, -211, -209,    0,    0,   85,  236,  303,  837,  390,
   60,  444,    0,    0,    0,    0,  258,  261,  272,  275,
    0,    0,    0,  301,  127,   17,    0,    0,    0,  328,
  301,  305,  318,  330,  348,  361,  402,  132,  856,  523,
  863,  349,  338,  570,  173,  356,   72,   27,   89,  144,
    0,    0,    0,    0,  870,  669,  383,    0,  603,  -19,
  194,    0,    0,    0,  -20,    0,    0,    0,    0,  197,
    0,    0,  876,  377,   34,  199,    0,  384,  -27,  418,
    0,    0,    0,  219,    0,    0,    0,  423,  120,    3,
    1,    3,    1,    0,    0,    0,    0,  630,  146,    0,
  402,  224,  240,  146,    3,    1,    3,    1,  630,  146,
    0,    0,    0,    0,  246,  402,  447,  161,  454,  192,
    0,  225,  112,  123,  173,  173,  282,  259,  297,  302,
  273,  474,  234,  194,    0,    0,  194,  145,  505,  364,
  519,  283,  -40,  301,  301,  374,    0,    0,  301,    0,
  327,  322,  332,  521,    0,    0,    0,  256,  589,    0,
  402,  529,  260,    0,    0,    0,    0,    0,    0,    0,
    0,  335, -196,  339,    0,    0, -196,  340,  341, -196,
  350,    0,    0,    0,  575,  323,    0,  560,  563,  843,
  410,  366,  583,  146,  146,  630,  146,  146,    0,  521,
  552,  460,  402,  585,  309,   59,  392,    0,  402,  588,
  404,  100,   95, -196,   96,    0,  406,  128,    0,  590,
    0,  266,    0,    0,    0,    0,    0,    0,  419,  480,
  -11,  500,    0,  415,    0,  427,  628,  402,  314,    0,
  639,  437,    0,  445,    0,  129,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  646,  451,    0,  -37,
    0,    0,    0,  652,    0,    0,    0,  663,  336,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  241,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   53,    0,    0,    0,
    0,    0,    0,    0,    0,   65,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  727,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   76,
   87,  109,  116,    0,    0,    0,    0,    0,  -34,    0,
    0,    0,    0,  -26,  121,  149,  154,  162,    8,   18,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  307,  399,  408,  429,  432,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0, -200,  -18,    0,    9,  508,    6,    0,  637,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,    0,
  568,    0,  546,  571,  376,  385,    0,  676,    0,    0,
  -54,
};
final static int YYTABLESIZE=923;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
   44,   44,   82,  232,   45,   45,  119,    5,  139,   26,
   31,   92,   24,  242,  118,  151,   31,   52,   45,   43,
  227,  360,   53,  232,  119,   53,   26,   39,   39,   24,
   86,   75,  118,  157,   96,   78,  109,  107,  128,  108,
  103,  110,  125,  292,  109,  167,  169,  126,  120,  110,
   76,   17,   18,   92,  112,  111,  113,  192,  117,   59,
  302,   60,   62,   17,   18,   31,  120,  122,  162,  123,
  215,   83,  168,   95,   62,  133,  117,  239,  148,   53,
  214,   84,  117,  118,  112,  111,  113,   85,  219,  320,
  322,   67,  238,   86,   86,   86,   86,   86,   85,   86,
  178,  109,  107,  119,  108,   76,  110,   76,   76,   76,
  120,   86,   86,   86,   86,  216,   74,  330,   74,   74,
   74,   93,   31,   76,   76,   76,   76,   72,   53,   72,
   72,   72,  216,  217,   74,   74,   74,   74,   92,   92,
    6,  154,    7,   45,   31,   72,   72,   72,   72,   75,
  220,   75,   75,   75,   44,  215,   73,  226,   73,   73,
   73,   70,   79,   70,   70,   70,  216,   75,   75,   75,
   75,   92,   92,  262,   73,   73,   73,   73,  247,   70,
   70,   70,   70,  115,  264,  278,   31,  140,  122,   68,
  123,   68,   68,   68,   71,  147,   71,   71,   71,  155,
  135,   31,   69,  277,   69,   69,   69,   68,   68,   68,
   68,  152,   71,   71,   71,   71,   40,   41,   41,  256,
   69,   69,   69,   69,   88,   92,  359,  119,   53,  171,
  156,   41,   77,   46,   47,  118,   46,   47,   53,   31,
   91,   42,   42,  231,   31,  150,   31,  345,   89,   26,
  259,   90,   24,   48,   49,   50,   48,   49,   50,  127,
  230,   51,   53,  231,   51,  161,   88,   53,  303,  120,
   94,   53,  305,   82,  104,  308,  105,  106,  191,  117,
  121,    1,    2,  261,  218,   31,   31,   31,   31,  237,
  131,   47,  274,   90,   31,   26,   26,   26,   24,   24,
   24,   82,   53,   82,  104,   53,  105,  106,   86,  336,
   48,   49,   50,  132,   86,   31,   53,   31,   51,   53,
   76,  328,  329,   31,  342,   26,   76,   26,   24,   63,
   24,   74,   86,  160,   86,   86,  166,   74,  153,   99,
   47,  171,   72,  174,   76,   53,   76,   76,   72,   53,
   63,   88,   88,  335,  337,   74,   41,   74,   74,   48,
   49,   50,   53,  312,   75,   63,   72,   51,   72,   72,
   75,   73,  352,  193,   53,  246,   70,   73,   90,   90,
  263,  311,   70,  190,   88,   88,  339,  356,   75,  210,
   75,   75,   53,  206,  364,   73,  211,   73,   73,  215,
   70,  221,   70,   70,   68,   53,  141,  142,   53,   71,
   68,   90,   90,   17,   18,   71,  255,   69,   53,  122,
  236,  123,  224,   69,   62,  143,  144,  116,   68,  241,
   68,   68,  122,   71,  123,   71,   71,   87,   88,  101,
   47,   69,   62,   69,   69,   17,   18,  258,  177,   46,
   47,   64,  122,  233,  123,  240,   62,   62,  243,   48,
   49,   50,   89,  158,  159,   90,   64,   51,  317,   48,
   49,   50,   60,  129,   47,   61,  244,   51,  145,   47,
  260,  245,  163,   47,  179,  249,  122,   60,  123,  273,
   61,  172,  173,   48,   49,   50,  250,   30,   48,   49,
   50,   51,   48,   49,   50,  254,   51,   30,  251,  252,
   51,  205,  257,  180,   47,  268,  182,   47,  293,  294,
   64,  341,  299,  300,   30,  228,  229,  184,   47,  271,
  186,   47,  272,   48,   49,   50,   48,   49,   50,  267,
   61,   51,   17,   18,   51,  279,   97,   48,   49,   50,
   48,   49,   50,   62,  269,   51,  188,   47,   51,  270,
  195,   47,   63,  208,  282,  122,  283,  123,  136,  351,
  138,  326,  327,  197,   47,  248,   48,   49,   50,  290,
   48,   49,   50,  289,   51,  199,   47,  298,   51,  291,
  253,  363,  301,   48,   49,   50,  304,  307,  306,  275,
   97,   51,  276,  201,   47,   48,   49,   50,  309,  100,
  212,  102,  122,   51,  123,  310,  203,   47,  313,  280,
   47,  314,  318,   48,   49,   50,  130,  134,  319,  286,
   47,   51,  235,  296,  146,  297,   48,   49,   50,   48,
   49,   50,  213,  325,   51,  176,  333,   51,  340,   48,
   49,   50,  181,  183,   62,  331,  164,   51,   69,   70,
  334,   10,   11,   64,  338,  316,   12,  196,  198,   14,
   15,  109,  107,   16,  108,  343,  110,  324,  347,  185,
  187,  189,  348,  332,   60,   71,  349,   61,  194,   80,
    9,  200,   81,   10,   11,  202,  204,  353,   12,   13,
  354,   14,   15,  355,  357,   16,   17,   18,   19,  223,
  361,  122,  350,  123,  358,    8,    9,   20,  323,   10,
   11,  362,  265,  266,   12,   13,    2,   14,   15,  170,
  124,   16,   17,   18,   19,    8,    9,    0,  344,   10,
   11,    0,    0,   20,   12,   13,    0,   14,   15,    0,
    0,   16,   17,   18,   19,    8,    9,    0,  346,   10,
   11,    0,    0,   20,   12,   13,    0,   14,   15,    0,
    0,   16,   17,   18,   19,    0,    8,    9,    0,    0,
   10,   11,    0,   20,    0,   12,   13,    0,   14,   15,
    0,    0,   16,   17,   18,   19,    0,  281,    0,    0,
    0,  284,  285,  287,   20,    0,  288,  321,    9,    0,
    0,   10,   11,    0,    0,    0,   12,   13,    0,   14,
   15,    0,    0,   16,   17,   18,   19,   68,   69,   70,
    0,   10,   11,    0,    0,   20,   12,    0,    0,   14,
   15,    0,    0,   16,  295,   69,   70,    0,   10,   11,
    0,    0,    0,   12,    0,   71,   14,   15,    0,   69,
   16,  225,   10,   11,    0,   69,    0,   12,   10,   11,
   14,   15,   71,   12,   16,    0,   14,   15,  109,  107,
   16,  108,    0,  110,  109,  107,   71,  108,    0,  110,
    0,    0,   71,    0,    0,  175,  207,  109,  107,    0,
  108,  315,  110,  209,  109,  107,    0,  108,    0,  110,
  222,  109,  107,    0,  108,    0,  110,  109,  107,  234,
  108,    0,  110,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
   40,   40,   21,   44,   44,   44,   41,  258,   63,    4,
   15,   44,    4,   41,   41,   40,   21,   40,   44,   59,
   40,   59,   45,   44,   59,   45,   21,   40,   40,   21,
   25,  256,   59,   59,  256,   59,   42,   43,   41,   45,
  257,   47,   42,  244,   42,  257,  256,   47,   41,   47,
  275,  273,  274,   44,   60,   61,   62,   41,   41,   40,
  257,   40,  284,  273,  274,   70,   59,   43,   59,   45,
   44,  257,  284,   59,  284,   41,   59,   44,   70,   45,
  135,  267,  256,  257,   60,   61,   62,  284,   62,  290,
  291,   40,   59,   41,   42,   43,   44,   45,  284,   47,
   41,   42,   43,  277,   45,   41,   47,   43,   44,   45,
  284,   59,   60,   61,   62,   44,   41,   59,   43,   44,
   45,   40,  127,   59,   60,   61,   62,   41,   45,   43,
   44,   45,   44,   62,   59,   60,   61,   62,   44,   44,
  256,   59,  258,   44,  149,   59,   60,   61,   62,   41,
   62,   43,   44,   45,   40,   44,   41,  149,   43,   44,
   45,   41,   46,   43,   44,   45,   44,   59,   60,   61,
   62,   44,   44,   62,   59,   60,   61,   62,   59,   59,
   60,   61,   62,   46,   62,   41,  191,   62,   43,   41,
   45,   43,   44,   45,   41,  270,   43,   44,   45,  257,
   60,  206,   41,   59,   43,   44,   45,   59,   60,   61,
   62,   59,   59,   60,   61,   62,  256,  257,  257,   59,
   59,   60,   61,   62,  257,   44,  264,  262,   45,  257,
  256,  257,  256,  256,  257,  262,  256,  257,   45,  244,
   59,  281,  281,  284,  249,  270,  251,  259,  281,  244,
   59,  284,  244,  276,  277,  278,  276,  277,  278,  262,
  281,  284,   45,  284,  284,  256,  257,   45,  263,  262,
  256,   45,  267,  292,  280,  270,  282,  283,  262,  262,
  256,  256,  257,   59,  258,  290,  291,  292,  293,  256,
  256,  257,   59,  284,  299,  290,  291,  292,  290,  291,
  292,  320,   45,  322,  280,   45,  282,  283,  256,  304,
  276,  277,  278,  279,  262,  320,   45,  322,  284,   45,
  256,  263,  264,  328,   59,  320,  262,  322,  320,   60,
  322,  256,  280,   46,  282,  283,   46,  262,  256,  256,
  257,  257,  256,   41,  280,   45,  282,  283,  262,   45,
   44,  257,  257,  259,  259,  280,  257,  282,  283,  276,
  277,  278,   45,   41,  256,   59,  280,  284,  282,  283,
  262,  256,   59,   46,   45,  256,  256,  262,  284,  284,
  258,   59,  262,  257,  257,  257,  259,  259,  280,   41,
  282,  283,   45,  262,   59,  280,   59,  282,  283,   44,
  280,  258,  282,  283,  256,   45,  256,  257,   45,  256,
  262,  284,  284,  273,  274,  262,  256,  256,   45,   43,
   44,   45,   40,  262,  284,  256,  257,   52,  280,   46,
  282,  283,   43,  280,   45,  282,  283,  256,  257,  256,
  257,  280,   44,  282,  283,  273,  274,  256,   59,  256,
  257,   44,   43,  257,   45,  257,  284,   59,   41,  276,
  277,  278,  281,  256,  257,  284,   59,  284,   59,  276,
  277,  278,   44,  256,  257,   44,  258,  284,  256,  257,
  256,   59,  256,  257,   41,  262,   43,   59,   45,  256,
   59,  256,  257,  276,  277,  278,  257,  257,  276,  277,
  278,  284,  276,  277,  278,   59,  284,  267,  263,  264,
  284,  127,   59,  256,  257,  257,  256,  257,  263,  264,
   13,  256,  263,  264,  284,  150,  151,  256,  257,  257,
  256,  257,   59,  276,  277,  278,  276,  277,  278,  258,
  271,  284,  273,  274,  284,   41,   39,  276,  277,  278,
  276,  277,  278,  284,  258,  284,  256,  257,  284,  258,
  256,  257,  256,   41,   46,   43,  284,   45,   61,  256,
   63,  263,  264,  256,  257,  191,  276,  277,  278,  258,
  276,  277,  278,  257,  284,  256,  257,   59,  284,  258,
  206,  256,  258,  276,  277,  278,  258,  257,  259,  224,
   93,  284,  227,  256,  257,  276,  277,  278,  259,   42,
   41,   44,   43,  284,   45,   41,  256,  257,   59,  256,
  257,   59,  257,  276,  277,  278,   59,   60,   46,  256,
  257,  284,  256,  249,   67,  251,  276,  277,  278,  276,
  277,  278,  135,   59,  284,  256,   59,  284,   59,  276,
  277,  278,  107,  108,  256,  264,   89,  284,  257,  258,
  257,  260,  261,  256,  259,  256,  265,  122,  123,  268,
  269,   42,   43,  272,   45,  257,   47,  293,  264,  109,
  110,  114,  256,  299,  256,  284,   59,  256,  121,  256,
  257,  124,  259,  260,  261,  125,  126,   59,  265,  266,
  264,  268,  269,  259,   59,  272,  273,  274,  275,   41,
   59,   43,  328,   45,  264,  256,  257,  284,  259,  260,
  261,   59,  215,  216,  265,  266,    0,  268,  269,   93,
   55,  272,  273,  274,  275,  256,  257,   -1,  259,  260,
  261,   -1,   -1,  284,  265,  266,   -1,  268,  269,   -1,
   -1,  272,  273,  274,  275,  256,  257,   -1,  259,  260,
  261,   -1,   -1,  284,  265,  266,   -1,  268,  269,   -1,
   -1,  272,  273,  274,  275,   -1,  256,  257,   -1,   -1,
  260,  261,   -1,  284,   -1,  265,  266,   -1,  268,  269,
   -1,   -1,  272,  273,  274,  275,   -1,  230,   -1,   -1,
   -1,  234,  235,  236,  284,   -1,  239,  256,  257,   -1,
   -1,  260,  261,   -1,   -1,   -1,  265,  266,   -1,  268,
  269,   -1,   -1,  272,  273,  274,  275,  256,  257,  258,
   -1,  260,  261,   -1,   -1,  284,  265,   -1,   -1,  268,
  269,   -1,   -1,  272,  256,  257,  258,   -1,  260,  261,
   -1,   -1,   -1,  265,   -1,  284,  268,  269,   -1,  257,
  272,  259,  260,  261,   -1,  257,   -1,  265,  260,  261,
  268,  269,  284,  265,  272,   -1,  268,  269,   42,   43,
  272,   45,   -1,   47,   42,   43,  284,   45,   -1,   47,
   -1,   -1,  284,   -1,   -1,   59,   41,   42,   43,   -1,
   45,   59,   47,   41,   42,   43,   -1,   45,   -1,   47,
   41,   42,   43,   -1,   45,   -1,   47,   42,   43,   44,
   45,   -1,   47,
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

//#line 318 "gramatica.y"
  
    private static final String ERROR_BEGIN = "se espera un delimitador (BEGIN)";
    private static final String ERROR_CANTIDAD_PARAMETRO = "cantidad de parametros incorrectos";
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

    void actualizarTipoParamEsperado(String funcion, String tipoParametro){
        ts.actualizarTipoParamEsperado(funcion, tipoParametro);
    }

    void borrarSimbolosDuplicados() {
        ts.borrarSimbolosDuplicados();
    }

    Boolean paramRealIgualFormal(String funcion, String tipoParamReal){
        for (String mangle : mangling) {
            funcion = funcion + ":" + mangle;
        }
        String tipoParamFormal = ts.buscar(funcion).getTipoParametroEsperado();

        System.out.println("TIPO PARAM REAL: "+tipoParamReal);
        System.out.println("TIPO PARAM FORMAL: "+tipoParamFormal);

        if(tipoParamFormal.equals(tipoParamReal)){
            return true;
        }
        return false;
    }
//#line 820 "Parser.java"
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
              Nodo programa = new NodoCompuesto("programa",(Nodo)val_peek(2).obj, (Nodo)val_peek(1).obj);
              System.out.println(programa.toString());  /* Imprime el árbol sintáctico completo*/
              yyval.obj = programa;  /* Almacena el nodo en ParserVal*/
              actualizarTipo(val_peek(2).sval, "NOMBRE_PROGRAMA"); /* Actualiza el tipo de la variable que se genera con el nombre del programa, puede servir a futuro..*/
              actualizarUso(val_peek(2).sval, "NOMBRE_PROGRAMA");
              borrarSimbolosDuplicados();  /*ojo con esto :D - No arregla lo que busca en caso de tipos embebidos*/
          }
break;
case 2:
//#line 20 "gramatica.y"
{ yyerror(ERROR_END); }
break;
case 3:
//#line 23 "gramatica.y"
{mangling.add(val_peek(1).sval); yyval = val_peek(1);}
break;
case 4:
//#line 24 "gramatica.y"
{yyerror(ERROR_BEGIN);}
break;
case 5:
//#line 25 "gramatica.y"
{yyerror(ERROR_NOMBRE_PROGRAMA);}
break;
case 6:
//#line 29 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 30 "gramatica.y"
{ yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj); }
break;
case 8:
//#line 33 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 9:
//#line 34 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 10:
//#line 37 "gramatica.y"
{actualizarTipoDelGrupo(val_peek(2).sval, val_peek(1).sval);}
break;
case 12:
//#line 39 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 13:
//#line 40 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");
                                    if (tipoEmbebido(val_peek(1).sval))
                                        chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                    else
                                        actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                    nameMangling(val_peek(1).sval);
                                }
break;
case 14:
//#line 47 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 15:
//#line 48 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); nameMangling(val_peek(1).sval);}
break;
case 16:
//#line 49 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 17:
//#line 50 "gramatica.y"
{yyval = val_peek(1); yyval.obj = null;}
break;
case 18:
//#line 51 "gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                    actualizarTipoParamEsperado(val_peek(6).sval, val_peek(4).sval);
                                                                                    System.out.println("FUNCION: "+val_peek(6).sval);
                                                                                    Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);
                                                                                    yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador);
                                                                                    mangling.remove(mangling.size() - 1);
                                                                                    }
break;
case 19:
//#line 58 "gramatica.y"
{yyerror(ERROR_RET);}
break;
case 20:
//#line 59 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 21:
//#line 60 "gramatica.y"
{yyerror(ERROR_HEADER_FUNC);}
break;
case 23:
//#line 62 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 63 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 64 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 26:
//#line 67 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Funcion"); actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                               errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                               this.nuevoNombre = nameMangling(val_peek(0).sval); mangling.add(val_peek(0).sval); yyval.sval = this.nuevoNombre;
                              }
break;
case 27:
//#line 72 "gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 31:
//#line 81 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      nameMangling(val_peek(0).sval); System.out.println("HOLA SOY UN PARAMETRO: " + val_peek(0).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      yyval.sval = val_peek(1).sval;
                     }
break;
case 32:
//#line 86 "gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 33:
//#line 87 "gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 44:
//#line 104 "gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 45:
//#line 108 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 46:
//#line 109 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 48:
//#line 111 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 49:
//#line 112 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 50:
//#line 115 "gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);}
break;
case 51:
//#line 117 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 52:
//#line 120 "gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = nameMangling(val_peek(2).sval) + "," + nameMangling(val_peek(0).sval); 
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));
                                                         }
break;
case 54:
//#line 125 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + nameMangling(val_peek(0).sval);
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));
                                            }
break;
case 56:
//#line 130 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 131 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 132 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 59:
//#line 133 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 60:
//#line 136 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 61:
//#line 137 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 62:
//#line 138 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 63:
//#line 140 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 64:
//#line 141 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 65:
//#line 145 "gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 66:
//#line 147 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 67:
//#line 148 "gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 68:
//#line 151 "gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 69:
//#line 155 "gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 70:
//#line 159 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 160 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 161 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 162 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 163 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 75:
//#line 164 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 76:
//#line 165 "gramatica.y"
{ yyval = val_peek(0);  }
break;
case 77:
//#line 168 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 78:
//#line 172 "gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 79:
//#line 176 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 177 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 178 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 179 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 180 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 84:
//#line 181 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 85:
//#line 182 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 86:
//#line 185 "gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 87:
//#line 188 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct*/
        }
break;
case 88:
//#line 191 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante UINTEGER*/
         }
break;
case 89:
//#line 194 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante SINGLE*/
         }
break;
case 90:
//#line 197 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para constante HEXA*/
         }
break;
case 92:
//#line 201 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable negativa*/
        }
break;
case 93:
//#line 204 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct negativa*/
        }
break;
case 94:
//#line 207 "gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval); System.out.println("CONSTANTE SINGLE NEGATIVA: " + "-"+val_peek(0).sval);}
break;
case 95:
//#line 208 "gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 96:
//#line 211 "gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);
                                                System.out.println("NODO EXPRESION: " + val_peek(2).obj.toString());
                                                if(!paramRealIgualFormal(val_peek(4).sval, ((Nodo)val_peek(2).obj).devolverTipo(mangling))) {yyerror(ERROR_TIPO_PARAMETRO);}
                                               }
break;
case 97:
//#line 215 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 98:
//#line 216 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 99:
//#line 219 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null)); /* No es necesario nodo de control "CUERPO" porque el camino es solo del THEN. MIRAR FILMINAS 14 DEL PAQUETE 08.3 (basado en eso para crear la estructura del arbol adecuado reutilizando clases por patron composite)*/
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 100:
//#line 223 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 101:
//#line 227 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 102:
//#line 228 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 103:
//#line 229 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 230 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 231 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 232 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 107:
//#line 233 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 108:
//#line 234 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 109:
//#line 235 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 110:
//#line 236 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 111:
//#line 237 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 112:
//#line 238 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 113:
//#line 241 "gramatica.y"
{yyval = val_peek(1);}
break;
case 114:
//#line 242 "gramatica.y"
{yyval = val_peek(0);}
break;
case 115:
//#line 245 "gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 116:
//#line 246 "gramatica.y"
{yyval = val_peek(0);}
break;
case 117:
//#line 249 "gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 118:
//#line 250 "gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 119:
//#line 251 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 120:
//#line 252 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 127:
//#line 263 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 128:
//#line 264 "gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 129:
//#line 265 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 130:
//#line 266 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 131:
//#line 267 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 132:
//#line 268 "gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 133:
//#line 271 "gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 134:
//#line 275 "gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 135:
//#line 276 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 136:
//#line 277 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 137:
//#line 278 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 138:
//#line 279 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 139:
//#line 280 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 140:
//#line 283 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 141:
//#line 284 "gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 142:
//#line 285 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 143:
//#line 286 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 144:
//#line 289 "gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);}
break;
case 145:
//#line 290 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 146:
//#line 291 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 147:
//#line 292 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 148:
//#line 295 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 149:
//#line 296 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 150:
//#line 297 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 151:
//#line 298 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 152:
//#line 303 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 153:
//#line 304 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 154:
//#line 307 "gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 155:
//#line 308 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 156:
//#line 309 "gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 157:
//#line 312 "gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(2).obj,null);}
break;
case 158:
//#line 313 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 159:
//#line 314 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1580 "Parser.java"
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
