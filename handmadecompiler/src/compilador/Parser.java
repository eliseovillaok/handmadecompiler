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
    7,    2,    2,    3,    2,    3,    3,    1,    1,    2,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    4,    4,    6,    6,    6,    4,    4,    3,
    7,    3,    5,    2,    6,    2,    4,    3,    3,    3,
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
    0,    0,    0,    0,    0,   29,   28,    0,    0,    0,
    6,    8,    9,    0,    0,    0,    0,   33,   34,   35,
   36,   37,   38,   39,   40,   41,    0,   16,   54,    0,
   15,    0,    0,    0,    0,    0,    0,   85,   86,   87,
    0,    0,   88,    0,    0,   83,   89,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  111,    0,
    0,    0,    0,   12,   11,    0,    0,    1,    7,    0,
    0,    0,    0,   25,    0,    0,   17,    0,    0,   23,
   22,    0,    0,    0,    0,    0,    0,    0,    0,   50,
  122,  123,  119,    0,    0,    0,    0,  118,  120,  121,
    0,    0,    0,   92,   90,   91,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  139,  137,  140,  138,    0,    0,
    0,  113,    0,    0,    0,  153,  152,  151,    0,   14,
   13,   27,   26,    0,   24,   10,    0,    0,    0,    0,
   52,    0,    0,    0,   32,   31,   30,    0,   44,   43,
   42,    0,    0,    0,    0,    0,    0,    0,   81,   79,
   82,   80,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   77,   75,   78,   76,    0,
    0,    0,    0,  128,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  110,  112,    0,
    0,    0,    0,    0,    0,   57,    0,    0,    0,   49,
   48,    0,    0,    0,    0,    0,   94,   95,   93,    0,
  155,  154,    0,    0,    0,    0,    0,  129,  127,  125,
  126,  124,    0,    0,    0,  150,  149,    0,    0,    0,
    0,    0,   65,   64,   63,    0,    0,  135,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   53,    0,
    0,    0,   55,    0,    0,    0,    0,    0,  102,    0,
    0,    0,    0,    0,    0,    0,  148,    0,    0,  147,
    0,  134,    0,  133,  131,   46,   47,   45,    0,    0,
    0,    0,   21,    0,  101,    0,    0,    0,    0,  108,
    0,    0,  100,    0,  144,    0,  143,  146,  142,  136,
  132,  130,   51,   20,   19,   18,    0,    0,  106,    0,
   98,   96,  105,    0,  145,  141,  104,    0,    0,  109,
  103,  107,   99,   97,
};
final static short yydgoto[] = {                          3,
    4,   20,   21,   22,   69,   24,   70,   26,   94,   27,
   28,   29,   53,   31,   32,   33,   34,   35,   36,   54,
  160,   55,   56,   57,   58,   71,  143,  111,   63,   64,
  131,
};
final static short yysindex[] = {                      -136,
 -239, -174,    0,  496,    0,    0,    0,  -18,  127,  159,
   25,   85,   28,  549, -219,    0,    0,   34,   -7,  380,
    0,    0,    0,  123,  -42,   56,   70,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  223,    0,    0,  165,
    0,  188, -140,  -95,   12,  132,  138,    0,    0,    0,
  200,   84,    0,  145,  176,    0,    0,  -38,  153,  -54,
  118,  122,  155,  183,  206,  -78,  -32,  592,    0,  -29,
  -36,  152,  107,    0,    0,  -57,  -18,    0,    0,  149,
  190,  175,  329,    0,  218,  212,    0, -214,  242,    0,
    0,   48,  195,  316,  579,  -25,  625,  479,   71,    0,
    0,    0,    0,  224,  230,  256,  262,    0,    0,    0,
  270,  288,   -1,    0,    0,    0,  270,  294,  297,  300,
  306,  333,  572,   22,  731,  319,  326,  590,  118,  352,
  -20,  -16,  141,  161,    0,    0,    0,    0,  824,  661,
  383,    0,  586,  177,  200,    0,    0,    0,  -35,    0,
    0,    0,    0,  191,    0,    0,  201,  838,   67,  143,
    0,  428,  -28,  431,    0,    0,    0,  247,    0,    0,
    0,  411,  110,  464,  305,  176,  305,  176,    0,    0,
    0,    0,  712,  125,  832,  734,  572,  252,  125,  305,
  176,  305,  176,  712,  125,    0,    0,    0,    0,  196,
  572,  462,  130,    0,  137,  147,   27,  118,  118,  267,
  271,  272,  277,  280,  470,  194,  200,    0,    0,  200,
  189,  500,  336,  258,  535,    0,  270,  270,  342,    0,
    0,  270,  330,  344,  347,  496,    0,    0,    0,  343,
    0,    0,  229,  566,  572,  548,  231,    0,    0,    0,
    0,    0,  350, -190,  357,    0,    0, -190,  358,  368,
 -190,  373,    0,    0,    0,  593,  291,    0,  568,  588,
  618,   79,  605,  125,  125,  712,  125,  125,    0,  496,
  529,  426,    0,  572,  599,  240,  154,  395,    0,  572,
  603,  412,  111,   -3, -190,    8,    0,  419,   61,    0,
  612,    0,  215,    0,    0,    0,    0,    0,  422,  455,
  -33,  476,    0,  416,    0,  432,  628,  572,  243,    0,
  630,  433,    0,  434,    0,   96,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  631,  439,    0,   -6,
    0,    0,    0,  637,    0,    0,    0,  646,  253,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -12,    0,    0,    0,    0,
    0,    0,    0,    0,   17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  709,    0,    0,    0,
    0,    0,    0,    0,  -21,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,   47,   54,   82,    0,    0,
    0,    0,    0,  -15,    0,    0,    0,    0,    4,   87,
   92,  115,  120,   10,   59,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  157,  168,  287,  335,  345,    0,    0,
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
    0, -200,  -19,    0,    7,  499,    1,    0,  627,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,  564,
    0,  538,  474,    0,  424,  429,    0,  663,    0,    0,
    5,
};
final static int YYTABLESIZE=885;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
   79,   88,  124,  145,   25,  129,   37,   42,  224,   30,
   23,   44,  234,   43,   88,   30,   87,  118,    5,  119,
   25,   37,   56,  209,   83,  116,   23,  208,   84,   84,
   84,   84,   84,  171,   84,  282,   72,   56,   76,  188,
   88,  210,  161,  116,  115,  212,   84,   84,   84,   84,
  117,   88,  350,  106,  104,   73,  105,   74,  107,   74,
   74,   74,  115,   30,   59,  133,  293,   65,  117,  162,
  209,  109,  108,  110,  142,   74,   74,   74,   74,  310,
  312,    6,   72,    7,   72,   72,   72,   70,  255,   70,
   70,   70,   75,   82,   73,   89,   73,   73,   73,  114,
   72,   72,   72,   72,   88,   70,   70,   70,   70,  118,
  229,  119,   73,   73,   73,   73,   99,  114,   30,    1,
    2,  118,   71,  119,   71,   71,   71,   68,   91,   68,
   68,   68,   66,  207,   66,   66,   66,  308,   30,   88,
   71,   71,   71,   71,   61,   68,   68,   68,   68,  219,
   66,   66,   66,   66,   44,   69,   43,   69,   69,   69,
   67,  100,   67,   67,   67,  148,   42,  118,  239,  119,
   44,   42,   43,   69,   69,   69,   69,  112,   67,   67,
   67,   67,   30,  134,  209,   41,  232,  118,  250,  119,
  208,  141,   44,  127,   43,  252,   30,   52,   51,  149,
   61,  231,  213,   52,  109,  108,  110,  151,  253,   52,
  146,   60,  320,   84,   85,   61,  220,  121,   16,   17,
  154,   52,  122,  123,   39,  335,   60,   85,  165,  269,
  170,   30,   52,  144,   56,   56,   25,   56,   86,   30,
   30,  211,   23,   84,   52,  223,  116,  268,   40,   84,
   52,   86,  265,   85,  294,  325,   52,  349,  296,   56,
  187,  299,   79,  157,   85,  115,  327,   84,   52,   84,
   84,  117,   74,  332,   52,   30,   30,   30,   74,   30,
   25,   25,   25,  201,  254,   30,   23,   23,   23,   74,
   79,  101,   79,  102,  103,  326,   74,   72,   74,   74,
   52,  342,   70,   72,  165,   30,   52,   30,   70,   73,
   25,  354,   25,   30,   52,   73,   23,   85,   23,  329,
  114,   72,  228,   72,   72,   90,   70,  174,   70,   70,
   62,  303,   52,   73,  307,   73,   73,   71,   52,  114,
  115,   52,   68,   71,   52,   62,  106,   66,   68,  302,
   52,  107,   85,   66,  346,   60,  168,   16,   17,  203,
  116,   71,  147,   71,   71,  238,   68,   39,   68,   68,
   69,   66,   88,   66,   66,   67,   69,   52,   58,   80,
   52,   67,   38,   39,  204,  249,   52,  156,   59,   81,
   16,   17,  251,   58,   69,  208,   69,   69,  230,   67,
  117,   67,   67,   59,  150,   39,   82,   40,  125,   46,
  135,  136,   61,   47,   45,   46,  318,  319,  214,   47,
   95,   46,  217,   60,  101,   47,  102,  103,   48,   49,
   50,  126,   45,   46,   48,   49,   50,   47,  137,  138,
   48,   49,   50,   97,   46,  152,  153,  225,   47,  264,
  166,  167,   48,   49,   50,   45,   46,  226,  245,  246,
   47,  139,   46,   48,   49,   50,   47,  158,   46,  237,
  331,  235,   47,  233,  113,   48,   49,   50,   92,  175,
   46,   48,   49,   50,   47,  177,   46,   48,   49,   50,
   47,  284,  285,  290,  291,   16,   17,  163,  341,   48,
   49,   50,  316,  317,  236,   48,   49,   50,  353,  240,
   62,  179,   46,  244,   16,   17,   47,  181,   46,  173,
  248,  118,   47,  119,  258,  183,   46,  259,  263,  260,
   47,   48,   49,   50,  261,   93,  262,   48,   49,   50,
  270,  273,   62,  185,   46,   48,   49,   50,   47,  190,
   46,  200,  192,   46,   47,  194,   46,   47,  130,  132,
   47,  196,   46,   48,   49,   50,   47,  221,  222,   48,
   49,   50,   48,   49,   50,   48,   49,   50,  224,  180,
  182,   48,   49,   50,  155,   85,  279,   93,  198,   46,
   58,  271,   46,   47,  197,  199,   47,  276,   46,  283,
   59,  280,   47,   96,  281,   98,  289,  292,   48,   49,
   50,   48,   49,   50,  295,  243,  297,   48,   49,   50,
  106,  104,  128,  105,  298,  107,  304,  206,  140,  247,
  205,  300,  118,  301,  119,   77,    9,  169,   78,   10,
  266,  176,  178,  267,   11,   12,  305,   13,   14,  159,
  309,   15,   16,   17,   18,  191,  193,  315,  321,  106,
  104,  323,  105,   19,  107,  172,  106,  104,  324,  105,
  330,  107,  287,  288,  184,  186,  306,  328,  333,  337,
  189,    8,    9,  195,  313,   10,  339,  338,  343,  347,
   11,   12,  345,   13,   14,  351,  344,   15,   16,   17,
   18,  216,  348,  118,  352,  119,  256,  257,    2,   19,
    8,    9,  314,  334,   10,  164,  120,    0,  322,   11,
   12,    0,   13,   14,    0,    0,   15,   16,   17,   18,
    0,    8,    9,    0,  336,   10,    0,    0,   19,    0,
   11,   12,    0,   13,   14,    0,  340,   15,   16,   17,
   18,    8,    9,  106,  104,   10,  105,    0,  107,   19,
   11,   12,    0,   13,   14,    0,    0,   15,   16,   17,
   18,  202,  106,  104,  242,  105,  118,  107,  119,   19,
    0,    0,    0,    0,  311,    9,  272,    0,   10,    0,
  274,  275,  277,   11,   12,  278,   13,   14,    0,    0,
   15,   16,   17,   18,   66,   67,   68,    0,   10,    0,
    0,    0,   19,   11,    0,    0,   13,   14,    0,    0,
   15,  286,   67,   68,    0,   10,    0,    0,   67,   68,
   11,   10,   19,   13,   14,    0,   11,   15,    0,   13,
   14,    0,   67,   15,  218,   10,    0,    0,   67,   19,
   11,   10,    0,   13,   14,   19,   11,   15,    0,   13,
   14,    0,    0,   15,  215,  106,  104,    0,  105,   19,
  107,    0,  241,  106,  104,   19,  105,    0,  107,  106,
  104,  227,  105,    0,  107,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
   20,   44,   41,   40,    4,   60,   40,   40,   44,   14,
    4,   44,   41,   46,   44,   20,   59,   43,  258,   45,
   20,   40,   44,   44,   24,   41,   20,   44,   41,   42,
   43,   44,   45,   59,   47,  236,  256,   59,   46,   41,
   44,   62,  257,   59,   41,   62,   59,   60,   61,   62,
   41,   44,   59,   42,   43,  275,   45,   41,   47,   43,
   44,   45,   59,   68,   40,   61,  257,   40,   59,  284,
   44,   60,   61,   62,   68,   59,   60,   61,   62,  280,
  281,  256,   41,  258,   43,   44,   45,   41,   62,   43,
   44,   45,   59,  284,   41,   40,   43,   44,   45,   41,
   59,   60,   61,   62,   44,   59,   60,   61,   62,   43,
   44,   45,   59,   60,   61,   62,  257,   59,  123,  256,
  257,   43,   41,   45,   43,   44,   45,   41,   59,   43,
   44,   45,   41,  129,   43,   44,   45,   59,  143,   44,
   59,   60,   61,   62,   60,   59,   60,   61,   62,  143,
   59,   60,   61,   62,   44,   41,   46,   43,   44,   45,
   41,  257,   43,   44,   45,   59,   40,   43,   59,   45,
   44,   40,   46,   59,   60,   61,   62,   40,   59,   60,
   61,   62,  187,   62,   44,   59,   44,   43,   59,   45,
   44,  270,   44,   41,   46,   59,  201,   45,   40,  257,
   44,   59,   62,   45,   60,   61,   62,   59,   62,   45,
   59,   44,   59,  256,  257,   59,   40,   42,  273,  274,
   46,   45,   47,  262,  257,  259,   59,  257,  257,   41,
  256,  236,   45,  270,  256,  257,  236,  259,  281,  244,
  245,  258,  236,  256,   45,  281,  262,   59,  281,  262,
   45,  281,   59,  257,  254,  259,   45,  264,  258,  281,
  262,  261,  282,   46,  257,  262,  259,  280,   45,  282,
  283,  262,  256,   59,   45,  280,  281,  282,  262,  284,
  280,  281,  282,  262,  258,  290,  280,  281,  282,  256,
  310,  280,  312,  282,  283,  295,  280,  256,  282,  283,
   45,   59,  256,  262,  257,  310,   45,  312,  262,  256,
  310,   59,  312,  318,   45,  262,  310,  257,  312,  259,
  262,  280,  256,  282,  283,  256,  280,  257,  282,  283,
   44,   41,   45,  280,  256,  282,  283,  256,   45,  256,
  257,   45,  256,  262,   45,   59,   42,  256,  262,   59,
   45,   47,  257,  262,  259,  271,   41,  273,  274,   41,
  277,  280,  256,  282,  283,  256,  280,  257,  282,  283,
  256,  280,   44,  282,  283,  256,  262,   45,   44,  257,
   45,  262,  256,  257,   59,  256,   45,   59,   44,  267,
  273,  274,  256,   59,  280,   44,  282,  283,  256,  280,
  256,  282,  283,   59,  256,  257,  284,  281,  256,  257,
  256,  257,  256,  261,  256,  257,  263,  264,  258,  261,
  256,  257,   40,  256,  280,  261,  282,  283,  276,  277,
  278,  279,  256,  257,  276,  277,  278,  261,  256,  257,
  276,  277,  278,  256,  257,  256,  257,  257,  261,  256,
  256,  257,  276,  277,  278,  256,  257,  257,  263,  264,
  261,  256,  257,  276,  277,  278,  261,  256,  257,   59,
  256,   41,  261,   46,   51,  276,  277,  278,  256,  256,
  257,  276,  277,  278,  261,  256,  257,  276,  277,  278,
  261,  263,  264,  263,  264,  273,  274,  256,  256,  276,
  277,  278,  263,  264,  258,  276,  277,  278,  256,   46,
   12,  256,  257,  262,  273,  274,  261,  256,  257,   41,
   59,   43,  261,   45,  258,  256,  257,  257,   59,  258,
  261,  276,  277,  278,  258,   37,  257,  276,  277,  278,
   41,  284,  256,  256,  257,  276,  277,  278,  261,  256,
  257,  123,  256,  257,  261,  256,  257,  261,   60,   61,
  261,  256,  257,  276,  277,  278,  261,  144,  145,  276,
  277,  278,  276,  277,  278,  276,  277,  278,   44,  106,
  107,  276,  277,  278,  256,  257,  257,   89,  256,  257,
  256,  256,  257,  261,  121,  122,  261,  256,  257,  257,
  256,  258,  261,   40,  258,   42,   59,  258,  276,  277,
  278,  276,  277,  278,  258,  187,  259,  276,  277,  278,
   42,   43,   59,   45,  257,   47,   59,  129,   65,  201,
   41,  259,   43,   41,   45,  256,  257,   59,  259,  260,
  217,  104,  105,  220,  265,  266,   59,  268,  269,   86,
   46,  272,  273,  274,  275,  118,  119,   59,  264,   42,
   43,   59,   45,  284,   47,   41,   42,   43,  257,   45,
   59,   47,  244,  245,  111,  112,   59,  259,  257,  264,
  117,  256,  257,  120,  259,  260,   59,  256,   59,   59,
  265,  266,  259,  268,  269,   59,  264,  272,  273,  274,
  275,   41,  264,   43,   59,   45,  208,  209,    0,  284,
  256,  257,  284,  259,  260,   89,   54,   -1,  290,  265,
  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,  275,
   -1,  256,  257,   -1,  259,  260,   -1,   -1,  284,   -1,
  265,  266,   -1,  268,  269,   -1,  318,  272,  273,  274,
  275,  256,  257,   42,   43,  260,   45,   -1,   47,  284,
  265,  266,   -1,  268,  269,   -1,   -1,  272,  273,  274,
  275,   41,   42,   43,   41,   45,   43,   47,   45,  284,
   -1,   -1,   -1,   -1,  256,  257,  223,   -1,  260,   -1,
  227,  228,  229,  265,  266,  232,  268,  269,   -1,   -1,
  272,  273,  274,  275,  256,  257,  258,   -1,  260,   -1,
   -1,   -1,  284,  265,   -1,   -1,  268,  269,   -1,   -1,
  272,  256,  257,  258,   -1,  260,   -1,   -1,  257,  258,
  265,  260,  284,  268,  269,   -1,  265,  272,   -1,  268,
  269,   -1,  257,  272,  259,  260,   -1,   -1,  257,  284,
  265,  260,   -1,  268,  269,  284,  265,  272,   -1,  268,
  269,   -1,   -1,  272,   41,   42,   43,   -1,   45,  284,
   47,   -1,   41,   42,   43,  284,   45,   -1,   47,   42,
   43,   44,   45,   -1,   47,
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

//#line 316 ".\gramatica.y"
  
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
{String[] lista = (val_peek(1).sval).split(",");
                                                   for (String s : lista){
                                                        nameMangling(s);
                                                   }
                                                   actualizarTipoDelGrupo(val_peek(2).sval, val_peek(1).sval);
                                                  }
break;
case 12:
//#line 45 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 13:
//#line 46 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");
                                    if (tipoEmbebido(val_peek(1).sval))
                                        chequeoTipo(val_peek(1).sval,val_peek(2).sval);
                                    else
                                        actualizarTipo(val_peek(1).sval, val_peek(2).sval);
                                    nameMangling(val_peek(1).sval);
                                }
break;
case 14:
//#line 53 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 15:
//#line 54 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); nameMangling(val_peek(1).sval);}
break;
case 16:
//#line 55 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 17:
//#line 56 ".\gramatica.y"
{yyval = val_peek(1); yyval.obj = null;}
break;
case 18:
//#line 57 ".\gramatica.y"
{System.out.println("DECLARACION FUNCION. Linea "+lex.getNumeroLinea());
                                                                                    actualizarTipoParamEsperado(val_peek(6).sval, val_peek(4).sval);
                                                                                    System.out.println("FUNCION: "+val_peek(6).sval);
                                                                                    Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(6).sval);
                                                                                    yyval.obj = new NodoCompuesto("FUNCION_"+val_peek(6).sval,(Nodo)val_peek(1).obj,delimitador, ts.devolverTipo(val_peek(6).sval));
                                                                                    mangling.remove(mangling.size() - 1);
                                                                                    }
break;
case 19:
//#line 64 ".\gramatica.y"
{yyerror(ERROR_RET);}
break;
case 20:
//#line 65 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 21:
//#line 66 ".\gramatica.y"
{yyerror(ERROR_HEADER_FUNC);}
break;
case 23:
//#line 68 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 69 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 70 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 26:
//#line 73 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Funcion"); actualizarTipo(val_peek(0).sval, val_peek(2).sval);
                               errorRedeclaracion(val_peek(0).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                               this.nuevoNombre = nameMangling(val_peek(0).sval); mangling.add(val_peek(0).sval); yyval.sval = this.nuevoNombre;     
                              }
break;
case 27:
//#line 78 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 30:
//#line 86 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      nameMangling(val_peek(0).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      yyval.sval = val_peek(1).sval;
                     }
break;
case 31:
//#line 91 ".\gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 32:
//#line 92 ".\gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 42:
//#line 108 ".\gramatica.y"
{
                      yyval.obj = new NodoCompuestoBinario(":=",new NodoConcreto(val_peek(3).sval),(Nodo)val_peek(1).obj); /* Lo creamos compuesto*/
                      System.out.println("ASIGNACION");
                   }
break;
case 43:
//#line 112 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 44:
//#line 113 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 46:
//#line 115 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 47:
//#line 116 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 48:
//#line 119 ".\gramatica.y"
{System.out.println("ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoCompuestoBinario(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
                                                                         if (!igualCantElementos(val_peek(3).sval,val_peek(1).sval)) {yyerror(ERROR_CANTIDAD_ASIGNACION);}
                                                                        }
break;
case 49:
//#line 123 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 50:
//#line 126 ".\gramatica.y"
{actualizarUso(val_peek(2).sval, "Variable"); actualizarUso(val_peek(0).sval, "Variable");
                                                          yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                                          yyval.obj = new NodoCompuestoBinario(",",new NodoConcreto(val_peek(2).sval),new NodoConcreto(val_peek(0).sval));}
break;
case 52:
//#line 130 ".\gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(0).sval));}
break;
case 54:
//#line 134 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 55:
//#line 135 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 136 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 137 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 140 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 59:
//#line 141 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 60:
//#line 142 ".\gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 61:
//#line 144 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 62:
//#line 145 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 149 ".\gramatica.y"
{System.out.println("RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoCompuesto("RET",(Nodo)val_peek(2).obj,null);}
break;
case 64:
//#line 151 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 65:
//#line 152 ".\gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 66:
//#line 155 ".\gramatica.y"
{
                yyval.obj = new NodoCompuestoBinario("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                System.out.println("SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 67:
//#line 159 ".\gramatica.y"
{
            yyval.obj = new NodoCompuestoBinario("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            System.out.println("RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 68:
//#line 163 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 69:
//#line 164 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 165 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 166 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 167 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 168 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 169 ".\gramatica.y"
{ yyval = val_peek(0);  }
break;
case 75:
//#line 172 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 76:
//#line 176 ".\gramatica.y"
{
              yyval.obj = new NodoCompuestoBinario("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              System.out.println("DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 180 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 78:
//#line 181 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 182 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 183 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 184 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 185 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 186 ".\gramatica.y"
{ yyval = val_peek(0); }
break;
case 84:
//#line 189 ".\gramatica.y"
{
             yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable*/
         }
break;
case 85:
//#line 192 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"UINTEGER");  /* Nodo para constante UINTEGER*/
         }
break;
case 86:
//#line 195 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"SINGLE");  /* Nodo para constante SINGLE*/
         }
break;
case 87:
//#line 198 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"HEXA");  /* Nodo para constante HEXA*/
         }
break;
case 90:
//#line 203 ".\gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval);  /* Nodo para una variable negativa*/
        }
break;
case 91:
//#line 206 ".\gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval,"SINGLE");}
break;
case 92:
//#line 207 ".\gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 93:
//#line 210 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("INVOCACION_FUNCION_" + val_peek(4).sval,(Nodo)val_peek(2).obj,null);
                                                System.out.println("NODO EXPRESION: " + val_peek(2).obj.toString());
                                                if(!paramRealIgualFormal(val_peek(4).sval, ((Nodo)val_peek(2).obj).devolverTipo(mangling))) {yyerror(ERROR_TIPO_PARAMETRO);}
                                               }
break;
case 94:
//#line 214 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 95:
//#line 215 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 96:
//#line 218 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CUERPO",(Nodo)val_peek(2).obj,null));
                  System.out.println("DECLARACION DE IF. Linea " + lex.getNumeroLinea());
              }
break;
case 97:
//#line 222 ".\gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCompuesto("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  System.out.println("DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 98:
//#line 226 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 99:
//#line 227 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 100:
//#line 228 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 101:
//#line 229 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 102:
//#line 230 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 231 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 232 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 233 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 234 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 107:
//#line 235 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 108:
//#line 236 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 109:
//#line 237 ".\gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 110:
//#line 240 ".\gramatica.y"
{yyval = val_peek(1);}
break;
case 111:
//#line 241 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 112:
//#line 244 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 113:
//#line 245 ".\gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 248 ".\gramatica.y"
{yyval.obj = new NodoCompuestoBinario(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 115:
//#line 249 ".\gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 116:
//#line 250 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 117:
//#line 251 ".\gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 124:
//#line 262 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 125:
//#line 263 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("OUTF",new NodoConcreto(val_peek(2).sval),null);}
break;
case 126:
//#line 264 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 127:
//#line 265 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 128:
//#line 266 ".\gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 129:
//#line 267 ".\gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 130:
//#line 270 ".\gramatica.y"
{
                                                                    System.out.println("SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                    yyval.obj = new NodoCompuesto("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 131:
//#line 274 ".\gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 132:
//#line 275 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 133:
//#line 276 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 134:
//#line 277 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 135:
//#line 278 ".\gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 279 ".\gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 137:
//#line 282 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT MULTIPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct"); ts.insertar(new TokenStruct( 257, val_peek(0).sval, val_peek(1).sval )); }
break;
case 138:
//#line 283 ".\gramatica.y"
{System.out.println("DECLARACION DE STRUCT SIMPLE. Linea "+lex.getNumeroLinea()); actualizarUso(val_peek(0).sval, "Struct");}
break;
case 139:
//#line 284 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 140:
//#line 285 ".\gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 141:
//#line 288 ".\gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);  yyval.sval = val_peek(1).sval+"."+val_peek(4).sval;}
break;
case 142:
//#line 289 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 143:
//#line 290 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 144:
//#line 291 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 145:
//#line 294 ".\gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable");}
break;
case 146:
//#line 295 ".\gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 147:
//#line 296 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 148:
//#line 297 ".\gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 149:
//#line 302 ".\gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 150:
//#line 303 ".\gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 151:
//#line 306 ".\gramatica.y"
{System.out.println("SENTENCIA GOTO. Linea "+lex.getNumeroLinea()); errorRedeclaracion(val_peek(1).sval,"Error: Redeclaración. Linea: "+lex.getNumeroLinea()+" etiqueta:"); yyval.obj = new NodoCompuesto("GOTO",new NodoConcreto(val_peek(1).sval),null);}
break;
case 152:
//#line 307 ".\gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 153:
//#line 308 ".\gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 154:
//#line 311 ".\gramatica.y"
{yyval.obj = new NodoCompuesto("TOS",(Nodo)val_peek(1).obj,null,"SINGLE");}
break;
case 155:
//#line 312 ".\gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1565 "Parser.java"
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
