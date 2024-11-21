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
    4,    4,    4,    4,    8,    8,    8,    6,    6,    6,
   10,   10,   10,    5,    5,    5,    5,    5,    5,    5,
   11,   11,   18,   18,   18,   18,   18,   18,   19,   19,
    7,    7,    7,    7,    7,    7,    7,   21,   21,   21,
   21,   21,   12,   12,   12,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   13,   13,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   26,
   26,   27,   27,   25,   25,   25,   25,   28,   28,   28,
   28,   28,   28,   15,   15,   15,   15,   15,   15,   16,
   16,   16,   16,   16,   16,   16,    9,    9,    9,    9,
   29,   29,   29,   29,   30,   30,   30,   30,   31,   31,
   17,   17,   17,   24,   24,
};
final static short yylen[] = {                            2,
    3,    3,    2,    2,    2,    1,    2,    1,    1,    3,
    2,    2,    3,    3,    2,    2,    2,    4,    4,    4,
    2,    2,    3,    2,    6,    3,    6,    1,    1,    1,
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
   24,    0,    0,   17,    0,    0,   22,   21,    0,    0,
    0,    0,    0,    0,   51,  122,  123,  119,    0,    0,
    0,    0,  118,  120,  121,    0,    0,    0,    0,   93,
   91,   92,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  139,  137,  140,  138,    0,    0,    0,  113,    0,    0,
    0,  153,  152,  151,   14,   13,    0,   26,    0,   23,
   10,    0,    0,    0,    0,    0,    0,    0,   20,   45,
   44,   43,   95,   94,    0,    0,    0,    0,    0,    0,
    0,   81,   79,   82,   80,    0,    0,   88,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   77,
   75,   78,   76,    0,    0,    0,    0,  128,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  110,  112,    0,    0,    0,    0,    0,   57,    0,
    0,    0,   50,   49,    0,    0,   19,   18,    0,    0,
    0,    0,  155,  154,    0,    0,    0,    0,    0,  129,
  127,  125,  126,  124,    0,    0,    0,  150,  149,    0,
    0,    0,    0,    0,   65,   64,   63,    0,    0,  135,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,   55,   47,   48,   46,    0,    0,    0,    0,    0,
    0,  102,    0,    0,    0,    0,    0,    0,    0,  148,
    0,    0,  147,    0,  134,    0,  133,  131,   33,   27,
   32,   31,   25,   52,    0,  101,    0,    0,    0,    0,
  108,    0,    0,  100,    0,  144,    0,  143,  146,  142,
  136,  132,  130,    0,    0,  106,    0,   98,   96,  105,
    0,  145,  141,  104,    0,    0,  109,  103,  107,   99,
   97,
};
final static short yydgoto[] = {                          3,
    4,   19,   20,   21,   68,   23,   69,   25,   26,  265,
   27,   28,   51,   30,   31,   32,   33,   34,   35,   52,
  155,   53,   54,   55,   56,   70,  139,  106,   62,   63,
  127,
};
final static short yysindex[] = {                        65,
 -228,    3,    0,  481,    0,    0,    0, -221,   18,  135,
   52,  130,   82,  523, -136,    0,    0,  -33,  420,    0,
    0,    0, -130,   15, -217,   25,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  481,    0,  141,    0,  164,
 -182, -121,  101,  154,  128,    0,    0,    0,  167,  -64,
    0,   89,  125,    0,    0,    7,  129,    0,  136,  -46,
  117,   98,  212,  174, -105,  -32,  556,    0,  -26,  -29,
  148,   45,    0,    0, -221,    0,    0,  330,  238,  221,
    0,  151,  177,    0,  -31,  501,    0,    0,  441,  569,
  126,  598,  472,   26,    0,    0,    0,    0,  203,  209,
  215,  242,    0,    0,    0,  248,  -20,  255,   20,    0,
    0,    0,  248,  278,  284,  287,  290,  296,  546,   -9,
  788,  229,  236,  572,  -46,  283,  -10,  120,  137,   75,
    0,    0,    0,    0,  795,  624,  252,    0,  394,  138,
  167,    0,    0,    0,    0,    0,   81,    0,  348,    0,
    0,  180,  808,  144,  166,  312,  230,  461,    0,    0,
    0,    0,    0,    0,  383,  319,  182,  159,  125,  159,
  125,    0,    0,    0,    0,  697,  269,    0,  802,  719,
  546,  200,  269,  159,  125,  159,  125,  697,  269,    0,
    0,    0,    0,  233,  546,  416,   74,    0,   86,  140,
  232,  -46,  -46,  224,  245,  250,  256,  253,  479,  107,
  167,    0,    0,  167,  157,  528,   59,  -53,    0,  248,
  248,  322,    0,    0,  248,  285,    0,    0,  314,  576,
  193,  531,    0,    0,  237,  540,  546,  525,  295,    0,
    0,    0,    0,    0,  329,  331,  332,    0,    0,  331,
  342,  347,  331,  351,    0,    0,    0,  548,  302,    0,
  561,  571,  -22,  272,  583,  269,  269,  697,  269,  269,
    0,    0,    0,    0,    0,  350,  546,  575,  318,   93,
  361,    0,  546,  577,  374,  304,   77,  331,  103,    0,
  378,  109,    0,  587,    0,  114,    0,    0,    0,    0,
    0,    0,    0,    0,  385,    0,  391,  591,  546,  170,
    0,  597,  393,    0,  409,    0,  116,    0,    0,    0,
    0,    0,    0,  602,  408,    0,   34,    0,    0,    0,
  615,    0,    0,    0,  616,  171,    0,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -62,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -38,    0,    0,    0,    0,    0,    0,
    0,    0,  -16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  682,    0,    0,    0,    0,    0,
    0,  -42,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -5,    6,   28,
   35,    0,    0,    0,    0,    0,  -28,    0,    0,    0,
    0,    0,   -6,   57,   64,   69,   97,    1,   40,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  265,  388,  402,  404,  414,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  172,  448,    0,  566,  424,   -3,    0,    0,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,  565,
    0,  494,  405,    0,  316,  355,    0,  631,    0,    0,
   51,
};
final static int YYTABLESIZE=855;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
   24,   56,   84,   84,   84,   84,   84,   40,   84,   29,
  141,   42,  116,   41,   29,   24,   56,   85,  300,   80,
   84,   84,   84,   84,   74,   74,   74,   74,   74,    5,
  116,   29,   24,  203,  115,   72,   36,   72,   72,   72,
   86,  117,   74,   74,   74,   74,   70,  120,   70,   70,
   70,  204,  115,   72,   72,   72,   72,   40,   85,  117,
  182,   42,   29,   41,   70,   70,   70,   70,   73,  167,
   73,   73,   73,   84,   94,   71,   39,   71,   71,   71,
  114,   29,   24,   88,   29,   24,   73,   73,   73,   73,
   53,   57,  337,   71,   71,   71,   71,   68,  114,   68,
   68,   68,  167,  144,   66,   53,   66,   66,   66,   69,
  129,   69,   69,   69,   29,   68,   68,   68,   68,   71,
   85,   64,   66,   66,   66,   66,   78,   69,   69,   69,
   69,  114,  242,  115,   29,   95,   79,   67,   72,   67,
   67,   67,  101,   99,  244,  100,   85,  102,  104,  103,
  105,  311,   85,   29,   24,   67,   67,   67,   67,   85,
  104,  103,  105,  202,  137,  257,  117,  108,  114,  123,
  115,  118,  323,   50,   49,  201,   29,  214,  130,   50,
  203,  206,   50,  202,  162,   50,  114,  222,  115,   60,
   29,  110,  111,   40,   30,  125,  152,  261,  207,  107,
  101,  245,  263,   58,   30,  102,  142,   89,   50,  225,
   58,   50,  112,   56,   56,  260,   56,   84,   50,   16,
   17,   50,   73,   84,  224,  156,   16,   17,  329,  341,
   82,   29,   29,  116,  299,  114,  178,  115,   56,   74,
  140,   84,  287,   84,   84,   74,  289,   50,   38,  292,
   72,  275,  195,   50,   83,  115,   72,  158,    6,   50,
    7,   70,  117,   74,   85,   74,   74,   70,  119,  197,
   81,   82,   29,   37,   72,  203,   72,   72,   29,  151,
   87,  181,  165,   73,  317,   70,   50,   70,   70,   73,
   71,  211,   50,  247,  198,   83,   71,  336,   38,   50,
  143,  114,   53,   53,   29,   53,  166,   73,   61,   73,
   73,  114,   68,  115,   71,  165,   71,   71,   68,   66,
    1,    2,   50,   61,   69,   66,  202,   53,   50,  241,
   69,   50,  208,   82,   50,  316,   68,  217,   68,   68,
   50,  243,  296,   66,  113,   66,   66,   42,   69,  147,
   69,   69,   67,  131,  132,  309,  310,  226,   67,   82,
  295,  318,  256,   50,  109,   82,   50,  320,   96,  322,
   97,   98,   82,   42,  333,  147,   67,  205,   67,   67,
   96,  161,   97,   98,  121,   44,   58,  218,  146,   45,
   43,   44,   58,   43,   44,   45,   90,   44,   45,  221,
   59,   45,   16,   17,   46,   47,   48,  122,   16,   17,
   46,   47,   48,   46,   47,   48,   46,   47,   48,   92,
   44,  223,   43,   44,   45,  328,  340,   45,  229,  135,
   44,   60,  153,   44,   45,   61,  219,   45,  232,   46,
   47,   48,   46,   47,   48,   62,   60,   58,  274,   46,
   47,   48,   46,   47,   48,  215,  216,   59,  168,   44,
   62,  236,   58,   45,  170,   44,   77,  133,  134,   45,
  172,   44,   59,  194,  240,   45,  150,   82,   46,   47,
   48,  250,  126,  128,   46,   47,   48,   36,  227,  246,
   46,   47,   48,  148,  149,  237,  238,  174,   44,  277,
  278,  251,   45,  176,   44,  173,  175,  252,   45,  254,
  179,   44,  164,  253,  114,   45,  115,   46,   47,   48,
   61,  191,  193,   46,   47,   48,  258,  301,  302,  259,
   46,   47,   48,  184,   44,  235,   77,  255,   45,  186,
   44,  271,  188,   44,   45,  190,   44,   45,  200,  239,
   45,  192,   44,   46,   47,   48,   45,  283,  284,   46,
   47,   48,   46,   47,   48,   46,   47,   48,  262,   22,
  272,   46,   47,   48,  230,   44,  276,  268,   44,   45,
  307,  308,   45,  282,   22,  145,  285,  286,  294,  288,
  280,  281,  169,  171,   46,   47,   48,   46,   47,   48,
  290,   22,   91,  291,   93,   77,  304,  185,  187,  293,
  101,   99,  199,  100,  114,  102,  115,  101,   99,  297,
  100,  124,  102,  303,  312,  248,  249,  160,  136,  298,
  315,  305,  138,  306,  273,  314,  319,  313,  163,  101,
   99,  264,  100,   60,  102,  321,  325,  154,  324,  326,
   66,   22,  212,   10,   22,  330,  331,   62,   11,   58,
  334,   13,   14,  327,  210,   15,  114,  332,  115,   59,
  177,  335,  180,  338,  339,   75,    9,  183,   76,   10,
  189,    2,  116,    0,   11,   12,    0,   13,   14,    0,
    0,   15,   16,   17,   18,    0,    8,    9,    0,  159,
   10,    0,    0,    0,  213,   11,   12,    0,   13,   14,
    0,    0,   15,   16,   17,   18,    8,    9,    0,  228,
   10,    0,    0,   22,    0,   11,   12,    0,   13,   14,
  231,    0,   15,   16,   17,   18,    8,    9,  101,   99,
   10,  100,    0,  102,    0,   11,   12,    0,   13,   14,
    0,    0,   15,   16,   17,   18,  157,    9,    0,  234,
   10,  114,    0,  115,    0,   11,   12,    0,   13,   14,
    0,    0,   15,   16,   17,   18,    0,    0,   65,   66,
   67,    0,   10,    0,  266,  267,  269,   11,    0,  270,
   13,   14,    0,    0,   15,  279,   66,   67,    0,   10,
    0,    0,   66,   67,   11,   10,    0,   13,   14,    0,
   11,   15,   66,   13,   14,   10,    0,   15,    0,    0,
   11,    0,    0,   13,   14,    0,    0,   15,  196,  101,
   99,    0,  100,    0,  102,  209,  101,   99,    0,  100,
    0,  102,  233,  101,   99,    0,  100,    0,  102,  101,
   99,  220,  100,    0,  102,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
    4,   44,   41,   42,   43,   44,   45,   40,   47,   14,
   40,   44,   41,   46,   19,   19,   59,   44,   41,   23,
   59,   60,   61,   62,   41,   59,   43,   44,   45,  258,
   59,   36,   36,   44,   41,   41,  258,   43,   44,   45,
  258,   41,   59,   60,   61,   62,   41,   41,   43,   44,
   45,   62,   59,   59,   60,   61,   62,   40,   44,   59,
   41,   44,   67,   46,   59,   60,   61,   62,   41,   44,
   43,   44,   45,   59,  257,   41,   59,   43,   44,   45,
   41,   86,   86,   59,   89,   89,   59,   60,   61,   62,
   44,   40,   59,   59,   60,   61,   62,   41,   59,   43,
   44,   45,   44,   59,   41,   59,   43,   44,   45,   41,
   60,   43,   44,   45,  119,   59,   60,   61,   62,  256,
   44,   40,   59,   60,   61,   62,  257,   59,   60,   61,
   62,   43,   59,   45,  139,  257,  267,   41,  275,   43,
   44,   45,   42,   43,   59,   45,   44,   47,   60,   61,
   62,   59,   44,  158,  158,   59,   60,   61,   62,   44,
   60,   61,   62,   44,  270,   59,   42,   40,   43,   41,
   45,   47,   59,   45,   40,  125,  181,   40,   62,   45,
   44,   62,   45,   44,   59,   45,   43,   44,   45,   60,
  195,  256,  257,   40,  257,   60,   46,   41,   62,   46,
   42,   62,  256,  257,  267,   47,   59,   36,   45,   44,
  257,   45,  277,  256,  257,   59,  259,  256,   45,  273,
  274,   45,  256,  262,   59,  257,  273,  274,   59,   59,
  257,  236,  237,  262,  257,   43,  257,   45,  281,  256,
  270,  280,  246,  282,  283,  262,  250,   45,  281,  253,
  256,   59,  262,   45,  281,  262,  262,   86,  256,   45,
  258,  256,  262,  280,   44,  282,  283,  262,  262,   41,
  256,  257,  277,  256,  280,   44,  282,  283,  283,   59,
  256,  262,  257,  256,  288,  280,   45,  282,  283,  262,
  256,   40,   45,   62,   59,  281,  262,  264,  281,   45,
  256,  262,  256,  257,  309,  259,  281,  280,   44,  282,
  283,   43,  256,   45,  280,  257,  282,  283,  262,  256,
  256,  257,   45,   59,  256,  262,   44,  281,   45,  256,
  262,   45,  258,  257,   45,  259,  280,  257,  282,  283,
   45,  256,   41,  280,  256,  282,  283,   44,  280,   46,
  282,  283,  256,  256,  257,  263,  264,   46,  262,  257,
   59,  259,  256,   45,   49,  257,   45,  259,  280,  256,
  282,  283,  257,   44,  259,   46,  280,  258,  282,  283,
  280,  256,  282,  283,  256,  257,  257,   40,   59,  261,
  256,  257,  257,  256,  257,  261,  256,  257,  261,  256,
  271,  261,  273,  274,  276,  277,  278,  279,  273,  274,
  276,  277,  278,  276,  277,  278,  276,  277,  278,  256,
  257,  256,  256,  257,  261,  256,  256,  261,   46,  256,
  257,   44,  256,  257,  261,   12,  257,  261,  257,  276,
  277,  278,  276,  277,  278,   44,   59,   44,  256,  276,
  277,  278,  276,  277,  278,  140,  141,   44,  256,  257,
   59,  262,   59,  261,  256,  257,   19,  256,  257,  261,
  256,  257,   59,  119,   59,  261,  256,  257,  276,  277,
  278,  258,   59,   60,  276,  277,  278,  258,  259,  258,
  276,  277,  278,  256,  257,  263,  264,  256,  257,  263,
  264,  257,  261,  256,  257,  101,  102,  258,  261,  257,
  256,  257,   41,  258,   43,  261,   45,  276,  277,  278,
  256,  117,  118,  276,  277,  278,  211,  256,  257,  214,
  276,  277,  278,  256,  257,  181,   89,   59,  261,  256,
  257,  257,  256,  257,  261,  256,  257,  261,  125,  195,
  261,  256,  257,  276,  277,  278,  261,  263,  264,  276,
  277,  278,  276,  277,  278,  276,  277,  278,   41,    4,
  257,  276,  277,  278,  256,  257,   46,  256,  257,  261,
  263,  264,  261,   59,   19,  256,  258,  257,   41,  258,
  236,  237,   99,  100,  276,  277,  278,  276,  277,  278,
  259,   36,   38,  257,   40,  158,  257,  114,  115,  259,
   42,   43,   41,   45,   43,   47,   45,   42,   43,   59,
   45,   57,   47,   41,  264,  202,  203,   59,   64,   59,
  257,  277,   67,   59,   59,   59,  259,  283,   41,   42,
   43,  218,   45,  256,   47,   59,  256,   83,  264,   59,
  257,   86,  259,  260,   89,   59,  264,  256,  265,  256,
   59,  268,  269,  309,   41,  272,   43,  259,   45,  256,
  106,  264,  108,   59,   59,  256,  257,  113,  259,  260,
  116,    0,   52,   -1,  265,  266,   -1,  268,  269,   -1,
   -1,  272,  273,  274,  275,   -1,  256,  257,   -1,  259,
  260,   -1,   -1,   -1,  139,  265,  266,   -1,  268,  269,
   -1,   -1,  272,  273,  274,  275,  256,  257,   -1,  259,
  260,   -1,   -1,  158,   -1,  265,  266,   -1,  268,  269,
  166,   -1,  272,  273,  274,  275,  256,  257,   42,   43,
  260,   45,   -1,   47,   -1,  265,  266,   -1,  268,  269,
   -1,   -1,  272,  273,  274,  275,  256,  257,   -1,   41,
  260,   43,   -1,   45,   -1,  265,  266,   -1,  268,  269,
   -1,   -1,  272,  273,  274,  275,   -1,   -1,  256,  257,
  258,   -1,  260,   -1,  220,  221,  222,  265,   -1,  225,
  268,  269,   -1,   -1,  272,  256,  257,  258,   -1,  260,
   -1,   -1,  257,  258,  265,  260,   -1,  268,  269,   -1,
  265,  272,  257,  268,  269,  260,   -1,  272,   -1,   -1,
  265,   -1,   -1,  268,  269,   -1,   -1,  272,   41,   42,
   43,   -1,   45,   -1,   47,   41,   42,   43,   -1,   45,
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
"sentencia_declarativa : header_funcion BEGIN lista_sentencias END",
"sentencia_declarativa : header_funcion BEGIN error END",
"sentencia_declarativa : error BEGIN lista_sentencias END",
"sentencia_declarativa : struct ';'",
"sentencia_declarativa : struct error",
"sentencia_declarativa : tipo lista_variables error",
"sentencia_declarativa : lista_variables error",
"header_funcion : tipo FUN ID '(' parametro ')'",
"header_funcion : tipo FUN error",
"header_funcion : tipo FUN ID '(' error ')'",
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

//#line 468 "gramatica.y"
  
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
//#line 827 "Parser.java"
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
                                                                        FileHandler.appendToFile(filePathParser,"FUNCION: "+val_peek(3).sval);
                                                                        Nodo delimitador = new NodoConcreto("FIN_FUNCION_"+val_peek(3).sval);
                                                                        yyval.obj = new NodoFuncion(val_peek(3).sval,(Nodo)val_peek(1).obj,delimitador, ts.devolverTipo(val_peek(3).sval));
                                                                        mangling.remove(mangling.size() - 1);
                                                                    }
                                                                  }
break;
case 19:
//#line 121 "gramatica.y"
{yyerror(ERROR_RET);}
break;
case 20:
//#line 122 "gramatica.y"
{yyerror(ERROR_HEADER_FUNC);}
break;
case 22:
//#line 124 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 23:
//#line 125 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 24:
//#line 126 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 25:
//#line 129 "gramatica.y"
{
                                if(ts.buscar(actualizarAmbito(val_peek(3).sval)) == null){
                                    actualizarUso(val_peek(3).sval, "Funcion"); actualizarTipo(val_peek(3).sval, val_peek(5).sval);
                                    errorRedeclaracion(val_peek(3).sval,"Error: Redeclaración de nombre. Linea: "+lex.getNumeroLinea()+" funcion: ");
                                    this.nuevoNombre = nameMangling(val_peek(3).sval); mangling.add(val_peek(3).sval); yyval.sval = this.nuevoNombre;
                                    String[] parametro = (val_peek(1).sval).split(",");
                                    nameMangling(parametro[1]);
                                    actualizarTipoParamEsperado(this.nuevoNombre, parametro[0]);
                                    yyval.ival = 1;
                                }else{
                                    yyerror(FUNCION_REDECLARADA);
                                    borrarSimbolos(val_peek(3).sval);
                                    yyval.ival = 0;
                                }
                              }
break;
case 26:
//#line 145 "gramatica.y"
{yyerror(ERROR_NOMBRE_FUNCION);}
break;
case 27:
//#line 146 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 31:
//#line 155 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Parametro"); actualizarTipo(val_peek(0).sval, val_peek(1).sval);
                      errorRedeclaracion(val_peek(0).sval,"Error: redeclaración. Linea: "+lex.getNumeroLinea()+ " parametro: ");
                      yyval.sval = val_peek(1).sval + "," + val_peek(0).sval;
                     }
break;
case 32:
//#line 159 "gramatica.y"
{yyerror(ERROR_NOMBRE_PARAMETRO);}
break;
case 33:
//#line 160 "gramatica.y"
{yyerror(ERROR_TIPO);}
break;
case 43:
//#line 176 "gramatica.y"
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
//#line 186 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 45:
//#line 187 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 46:
//#line 188 "gramatica.y"
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
//#line 199 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 48:
//#line 200 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 49:
//#line 203 "gramatica.y"
{FileHandler.appendToFile(filePathParser,"ASIGNACION MULTIPLE");
                                                                         yyval.obj = new NodoAsignacionMultiple(":=",(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
                                                                         System.out.println("CANTIDAD DE ELEMENTOS: " + val_peek(3).sval);
                                                                         if (!igualCantElementos(val_peek(3).sval,val_peek(1).sval)) 
                                                                            yyerror(ERROR_CANTIDAD_ASIGNACION);
                                                                         borrarSimbolos(val_peek(3).sval);
                                                                        }
break;
case 50:
//#line 210 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 51:
//#line 213 "gramatica.y"
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
//#line 224 "gramatica.y"
{actualizarUso(val_peek(0).sval, "Variable");
                                            yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;
                                            Token simbolo = estaDeclarado(val_peek(0).sval);
                                            if (simbolo != null)
                                                yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,new NodoConcreto(simbolo.getLexema(), simbolo.getType()));
                                            else
                                                yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,new NodoConcreto(val_peek(2).sval));}
break;
case 55:
//#line 233 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 56:
//#line 234 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 57:
//#line 235 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 58:
//#line 238 "gramatica.y"
{yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 59:
//#line 239 "gramatica.y"
{yyval.obj = new NodoLista(",",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj); yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 60:
//#line 240 "gramatica.y"
{yyerror(ERROR_COMA);}
break;
case 61:
//#line 242 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 62:
//#line 243 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
case 63:
//#line 247 "gramatica.y"
{ FileHandler.appendToFile(filePathParser,"RETORNO. Linea "+lex.getNumeroLinea());
                                      yyval.obj = new NodoRet("RET",(Nodo)val_peek(2).obj,null);
                                    }
break;
case 64:
//#line 250 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 65:
//#line 251 "gramatica.y"
{yyerror(ERROR_RETORNO);}
break;
case 66:
//#line 254 "gramatica.y"
{
                yyval.obj = new NodoSuma("+",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
                FileHandler.appendToFile(filePathParser, "SUMA. Linea " + lex.getNumeroLinea());
            }
break;
case 67:
//#line 258 "gramatica.y"
{
            yyval.obj = new NodoResta("-",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
            FileHandler.appendToFile(filePathParser, "RESTA. Linea " + lex.getNumeroLinea());
        }
break;
case 68:
//#line 262 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 69:
//#line 263 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 70:
//#line 264 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 71:
//#line 265 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 72:
//#line 266 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 73:
//#line 267 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 74:
//#line 268 "gramatica.y"
{ yyval = val_peek(0);  }
break;
case 75:
//#line 271 "gramatica.y"
{
              yyval.obj = new NodoMultiplicacion("*",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              FileHandler.appendToFile(filePathParser, "MULTIPLICACION. Linea " + lex.getNumeroLinea());
         }
break;
case 76:
//#line 275 "gramatica.y"
{
              yyval.obj = new NodoDivision("/",(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);
              FileHandler.appendToFile(filePathParser, "DIVISION. Linea " + lex.getNumeroLinea());
         }
break;
case 77:
//#line 279 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 78:
//#line 280 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 79:
//#line 281 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 80:
//#line 282 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 81:
//#line 283 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 82:
//#line 284 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 83:
//#line 285 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 84:
//#line 288 "gramatica.y"
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
//#line 299 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"UINTEGER");  /* Nodo para constante UINTEGER*/
         }
break;
case 86:
//#line 302 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"SINGLE");  /* Nodo para constante SINGLE*/
         }
break;
case 87:
//#line 305 "gramatica.y"
{
            yyval.obj = new NodoConcreto(val_peek(0).sval,"HEXA");  /* Nodo para constante HEXA*/
         }
break;
case 88:
//#line 308 "gramatica.y"
{   /* Nodo para una variable struct*/
                        yyval.obj = new NodoConcreto(val_peek(2).sval + "." + val_peek(0).sval,ts.buscar(val_peek(0).sval+":"+val_peek(2).sval).getType());
                        borrarSimbolos(val_peek(0).sval);
                        }
break;
case 91:
//#line 314 "gramatica.y"
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
//#line 323 "gramatica.y"
{actualizarSimbolo("-" + val_peek(0).sval,val_peek(0).sval); yyval.obj = new NodoConcreto("-"+val_peek(0).sval,"SINGLE");}
break;
case 93:
//#line 324 "gramatica.y"
{yyerror(ERROR_NO_NEGATIVO);}
break;
case 94:
//#line 327 "gramatica.y"
{
                                                Nodo nodoExpresion = (Nodo)val_peek(1).obj; /* N/D si no hay nada*/

                                                if ((estaDeclarado(val_peek(3).sval) != null) && paramRealIgualFormal(val_peek(3).sval,nodoExpresion.devolverTipo(mangling))){
                                                    String nombreFuncion = estaDeclarado(val_peek(3).sval).getLexema();
                                                    yyval.obj = new NodoInvocacionFuncion("INVOCACION_FUNCION_" + nombreFuncion,nodoExpresion,null, ts.buscar(nombreFuncion).getType());
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
//#line 346 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 96:
//#line 349 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(5).obj,null),new NodoCuerpo("CUERPO",(Nodo)val_peek(2).obj,null));
                  FileHandler.appendToFile(filePathParser,"DECLARACION DE IF. Linea " + lex.getNumeroLinea() );
              }
break;
case 97:
//#line 353 "gramatica.y"
{
                  yyval.obj = new NodoCompuesto("IF",new NodoCompuesto("CONDICION",(Nodo)val_peek(7).obj,null),new NodoCuerpo("CUERPO",new NodoCompuesto("THEN",(Nodo)val_peek(4).obj,null),new NodoCompuesto("ELSE",(Nodo)val_peek(2).obj,null)));
                  FileHandler.appendToFile(filePathParser,"DECLARACION DE IF-ELSE. Linea " + lex.getNumeroLinea());
              }
break;
case 98:
//#line 357 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 99:
//#line 358 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 100:
//#line 359 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 101:
//#line 360 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 102:
//#line 361 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 103:
//#line 362 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 104:
//#line 363 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 105:
//#line 364 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 106:
//#line 365 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 107:
//#line 366 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 108:
//#line 367 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 109:
//#line 368 "gramatica.y"
{yyerror(ERROR_END_IF);}
break;
case 110:
//#line 371 "gramatica.y"
{yyval = val_peek(1);}
break;
case 111:
//#line 372 "gramatica.y"
{yyval = val_peek(0);}
break;
case 112:
//#line 375 "gramatica.y"
{yyval.obj = new NodoCompuesto("s",(Nodo)val_peek(1).obj, new NodoCompuesto("s",(Nodo)val_peek(0).obj, null)); }
break;
case 113:
//#line 376 "gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 379 "gramatica.y"
{yyval.obj = new NodoCondicion(val_peek(1).sval,(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 115:
//#line 380 "gramatica.y"
{yyerror(ERROR_OPERADOR);}
break;
case 116:
//#line 381 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 117:
//#line 382 "gramatica.y"
{yyerror(ERROR_OPERANDO);}
break;
case 124:
//#line 393 "gramatica.y"
{yyval.obj = new NodoOUTF("OUTF",(Nodo)val_peek(2).obj,null);}
break;
case 125:
//#line 394 "gramatica.y"
{yyval.obj = new NodoOUTF("OUTF",new NodoConcreto(val_peek(2).sval, "CADENA"),null);}
break;
case 126:
//#line 395 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 127:
//#line 396 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 128:
//#line 397 "gramatica.y"
{yyerror(ERROR_CANTIDAD_PARAMETRO);}
break;
case 129:
//#line 398 "gramatica.y"
{yyerror(ERROR_PARAMETRO);}
break;
case 130:
//#line 401 "gramatica.y"
{  FileHandler.appendToFile(filePathParser,"SENTENCIA REPEAT UNTIL. Linea "+lex.getNumeroLinea());
                                                                        yyval.obj = new NodoRepeat("REPEAT_UNTIL",new NodoCompuesto("CUERPO",(Nodo)val_peek(5).obj,null),new NodoCompuesto("CONDICION",(Nodo)val_peek(2).obj,null));
                                                                    }
break;
case 131:
//#line 404 "gramatica.y"
{yyerror(ERROR_UNTIL);}
break;
case 132:
//#line 405 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 133:
//#line 406 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 134:
//#line 407 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 135:
//#line 408 "gramatica.y"
{yyerror(ERROR_PARENTESIS);}
break;
case 136:
//#line 409 "gramatica.y"
{yyerror(ERROR_CUERPO);}
break;
case 137:
//#line 412 "gramatica.y"
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
//#line 420 "gramatica.y"
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
//#line 428 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 140:
//#line 429 "gramatica.y"
{yyerror(ERROR_ID_STRUCT);}
break;
case 141:
//#line 432 "gramatica.y"
{actualizarTipoStruct(val_peek(4).sval, val_peek(1).sval);  yyval.sval = val_peek(1).sval+"."+val_peek(4).sval;}
break;
case 142:
//#line 433 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 143:
//#line 434 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 144:
//#line 435 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 145:
//#line 438 "gramatica.y"
{actualizarUso(val_peek(1).sval, "Variable"); yyval.sval = val_peek(1).sval+"."+ val_peek(4).sval;}
break;
case 146:
//#line 439 "gramatica.y"
{yyerror(ERROR_STRUCT);}
break;
case 147:
//#line 440 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 148:
//#line 441 "gramatica.y"
{yyerror(ERROR_TIPO_STRUCT);}
break;
case 149:
//#line 446 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 150:
//#line 447 "gramatica.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 151:
//#line 450 "gramatica.y"
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
//#line 459 "gramatica.y"
{yyerror(ERROR_PUNTOCOMA);}
break;
case 153:
//#line 460 "gramatica.y"
{yyerror(ERROR_ETIQUETA);}
break;
case 154:
//#line 463 "gramatica.y"
{yyval.obj = new NodoTOS("TOS",(Nodo)val_peek(1).obj,null);}
break;
case 155:
//#line 464 "gramatica.y"
{yyerror(ERROR_EXPRESION);}
break;
//#line 1727 "Parser.java"
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
