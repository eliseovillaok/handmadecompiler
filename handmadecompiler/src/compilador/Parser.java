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
    9,    9,    5,    5,    5,    5,    5,    5,    5,    5,
   11,   11,   19,   19,   19,   19,   19,   19,   20,   20,
    7,    7,    7,    7,    7,    7,    7,    7,   22,   22,
   22,   22,   22,   12,   12,   12,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   13,   13,   13,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   26,   26,   27,   27,   25,   25,   25,   25,   28,
   28,   28,   28,   28,   28,   15,   15,   15,   15,   15,
   15,   16,   16,   16,   16,   16,   16,   16,   10,   10,
   10,   10,   29,   29,   29,   29,   30,   30,   30,   30,
   31,   31,   17,   17,   17,   18,   18,   18,
};
final static short yylen[] = {                            2,
    3,    3,    2,    2,    2,    1,    2,    1,    1,    3,
    2,    2,    3,    3,    2,    2,    2,    7,    7,    7,
    7,    2,    2,    3,    2,    3,    3,    1,    1,    2,
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
    0,    0,    0,    0,    5,    4,    3,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   29,   28,    0,    0,
    0,    6,    8,    9,    0,    0,    0,    0,   33,   34,
   35,   36,   37,   38,   39,   40,   41,   42,    0,   16,
   55,    0,   15,    0,    0,    0,    0,    0,   87,   88,
   89,    0,    0,    0,   90,    0,    0,   84,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  113,    0,    0,    0,    0,   12,   11,    0,    0,    1,
    7,    0,    0,    0,    0,   25,    0,    0,   17,    0,
    0,   23,   22,    0,    0,    0,    0,    0,    0,    0,
    0,   51,  124,  125,  121,    0,    0,    0,    0,  120,
  122,  123,    0,    0,    0,   94,   91,   93,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  141,
  139,  142,  140,    0,    0,    0,  115,    0,    0,    0,
  155,  154,  153,    0,   14,   13,   27,   26,    0,   24,
   10,    0,    0,    0,    0,   53,    0,    0,    0,   32,
   31,   30,    0,   45,   44,   43,    0,    0,    0,    0,
    0,    0,    0,   82,   80,   83,   81,    0,    0,   86,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   78,   76,   79,   77,    0,    0,    0,    0,    0,    0,
  130,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  112,  114,    0,    0,    0,    0,
    0,    0,   58,    0,    0,    0,   50,   49,    0,    0,
    0,    0,    0,   96,   97,   95,    0,    0,    0,   92,
    0,    0,    0,  158,  157,  156,  131,  129,  127,  128,
  126,    0,    0,    0,  152,  151,    0,    0,    0,    0,
    0,   66,   65,   64,    0,    0,  137,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   54,    0,    0,
    0,   56,    0,    0,    0,    0,    0,  104,    0,    0,
    0,    0,    0,    0,    0,  150,    0,    0,  149,    0,
  136,    0,  135,  133,   47,   48,   46,    0,    0,    0,
    0,   21,    0,  103,    0,    0,    0,    0,  110,    0,
    0,  102,    0,  146,    0,  145,  148,  144,  138,  134,
  132,   52,   20,   19,   18,    0,    0,  108,    0,  100,
   98,  107,    0,  147,  143,  106,    0,    0,  111,  105,
  109,  101,   99,
};
final static short yydgoto[] = {                          3,
    4,   21,   22,   23,   71,   25,   72,   27,   96,   28,
   29,   30,   55,   32,   33,   34,   35,   36,   37,   38,
   56,  165,   57,   58,   59,   73,  148,  113,   65,   66,
  136,
};
final static short yysindex[] = {                      -238,
 -231, -160,    0,  534,    0,    0,    0,   -1,  159,  -36,
    2,   66,  251,   85,  356, -161,    0,    0,  -22,   19,
  435,    0,    0,    0, -234,  139,   88,    5,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -222,    0,
    0,   57,    0,   68, -124, -117,   -7,  126,    0,    0,
    0,   92,  148,  227,    0,  163,  375,    0,  -35,  171,
  -21,   77, -119,  107,  -28,  220,  181,  -85,   80,  594,
    0,  -43,  -33,  138,   24,    0,    0,  -57,   -1,    0,
    0,  311,  243,  161,  320,    0,  173,  185,    0, -181,
 -162,    0,    0,  -48,  246,  176,  590,  407,  741,  437,
   99,    0,    0,    0,    0,  214,  217,  229,  232,    0,
    0,    0,  258,  110,   17,    0,    0,    0,  275,  258,
  262,  271,  274,  287,  304,  574,   81,  832,  593,  841,
  352,  371,  645, -119,  391,   -3,  105,   12,  186,    0,
    0,    0,    0,  848,  859,  428,    0,  588,  -24,  148,
    0,    0,    0,  -31,    0,    0,    0,    0,  199,    0,
    0,  215,  854,  133,  328,    0,  441,  -26,  455,    0,
    0,    0,  254,    0,    0,    0,  458,   25,  475,  376,
  375,  376,  375,    0,    0,    0,    0,  599,   76,    0,
  574,  264,  272,   76,  376,  375,  376,  375,  599,   76,
    0,    0,    0,    0,  323,  574,  482,  109,  486,  142,
    0,  145,   28,  239, -119, -119,  296,  302,  299,  308,
  305,  513,  225,  148,    0,    0,  148,   64,  537,  313,
  291,  539,    0,  258,  258,  317,    0,    0,  258,  339,
  334,  347,  534,    0,    0,    0,  354,  345,  397,    0,
  574,  556,  384,    0,    0,    0,    0,    0,    0,    0,
    0,  361, -180,  369,    0,    0, -180,  380,  363, -180,
  393,    0,    0,    0,  589,  351,    0,  597,  602,  822,
  408,  624,   76,   76,  599,   76,   76,    0,  534,  554,
  457,    0,  574,  612,  396,  335,  409,    0,  574,  618,
  421,   36,   37, -180,  118,    0,  430,  131,    0,  620,
    0,  267,    0,    0,    0,    0,    0,  423,  477,  -37,
  503,    0,  433,    0,  442,  640,  574,  281,    0,  643,
  447,    0,  446,    0,  140,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  647,  448,    0,  -49,    0,
    0,    0,  656,    0,    0,    0,  661,  300,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   26,    0,    0,
    0,    0,    0,    0,    0,    0,  -13,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  724,    0,
    0,    0,    0,    0,    0,    0,  127,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   48,
   56,   86,   91,    0,    0,    0,    0,    0,  -16,    0,
    0,    0,    0,    3,   98,  120,  129,  151,    4,   16,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  362,  370,  395,  416,  420,    0,    0,    0,
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
    0, -229,  -19,    0,    8,  540,    1,    0,  636,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,    0,
  562,    0,  659,  559,  383,  394,    0,  672,    0,    0,
  -55,
};
final static int YYTABLESIZE=904;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
   90,   81,   39,   53,   26,  127,  150,  138,   54,  359,
   31,   24,  231,  291,  241,  227,   31,    1,    2,  132,
   54,   26,   82,   54,  118,   85,    5,   75,   24,   75,
   75,   75,   83,   94,  108,  106,   77,  107,   39,  109,
  216,   60,  118,  117,  119,   75,   75,   75,   75,   84,
   17,   18,  111,  110,  112,  216,  116,  192,  217,  319,
  321,  117,  119,   93,   78,   31,   85,   85,   85,   85,
   85,  215,   85,  220,  116,  166,  302,  147,  214,   46,
   90,   45,  153,  246,   85,   85,   85,   85,   73,  262,
   73,   73,   73,  168,   74,    6,   71,    7,   71,   71,
   71,   54,  167,   84,  278,   61,   73,   73,   73,   73,
   17,   18,   54,   75,   71,   71,   71,   71,  121,   44,
  122,   31,  277,   46,   67,   45,   74,   91,   74,   74,
   74,   72,  101,   72,   72,   72,  134,  114,   69,  102,
   69,   69,   69,   31,   74,   74,   74,   74,  215,   72,
   72,   72,   72,   17,   18,  226,   69,   69,   69,   69,
   67,   90,   67,   67,   67,   44,  219,  256,  139,   70,
   57,   70,   70,   70,   90,  121,  236,  122,   67,   67,
   67,   67,   90,   90,  146,   57,   31,   70,   70,   70,
   70,   68,   54,   68,   68,   68,  151,   89,   44,  154,
  259,   31,   46,  261,   45,  121,  159,  122,  170,   68,
   68,   68,   68,   87,  358,   54,  173,   43,  162,   47,
   48,  344,  111,  110,  112,   54,  126,  140,  141,   54,
  170,   47,   48,   76,  130,   48,  149,   88,   31,   49,
   50,   51,   75,   26,   31,  118,   31,   52,   75,  230,
   24,   49,   50,   51,   49,   50,   51,  131,   54,   52,
   92,   54,   52,  303,  117,  119,   75,  305,   75,   75,
  308,   81,  103,   54,  104,  105,   54,  116,  191,  152,
  245,   85,  216,  274,   31,   31,   31,   85,   31,   26,
   26,   26,   41,   87,   31,  334,   24,   24,   24,   81,
  264,   81,   54,   73,  335,   85,   54,   85,   85,   73,
   63,   71,   97,   48,   31,   54,   31,   71,   54,   26,
  193,   26,   31,   99,   48,  341,   24,   73,   24,   73,
   73,   54,   49,   50,   51,   71,   41,   71,   71,  351,
   52,   74,  206,   49,   50,   51,   72,   74,   54,   17,
   18,   52,   72,   69,   46,  179,   45,   54,  363,   69,
   42,   54,  218,   90,  255,   74,  190,   74,   74,  156,
   72,  239,   72,   72,   87,   67,  336,   69,  161,   69,
   69,   67,   57,   57,   70,   57,  238,   87,  235,  338,
   70,  312,  210,  329,   86,   87,   87,  258,  355,   67,
  260,   67,   67,   47,   48,   62,   68,   57,   70,  311,
   70,   70,   68,   61,   40,   41,  124,  108,  120,   88,
   62,  125,  109,   49,   50,   51,  128,   48,   61,  211,
   68,   52,   68,   68,  215,  115,  144,   48,   63,   42,
  163,   48,  103,  221,  104,  105,   49,   50,   51,  121,
  121,  122,  122,   63,   52,  232,   49,   50,   51,   59,
   49,   50,   51,   60,   52,  176,  317,  224,   52,  180,
   48,  233,  182,   48,   59,  142,  143,  178,   60,  121,
  273,  122,  116,  117,  184,   48,  240,  186,   48,   49,
   50,   51,   49,   50,   51,  242,  263,   52,  157,  158,
   52,  171,  172,  118,   49,   50,   51,   49,   50,   51,
  119,  243,   52,  188,   48,   52,  244,  195,   48,  205,
  247,   62,  340,   17,   18,  249,  197,   48,  250,  199,
   48,  228,  229,   49,   50,   51,  350,   49,   50,   51,
  254,   52,  201,   48,  257,   52,   49,   50,   51,   49,
   50,   51,   64,  267,   52,  362,  269,   52,  268,  203,
   48,  271,   49,   50,   51,  270,  155,   41,  280,   48,
   52,  272,  285,   48,  282,  160,   87,  279,   95,   49,
   50,   51,  231,  237,  248,  251,  252,   52,   49,   50,
   51,  289,   49,   50,   51,  288,   52,  327,  328,  253,
   52,  135,  137,   98,  290,  100,  275,  293,  294,  276,
  292,   68,   69,   70,  298,   10,   11,   62,  301,  307,
   12,  129,  133,   14,   15,   61,  304,   16,  145,  310,
   95,  108,  106,  208,  107,  121,  109,  122,  306,   20,
  108,  106,  296,  107,  297,  109,  299,  300,  174,  164,
   63,  309,  295,   69,   70,  313,   10,   11,  325,  326,
  314,   12,  175,  316,   14,   15,  185,  187,   16,  318,
  324,   59,  330,  213,  189,   60,  332,  333,  339,  342,
   20,  194,  202,  204,  200,  212,  323,  121,  337,  122,
   79,    9,  331,   80,   10,   11,  346,  347,  348,   12,
   13,  352,   14,   15,  354,  356,   16,   17,   18,   19,
  353,  357,    8,    9,  360,  322,   10,   11,   20,  361,
  349,   12,   13,    2,   14,   15,  169,  123,   16,   17,
   18,   19,    8,    9,    0,  343,   10,   11,    0,    0,
   20,   12,   13,    0,   14,   15,    0,    0,   16,   17,
   18,   19,    0,    0,  265,  266,    0,    0,    8,    9,
   20,  345,   10,   11,  181,  183,    0,   12,   13,    0,
   14,   15,    0,    0,   16,   17,   18,   19,    0,  196,
  198,  177,  108,  106,    0,  107,   20,  109,    0,    8,
    9,  281,    0,   10,   11,  283,  284,  286,   12,   13,
  287,   14,   15,    0,    0,   16,   17,   18,   19,  320,
    9,    0,    0,   10,   11,    0,    0,   20,   12,   13,
    0,   14,   15,    0,    0,   16,   17,   18,   19,    0,
   69,   70,    0,   10,   11,    0,    0,   20,   12,    0,
    0,   14,   15,    0,   69,   16,  225,   10,   11,    0,
   69,    0,   12,   10,   11,   14,   15,   20,   12,   16,
    0,   14,   15,  108,  106,   16,  107,    0,  109,    0,
    0,   20,  207,  108,  106,    0,  107,   20,  109,    0,
  315,  209,  108,  106,    0,  107,    0,  109,  222,  108,
  106,    0,  107,    0,  109,  108,  106,  234,  107,  223,
  109,  121,    0,  122,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
   44,   21,   40,   40,    4,   41,   40,   63,   45,   59,
   15,    4,   44,  243,   41,   40,   21,  256,  257,   41,
   45,   21,  257,   45,   41,   25,  258,   41,   21,   43,
   44,   45,  267,  256,   42,   43,   59,   45,   40,   47,
   44,   40,   59,   41,   41,   59,   60,   61,   62,  284,
  273,  274,   60,   61,   62,   44,   41,   41,   62,  289,
  290,   59,   59,   59,   46,   70,   41,   42,   43,   44,
   45,   44,   47,   62,   59,  257,  257,   70,  134,   44,
   44,   46,   59,   59,   59,   60,   61,   62,   41,   62,
   43,   44,   45,  256,  256,  256,   41,  258,   43,   44,
   45,   45,  284,  284,   41,   40,   59,   60,   61,   62,
  273,  274,   45,  275,   59,   60,   61,   62,   43,   40,
   45,  126,   59,   44,   40,   46,   41,   40,   43,   44,
   45,   41,  257,   43,   44,   45,   60,   46,   41,  257,
   43,   44,   45,  148,   59,   60,   61,   62,   44,   59,
   60,   61,   62,  273,  274,  148,   59,   60,   61,   62,
   41,   44,   43,   44,   45,   40,   62,   59,   62,   41,
   44,   43,   44,   45,   44,   43,   44,   45,   59,   60,
   61,   62,   44,   44,  270,   59,  191,   59,   60,   61,
   62,   41,   45,   43,   44,   45,   59,   59,   40,  257,
   59,  206,   44,   59,   46,   43,   46,   45,  257,   59,
   60,   61,   62,  257,  264,   45,   41,   59,   46,  256,
  257,  259,   60,   61,   62,   45,  262,  256,  257,   45,
  257,  256,  257,  256,  256,  257,  270,  281,  243,  276,
  277,  278,  256,  243,  249,  262,  251,  284,  262,  281,
  243,  276,  277,  278,  276,  277,  278,  279,   45,  284,
  256,   45,  284,  263,  262,  262,  280,  267,  282,  283,
  270,  291,  280,   45,  282,  283,   45,  262,  262,  256,
  256,  256,   44,   59,  289,  290,  291,  262,  293,  289,
  290,  291,  257,  257,  299,  259,  289,  290,  291,  319,
   62,  321,   45,  256,  304,  280,   45,  282,  283,  262,
   60,  256,  256,  257,  319,   45,  321,  262,   45,  319,
   46,  321,  327,  256,  257,   59,  319,  280,  321,  282,
  283,   45,  276,  277,  278,  280,  257,  282,  283,   59,
  284,  256,  262,  276,  277,  278,  256,  262,   45,  273,
  274,  284,  262,  256,   44,  257,   46,   45,   59,  262,
  281,   45,  258,   44,  256,  280,  257,  282,  283,   59,
  280,   44,  282,  283,  257,  256,  259,  280,   59,  282,
  283,  262,  256,  257,  256,  259,   59,  257,  256,  259,
  262,   41,   41,   59,  256,  257,  257,  256,  259,  280,
  256,  282,  283,  256,  257,   44,  256,  281,  280,   59,
  282,  283,  262,   44,  256,  257,   42,   42,  256,  281,
   59,   47,   47,  276,  277,  278,  256,  257,   59,   59,
  280,  284,  282,  283,   44,   53,  256,  257,   44,  281,
  256,  257,  280,  258,  282,  283,  276,  277,  278,   43,
   43,   45,   45,   59,  284,  257,  276,  277,  278,   44,
  276,  277,  278,   44,  284,   59,   59,   40,  284,  256,
  257,  257,  256,  257,   59,  256,  257,   41,   59,   43,
  256,   45,  256,  257,  256,  257,   46,  256,  257,  276,
  277,  278,  276,  277,  278,   41,  258,  284,  256,  257,
  284,  256,  257,  277,  276,  277,  278,  276,  277,  278,
  284,  258,  284,  256,  257,  284,   59,  256,  257,  126,
   46,  271,  256,  273,  274,  262,  256,  257,  257,  256,
  257,  149,  150,  276,  277,  278,  256,  276,  277,  278,
   59,  284,  256,  257,   59,  284,  276,  277,  278,  276,
  277,  278,   13,  258,  284,  256,  258,  284,  257,  256,
  257,  257,  276,  277,  278,  258,  256,  257,  256,  257,
  284,   59,  256,  257,  284,  256,  257,   41,   39,  276,
  277,  278,   44,  256,  191,  263,  264,  284,  276,  277,
  278,  258,  276,  277,  278,  257,  284,  263,  264,  206,
  284,   62,   63,   42,  258,   44,  224,  263,  264,  227,
  257,  256,  257,  258,   59,  260,  261,  256,  258,  257,
  265,   60,   61,  268,  269,  256,  258,  272,   67,   41,
   91,   42,   43,   41,   45,   43,   47,   45,  259,  284,
   42,   43,  249,   45,  251,   47,  263,  264,   59,   88,
  256,  259,  256,  257,  258,   59,  260,  261,  263,  264,
   59,  265,  256,  256,  268,  269,  108,  109,  272,   46,
   59,  256,  264,  134,  113,  256,   59,  257,   59,  257,
  284,  120,  124,  125,  123,   41,  293,   43,  259,   45,
  256,  257,  299,  259,  260,  261,  264,  256,   59,  265,
  266,   59,  268,  269,  259,   59,  272,  273,  274,  275,
  264,  264,  256,  257,   59,  259,  260,  261,  284,   59,
  327,  265,  266,    0,  268,  269,   91,   56,  272,  273,
  274,  275,  256,  257,   -1,  259,  260,  261,   -1,   -1,
  284,  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,
  274,  275,   -1,   -1,  215,  216,   -1,   -1,  256,  257,
  284,  259,  260,  261,  106,  107,   -1,  265,  266,   -1,
  268,  269,   -1,   -1,  272,  273,  274,  275,   -1,  121,
  122,   41,   42,   43,   -1,   45,  284,   47,   -1,  256,
  257,  230,   -1,  260,  261,  234,  235,  236,  265,  266,
  239,  268,  269,   -1,   -1,  272,  273,  274,  275,  256,
  257,   -1,   -1,  260,  261,   -1,   -1,  284,  265,  266,
   -1,  268,  269,   -1,   -1,  272,  273,  274,  275,   -1,
  257,  258,   -1,  260,  261,   -1,   -1,  284,  265,   -1,
   -1,  268,  269,   -1,  257,  272,  259,  260,  261,   -1,
  257,   -1,  265,  260,  261,  268,  269,  284,  265,  272,
   -1,  268,  269,   42,   43,  272,   45,   -1,   47,   -1,
   -1,  284,   41,   42,   43,   -1,   45,  284,   47,   -1,
   59,   41,   42,   43,   -1,   45,   -1,   47,   41,   42,
   43,   -1,   45,   -1,   47,   42,   43,   44,   45,   41,
   47,   43,   -1,   45,
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

//#line 316 ".\gramatica.y"
  
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

    void borrarSimbolosDuplicados() {
        ts.borrarSimbolosDuplicados();
    }

    Boolean paramRealIgualFormal(String funcion, String tipoParamReal){
        funcion = actualizarAmbito(funcion);
        if(ts.buscar(funcion) != null){
          String tipoParamFormal = ts.buscar(funcion).getTipoParametroEsperado();
          System.out.println("TIPO PARAM FORMAL: "+tipoParamFormal);
          System.out.println("TIPO PARAM REAL: "+tipoParamReal);
        
          if(tipoParamFormal.equals(tipoParamReal)){
              return true;
          }
        }
        
        
        return false;
    }
//#line 816 "Parser.java"
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
//#line 13 ".\gramatica.y"
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
//#line 21 ".\gramatica.y"
{ yyerror(ERROR_END); }
break;
case 3:
//#line 24 ".\gramatica.y"
{mangling.add(val_peek(1).sval); yyval = val_peek(1);}
break;
case 4:
//#line 25 ".\gramatica.y"
{yyerror(ERROR_BEGIN);}
break;
case 5:
//#line 26 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_PROGRAMA);}
break;
case 6:
//#line 30 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 31 ".\gramatica.y"
{ yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj); }
break;
case 8:
//#line 34 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 9:
//#line 35 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 10:
//#line 38 ".\gramatica.y"
{actualizarTipoDelGrupo(val_peek(2).sval, val_peek(1).sval);}
break;
case 12:
//#line 40 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 13:
//#line 41 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");
                                    if (tipoEmbebido(val_peek(1).sval))
                                        chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                    else
                                        actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                    nameMangling(val_peek(1).sval);
                                }
break;
case 14:
//#line 48 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 15:
//#line 49 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); nameMangling(val_peek(1).sval);}
break;
case 16:
//#line 50 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 17:
//#line 51 ".\gramatica.y"
{yyval = val_peek(1); yyval.obj = null;}
break;
case 18:
//#line 52 ".\gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                    actualizarTipoParamEsperado(val_peek(6).sval, val_peek(4).sval);
                                                                                    System.out.println("FUNCION: "+val_peek(6).sval);
                                                                                    Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);
                                                                                    yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador);
                                                                                    mangling.remove(mangling.size() - 1);
                                                                                    }
break;
case 19:
//#line 59 ".\gramatica.y"
{yyerror(ERROR_RET);}
break;
case 20:
//#line 60 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 21:
//#line 61 ".\gramatica.y"
{yyerror(ERROR_HEADER_FUNC);}
break;
case 23:
//#line 63 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 64 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 65 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 26:
//#line 68 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Funcion"); actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                               errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                               this.nuevoNombre = nameMangling(val_peek(0).sval); mangling.add(val_peek(0).sval); yyval.sval = this.nuevoNombre;
                              }
break;
case 27:
//#line 73 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 30:
//#line 81 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      nameMangling(val_peek(0).sval); System.out.println("HOLA SOY UN PARAMETRO: " + val_peek(0).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      yyval.sval = val_peek(1).sval;
                     }
break;
case 31:
//#line 86 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 32:
//#line 87 ".\gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 43:
//#line 104 ".\gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 44:
//#line 108 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 45:
//#line 109 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 47:
//#line 111 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 48:
//#line 112 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 49:
//#line 115 ".\gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);}
break;
case 50:
//#line 117 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 51:
//#line 120 ".\gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval; 
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 53:
//#line 124 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 55:
//#line 128 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 129 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 130 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 131 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 59:
//#line 134 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 60:
//#line 135 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 61:
//#line 136 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 62:
//#line 138 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 139 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 64:
//#line 143 ".\gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 65:
//#line 145 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 66:
//#line 146 ".\gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 67:
//#line 149 ".\gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 68:
//#line 153 ".\gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 69:
//#line 157 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 158 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 159 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 160 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 161 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 162 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 75:
//#line 163 ".\gramatica.y"
{ yyval = val_peek(0);  }
break;
case 76:
//#line 166 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 170 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 78:
//#line 174 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 175 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 176 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 177 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 178 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 179 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 84:
//#line 180 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 85:
//#line 183 ".\gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 86:
//#line 186 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct*/
        }
break;
case 87:
//#line 189 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"UINTEGER");  /* Nodo para constante UINTEGER*/
         }
break;
case 88:
//#line 192 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"SINGLE");  /* Nodo para constante SINGLE*/
         }
break;
case 89:
//#line 195 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"HEXA");  /* Nodo para constante HEXA*/
         }
break;
case 91:
//#line 199 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable negativa*/
        }
break;
case 92:
//#line 202 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable struct negativa*/
        }
break;
case 93:
//#line 205 ".\gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval,"SINGLE"); System.out.println("CONSTANTE SINGLE NEGATIVA: " + "-"+val_peek(0).sval);}
break;
case 94:
//#line 206 ".\gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 95:
//#line 209 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);
                                                System.out.println("NODO EXPRESION: " + val_peek(2).obj.toString());
                                                if(!paramRealIgualFormal(val_peek(4).sval, ((Nodo)val_peek(2).obj).devolverTipo(mangling))) {yyerror(ERROR_TIPO_PARAMETRO);}
                                               }
break;
case 96:
//#line 213 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 97:
//#line 214 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 98:
//#line 217 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null)); /* No es necesario nodo de control "CUERPO" porque el camino es solo del THEN. MIRAR FILMINAS 14 DEL PAQUETE 08.3 (basado en eso para crear la estructura del arbol adecuado reutilizando clases por patron composite)*/
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 99:
//#line 221 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 100:
//#line 225 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 101:
//#line 226 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 102:
//#line 227 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 228 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 229 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 230 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 231 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 107:
//#line 232 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 108:
//#line 233 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 109:
//#line 234 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 110:
//#line 235 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 111:
//#line 236 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 112:
//#line 239 ".\gramatica.y"
{yyval = val_peek(1);}
break;
case 113:
//#line 240 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 243 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 115:
//#line 244 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 116:
//#line 247 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 117:
//#line 248 ".\gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 118:
//#line 249 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 119:
//#line 250 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 126:
//#line 261 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 127:
//#line 262 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 128:
//#line 263 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 129:
//#line 264 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 130:
//#line 265 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 131:
//#line 266 ".\gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 132:
//#line 269 ".\gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 133:
//#line 273 ".\gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 134:
//#line 274 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 135:
//#line 275 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 276 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 137:
//#line 277 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 138:
//#line 278 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 139:
//#line 281 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct"); }
break;
case 140:
//#line 282 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 141:
//#line 283 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 142:
//#line 284 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 143:
//#line 287 ".\gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);}
break;
case 144:
//#line 288 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 145:
//#line 289 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 146:
//#line 290 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 147:
//#line 293 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 148:
//#line 294 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 149:
//#line 295 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 150:
//#line 296 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 151:
//#line 301 ".\gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 152:
//#line 302 ".\gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 153:
//#line 305 ".\gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 154:
//#line 306 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 155:
//#line 307 ".\gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 156:
//#line 310 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(2).obj,null);}
break;
case 157:
//#line 311 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 158:
//#line 312 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1574 "Parser.java"
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
