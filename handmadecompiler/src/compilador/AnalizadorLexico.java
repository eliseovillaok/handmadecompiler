package compilador;
import java.io.*;
import java.util.*;

import acciones_semanticas.*;

public class AnalizadorLexico {    
	private static volatile AnalizadorLexico unicaInstancia = new AnalizadorLexico(); 
    private final String ARCHIVO_MATRIZ_ESTADOS = "src/matrizEstados.txt";
    private final String ARCHIVO_MATRIZ_ACCIONES = "src/MatrizAccionesSemanticas.txt";

    private final char TABULACION = '\t';
    private final char BLANCO = ' ';
    private final char NUEVA_LINEA = '\n';
    private int estadoActual = 0;
    private char entrada;
    
    private final int[][] MATRIZ_TRANCISION_ESTADOS = { //-1 representa fin de cadena, -2 representa error
        /*E0*/  {1, 2, -2, 9, 9, 9, 13, 12, 10, 10, 9, 12, 9, 9, 9, 9, 9, -2, 7, 1, 1, 1, 18, 16, -2, 0, 0, -1},
        /*E1*/  {1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E2*/  {-1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E3*/  {-2, 4, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
        /*E4*/  {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 5, -1, -1, -1, -1, -1, -1, -1},
        /*E5*/  {-2, 6, -2, 6, 6, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
        /*E6*/  {-1, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E7*/  {-1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E8*/  {-1, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E9*/  {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E10*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E11*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E12*/ {-2, -2, -2, -2, -2, -2, -2, -2, -2, 11, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
        /*E13*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        /*E14*/ {14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 14, 14, 14, 14, 14, 14, 14, 14, 14, -2, 14},
        /*E15*/ {14, 14, 14, 14, 14, 14, 14, 0, 14, 14, 14, 14, 14, 14, 14, 14, 15, 14, 14, 14, 14, 14, 14, 14, 14, 14, -2, 14},
        /*E16*/ {16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, -2, 16, -2},
        /*E17*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    };
    
    private AccionSemantica[][] MatrizAS;

    private void loadSAMatrix() {
        // Crear acciones semánticas
        AccionSemantica AS0 = null;
        AccionSemantica AS1 = null;
        AccionSemantica AS2 = null;
        AccionSemantica AS3 = null;
        AccionSemantica AS4 = null;
        AccionSemantica AS5 = null;
        AccionSemantica AS6 = null;
        AccionSemantica AS7 = null;
        AccionSemantica ASE = null;
        
        // Inicializar matriz de acciones semánticas
        MatrizAS = new AccionSemantica[18][28];
    
        // Fila 0
        MatrizAS[0][0] = AS1;
        MatrizAS[0][1] = AS1;
        MatrizAS[0][2] = ASE;
        MatrizAS[0][3] = AS1;
        MatrizAS[0][4] = AS1;
        MatrizAS[0][5] = AS1;
        MatrizAS[0][6] = AS1;
        MatrizAS[0][7] = AS1;
        MatrizAS[0][8] = AS1;
        MatrizAS[0][9] = AS1;
        MatrizAS[0][10] = AS1;
        MatrizAS[0][11] = AS1;
        MatrizAS[0][12] = AS1;
        MatrizAS[0][13] = AS1;
        MatrizAS[0][14] = AS1;
        MatrizAS[0][15] = AS1;
        MatrizAS[0][16] = AS1;
        MatrizAS[0][17] = ASE;
        MatrizAS[0][18] = AS1;
        MatrizAS[0][19] = AS1;
        MatrizAS[0][20] = AS1;
        MatrizAS[0][21] = AS1;
        MatrizAS[0][22] = ASE;
        MatrizAS[0][23] = AS1;
        MatrizAS[0][24] = ASE;
        MatrizAS[0][25] = AS0;
        MatrizAS[0][26] = AS0;
        MatrizAS[0][27] = AS3;
    
        // Fila 1
        MatrizAS[1][0] = AS2;
        MatrizAS[1][1] = AS2;
        MatrizAS[1][2] = AS2;
        MatrizAS[1][3] = AS3;
        MatrizAS[1][4] = AS3;
        MatrizAS[1][5] = AS3;
        MatrizAS[1][6] = AS3;
        MatrizAS[1][7] = AS3;
        MatrizAS[1][8] = AS3;
        MatrizAS[1][9] = AS3;
        MatrizAS[1][10] = AS3;
        MatrizAS[1][11] = AS3;
        MatrizAS[1][12] = AS3;
        MatrizAS[1][13] = AS3;
        MatrizAS[1][14] = AS3;
        MatrizAS[1][15] = AS3;
        MatrizAS[1][16] = AS3;
        MatrizAS[1][17] = AS3;
        MatrizAS[1][18] = AS3;
        MatrizAS[1][19] = AS3;
        MatrizAS[1][20] = AS3;
        MatrizAS[1][21] = AS3;
        MatrizAS[1][22] = AS3;
        MatrizAS[1][23] = AS3;
        MatrizAS[1][24] = AS3;
        MatrizAS[1][25] = AS3;
        MatrizAS[1][26] = AS3;
        MatrizAS[1][27] = AS3;
    
        // Fila 2
        MatrizAS[2][0] = AS3;
        MatrizAS[2][1] = AS2;
        MatrizAS[2][2] = AS3;
        MatrizAS[2][3] = AS3;
        MatrizAS[2][4] = AS3;
        MatrizAS[2][5] = AS3;
        MatrizAS[2][6] = AS3;
        MatrizAS[2][7] = AS3;
        MatrizAS[2][8] = AS3;
        MatrizAS[2][9] = AS3;
        MatrizAS[2][10] = AS3;
        MatrizAS[2][11] = AS3;
        MatrizAS[2][12] = AS3;
        MatrizAS[2][13] = AS3;
        MatrizAS[2][14] = AS3;
        MatrizAS[2][15] = AS2;
        MatrizAS[2][16] = AS5;
        MatrizAS[2][17] = AS5;
        MatrizAS[2][18] = AS5;
        MatrizAS[2][19] = AS5;
        MatrizAS[2][20] = AS5;
        MatrizAS[2][21] = AS5;
        MatrizAS[2][22] = AS5;
        MatrizAS[2][23] = AS5;
        MatrizAS[2][24] = AS5;
        MatrizAS[2][25] = AS5;
        MatrizAS[2][26] = AS5;
        MatrizAS[2][27] = AS5;
    
            // Fila 3
        MatrizAS[3][0] = ASE;
        MatrizAS[3][1] = AS2;
        MatrizAS[3][2] = ASE;
        MatrizAS[3][3] = ASE;
        MatrizAS[3][4] = ASE;
        MatrizAS[3][5] = ASE;
        MatrizAS[3][6] = ASE;
        MatrizAS[3][7] = ASE;
        MatrizAS[3][8] = ASE;
        MatrizAS[3][9] = ASE;
        MatrizAS[3][10] = ASE;
        MatrizAS[3][11] = ASE;
        MatrizAS[3][12] = ASE;
        MatrizAS[3][13] = ASE;
        MatrizAS[3][14] = ASE;
        MatrizAS[3][15] = ASE;
        MatrizAS[3][16] = ASE;
        MatrizAS[3][17] = ASE;
        MatrizAS[3][18] = ASE;
        MatrizAS[3][19] = ASE;
        MatrizAS[3][20] = ASE;
        MatrizAS[3][21] = ASE;
        MatrizAS[3][22] = ASE;
        MatrizAS[3][23] = ASE;
        MatrizAS[3][24] = ASE;
        MatrizAS[3][25] = ASE;
        MatrizAS[3][26] = ASE;
        MatrizAS[3][27] = ASE;

        // Fila 4
        MatrizAS[4][0] = AS5;
        MatrizAS[4][1] = ASE;
        MatrizAS[4][2] = AS5;
        MatrizAS[4][3] = AS5;
        MatrizAS[4][4] = AS5;
        MatrizAS[4][5] = AS5;
        MatrizAS[4][6] = AS5;
        MatrizAS[4][7] = AS5;
        MatrizAS[4][8] = AS5;
        MatrizAS[4][9] = AS5;
        MatrizAS[4][10] = AS5;
        MatrizAS[4][11] = AS5;
        MatrizAS[4][12] = AS5;
        MatrizAS[4][13] = AS5;
        MatrizAS[4][14] = AS5;
        MatrizAS[4][15] = AS5;
        MatrizAS[4][16] = AS5;
        MatrizAS[4][17] = AS5;
        MatrizAS[4][18] = AS5;
        MatrizAS[4][19] = AS5;
        MatrizAS[4][20] = AS5;
        MatrizAS[4][21] = AS2;
        MatrizAS[4][22] = AS5;
        MatrizAS[4][23] = AS5;
        MatrizAS[4][24] = AS5;
        MatrizAS[4][25] = AS5;
        MatrizAS[4][26] = AS5;
        MatrizAS[4][27] = AS5;

        // Fila 5
        MatrizAS[5][0] = ASE;
        MatrizAS[5][1] = AS2;
        MatrizAS[5][2] = ASE;
        MatrizAS[5][3] = AS2;
        MatrizAS[5][4] = AS2;
        MatrizAS[5][5] = ASE;
        MatrizAS[5][6] = ASE;
        MatrizAS[5][7] = ASE;
        MatrizAS[5][8] = ASE;
        MatrizAS[5][9] = ASE;
        MatrizAS[5][10] = ASE;
        MatrizAS[5][11] = ASE;
        MatrizAS[5][12] = ASE;
        MatrizAS[5][13] = ASE;
        MatrizAS[5][14] = ASE;
        MatrizAS[5][15] = ASE;
        MatrizAS[5][16] = ASE;
        MatrizAS[5][17] = ASE;
        MatrizAS[5][18] = ASE;
        MatrizAS[5][19] = ASE;
        MatrizAS[5][20] = ASE;
        MatrizAS[5][21] = ASE;
        MatrizAS[5][22] = ASE;
        MatrizAS[5][23] = ASE;
        MatrizAS[5][24] = ASE;
        MatrizAS[5][25] = ASE;
        MatrizAS[5][26] = ASE;
        MatrizAS[5][27] = ASE;

        // Fila 6
        MatrizAS[6][0] = AS3;
        MatrizAS[6][1] = AS2;
        MatrizAS[6][2] = AS5;
        MatrizAS[6][3] = AS5;
        MatrizAS[6][4] = AS5;
        MatrizAS[6][5] = AS5;
        MatrizAS[6][6] = AS5;
        MatrizAS[6][7] = AS5;
        MatrizAS[6][8] = AS5;
        MatrizAS[6][9] = AS5;
        MatrizAS[6][10] = AS5;
        MatrizAS[6][11] = AS5;
        MatrizAS[6][12] = AS5;
        MatrizAS[6][13] = AS5;
        MatrizAS[6][14] = AS5;
        MatrizAS[6][15] = AS5;
        MatrizAS[6][16] = AS5;
        MatrizAS[6][17] = AS5;
        MatrizAS[6][18] = AS5;
        MatrizAS[6][19] = AS5;
        MatrizAS[6][20] = AS5;
        MatrizAS[6][21] = AS5;
        MatrizAS[6][22] = AS5;
        MatrizAS[6][23] = AS5;
        MatrizAS[6][24] = AS5;
        MatrizAS[6][25] = AS5;
        MatrizAS[6][26] = AS5;
        MatrizAS[6][27] = AS5;

        // Fila 7
        MatrizAS[7][0] = AS3;
        MatrizAS[7][1] = AS2;
        MatrizAS[7][2] = AS5;
        MatrizAS[7][3] = AS5;
        MatrizAS[7][4] = AS5;
        MatrizAS[7][5] = AS5;
        MatrizAS[7][6] = AS5;
        MatrizAS[7][7] = AS5;
        MatrizAS[7][8] = AS5;
        MatrizAS[7][9] = AS5;
        MatrizAS[7][10] = AS5;
        MatrizAS[7][11] = AS5;
        MatrizAS[7][12] = AS5;
        MatrizAS[7][13] = AS5;
        MatrizAS[7][14] = AS5;
        MatrizAS[7][15] = AS5;
        MatrizAS[7][16] = AS5;
        MatrizAS[7][17] = AS5;
        MatrizAS[7][18] = AS5;
        MatrizAS[7][19] = AS2;
        MatrizAS[7][20] = AS5;
        MatrizAS[7][21] = AS5;
        MatrizAS[7][22] = AS5;
        MatrizAS[7][23] = AS5;
        MatrizAS[7][24] = AS5;
        MatrizAS[7][25] = AS5;
        MatrizAS[7][26] = AS5;
        MatrizAS[7][27] = AS5;

        // Fila 8
        MatrizAS[8][0] = AS4;
        MatrizAS[8][1] = AS2;
        MatrizAS[8][2] = AS4;
        MatrizAS[8][3] = AS4;
        MatrizAS[8][4] = AS4;
        MatrizAS[8][5] = AS4;
        MatrizAS[8][6] = AS4;
        MatrizAS[8][7] = AS4;
        MatrizAS[8][8] = AS4;
        MatrizAS[8][9] = AS4;
        MatrizAS[8][10] = AS4;
        MatrizAS[8][11] = AS4;
        MatrizAS[8][12] = AS4;
        MatrizAS[8][13] = AS4;
        MatrizAS[8][14] = AS4;
        MatrizAS[8][15] = AS4;
        MatrizAS[8][16] = AS4;
        MatrizAS[8][17] = AS4;
        MatrizAS[8][18] = AS4;
        MatrizAS[8][19] = AS4;
        MatrizAS[8][20] = AS2;
        MatrizAS[8][21] = AS4;
        MatrizAS[8][22] = AS4;
        MatrizAS[8][23] = AS4;
        MatrizAS[8][24] = AS4;
        MatrizAS[8][25] = AS4;
        MatrizAS[8][26] = AS4;
        MatrizAS[8][27] = AS4;

        // Fila 9
        MatrizAS[9][0] = AS6;
        MatrizAS[9][1] = AS6;
        MatrizAS[9][2] = AS6;
        MatrizAS[9][3] = AS6;
        MatrizAS[9][4] = AS6;
        MatrizAS[9][5] = AS6;
        MatrizAS[9][6] = AS6;
        MatrizAS[9][7] = AS6;
        MatrizAS[9][8] = AS6;
        MatrizAS[9][9] = AS6;
        MatrizAS[9][10] = AS6;
        MatrizAS[9][11] = AS6;
        MatrizAS[9][12] = AS6;
        MatrizAS[9][13] = AS6;
        MatrizAS[9][14] = AS6;
        MatrizAS[9][15] = AS6;
        MatrizAS[9][16] = AS6;
        MatrizAS[9][17] = AS6;
        MatrizAS[9][18] = AS6;
        MatrizAS[9][19] = AS6;
        MatrizAS[9][20] = AS6;
        MatrizAS[9][21] = AS6;
        MatrizAS[9][22] = AS6;
        MatrizAS[9][23] = AS6;
        MatrizAS[9][24] = AS6;
        MatrizAS[9][25] = AS6;
        MatrizAS[9][26] = AS6;
        MatrizAS[9][27] = AS6;

        // Fila 10
        MatrizAS[10][0] = AS6;
        MatrizAS[10][1] = AS6;
        MatrizAS[10][2] = AS6;
        MatrizAS[10][3] = AS6;
        MatrizAS[10][4] = AS6;
        MatrizAS[10][5] = AS6;
        MatrizAS[10][6] = AS6;
        MatrizAS[10][7] = AS6;
        MatrizAS[10][8] = AS6;
        MatrizAS[10][9] = AS6;
        MatrizAS[10][10] = AS2;
        MatrizAS[10][11] = AS6;
        MatrizAS[10][12] = AS6;
        MatrizAS[10][13] = AS6;
        MatrizAS[10][14] = AS6;
        MatrizAS[10][15] = AS6;
        MatrizAS[10][16] = AS6;
        MatrizAS[10][17] = AS6;
        MatrizAS[10][18] = AS6;
        MatrizAS[10][19] = AS6;
        MatrizAS[10][20] = AS6;
        MatrizAS[10][21] = AS6;
        MatrizAS[10][22] = AS6;
        MatrizAS[10][23] = AS6;
        MatrizAS[10][24] = AS6;
        MatrizAS[10][25] = AS6;
        MatrizAS[10][26] = AS6;
        MatrizAS[10][27] = AS6;

        // Fila 11
        MatrizAS[11][0] = ASE;
        MatrizAS[11][1] = ASE;
        MatrizAS[11][2] = ASE;
        MatrizAS[11][3] = ASE;
        MatrizAS[11][4] = ASE;
        MatrizAS[11][5] = ASE;
        MatrizAS[11][6] = ASE;
        MatrizAS[11][7] = ASE;
        MatrizAS[11][8] = ASE;
        MatrizAS[11][9] = ASE;
        MatrizAS[11][10] = AS2;
        MatrizAS[11][11] = ASE;
        MatrizAS[11][12] = ASE;
        MatrizAS[11][13] = ASE;
        MatrizAS[11][14] = ASE;
        MatrizAS[11][15] = ASE;
        MatrizAS[11][16] = ASE;
        MatrizAS[11][17] = ASE;
        MatrizAS[11][18] = ASE;
        MatrizAS[11][19] = ASE;
        MatrizAS[11][20] = ASE;
        MatrizAS[11][21] = ASE;
        MatrizAS[11][22] = ASE;
        MatrizAS[11][23] = ASE;
        MatrizAS[11][24] = ASE;
        MatrizAS[11][25] = ASE;
        MatrizAS[11][26] = ASE;
        MatrizAS[11][27] = ASE;

        // Fila 12
        MatrizAS[12][0] = AS6;
        MatrizAS[12][1] = AS6;
        MatrizAS[12][2] = AS6;
        MatrizAS[12][3] = AS6;
        MatrizAS[12][4] = AS6;
        MatrizAS[12][5] = AS6;
        MatrizAS[12][6] = AS6;
        MatrizAS[12][7] = AS6;
        MatrizAS[12][8] = AS6;
        MatrizAS[12][9] = AS6;
        MatrizAS[12][10] = AS6;
        MatrizAS[12][11] = AS6;
        MatrizAS[12][12] = AS6;
        MatrizAS[12][13] = AS6;
        MatrizAS[12][14] = AS6;
        MatrizAS[12][15] = AS6;
        MatrizAS[12][16] = AS6;
        MatrizAS[12][17] = null;  // Campo vacío
        MatrizAS[12][18] = AS6;
        MatrizAS[12][19] = AS6;
        MatrizAS[12][20] = AS6;
        MatrizAS[12][21] = AS6;
        MatrizAS[12][22] = AS6;
        MatrizAS[12][23] = AS6;
        MatrizAS[12][24] = AS6;
        MatrizAS[12][25] = AS6;
        MatrizAS[12][26] = AS6;
        MatrizAS[12][27] = AS6;

        // Fila 13
        MatrizAS[13][0] = null;  // Campo vacío
        MatrizAS[13][1] = null;  // Campo vacío
        MatrizAS[13][2] = null;  // Campo vacío
        MatrizAS[13][3] = null;  // Campo vacío
        MatrizAS[13][4] = null;  // Campo vacío
        MatrizAS[13][5] = null;  // Campo vacío
        MatrizAS[13][6] = null;  // Campo vacío
        MatrizAS[13][7] = null;  // Campo vacío
        MatrizAS[13][8] = null;  // Campo vacío
        MatrizAS[13][9] = null;  // Campo vacío
        MatrizAS[13][10] = null;  // Campo vacío
        MatrizAS[13][11] = null;  // Campo vacío
        MatrizAS[13][12] = null;  // Campo vacío
        MatrizAS[13][13] = null;  // Campo vacío
        MatrizAS[13][14] = null;  // Campo vacío
        MatrizAS[13][15] = null;  // Campo vacío
        MatrizAS[13][16] = null;  // Campo vacío
        MatrizAS[13][17] = null;  // Campo vacío
        MatrizAS[13][18] = null;  // Campo vacío
        MatrizAS[13][19] = null;  // Campo vacío
        MatrizAS[13][20] = null;  // Campo vacío
        MatrizAS[13][21] = null;  // Campo vacío
        MatrizAS[13][22] = null;  // Campo vacío
        MatrizAS[13][23] = null;  // Campo vacío
        MatrizAS[13][24] = null;  // Campo vacío
        MatrizAS[13][25] = null;  // Campo vacío
        MatrizAS[13][26] = null;  // Campo vacío
        MatrizAS[13][27] = ASE;  // EOF (AS Error)

        // Fila 14
        MatrizAS[14][0] = null;  // Campo vacío
        MatrizAS[14][1] = null;  // Campo vacío
        MatrizAS[14][2] = null;  // Campo vacío
        MatrizAS[14][3] = null;  // Campo vacío
        MatrizAS[14][4] = null;  // Campo vacío
        MatrizAS[14][5] = null;  // Campo vacío
        MatrizAS[14][6] = AS0;
        MatrizAS[14][7] = null;  // Campo vacío
        MatrizAS[14][8] = null;  // Campo vacío
        MatrizAS[14][9] = null;  // Campo vacío
        MatrizAS[14][10] = null;  // Campo vacío
        MatrizAS[14][11] = null;  // Campo vacío
        MatrizAS[14][12] = null;  // Campo vacío
        MatrizAS[14][13] = null;  // Campo vacío
        MatrizAS[14][14] = null;  // Campo vacío
        MatrizAS[14][15] = null;  // Campo vacío
        MatrizAS[14][16] = null;  // Campo vacío
        MatrizAS[14][17] = null;  // Campo vacío
        MatrizAS[14][18] = null;  // Campo vacío
        MatrizAS[14][19] = null;  // Campo vacío
        MatrizAS[14][20] = null;  // Campo vacío
        MatrizAS[14][21] = null;  // Campo vacío
        MatrizAS[14][22] = null;  // Campo vacío
        MatrizAS[14][23] = null;  // Campo vacío
        MatrizAS[14][24] = null;  // Campo vacío
        MatrizAS[14][25] = null;  // Campo vacío
        MatrizAS[14][26] = null;  // Campo vacío
        MatrizAS[14][27] = ASE;  // EOF (AS Error)

        // Fila 15
        MatrizAS[15][0] = AS2;
        MatrizAS[15][1] = AS2;
        MatrizAS[15][2] = AS2;
        MatrizAS[15][3] = AS2;
        MatrizAS[15][4] = AS2;
        MatrizAS[15][5] = AS2;
        MatrizAS[15][6] = AS2;
        MatrizAS[15][7] = AS2;
        MatrizAS[15][8] = AS2;
        MatrizAS[15][9] = AS2;
        MatrizAS[15][10] = AS2;
        MatrizAS[15][11] = AS2;
        MatrizAS[15][12] = AS2;
        MatrizAS[15][13] = AS2;
        MatrizAS[15][14] = AS2;
        MatrizAS[15][15] = AS2;
        MatrizAS[15][16] = AS2;
        MatrizAS[15][17] = AS2;
        MatrizAS[15][18] = AS2;
        MatrizAS[15][19] = AS2;
        MatrizAS[15][20] = AS2;
        MatrizAS[15][21] = AS2;
        MatrizAS[15][22] = AS2;
        MatrizAS[15][23] = AS2;
        MatrizAS[15][24] = AS2;
        MatrizAS[15][25] = ASE;  // AS Error
        MatrizAS[15][26] = AS2;
        MatrizAS[15][27] = ASE;  // AS Error

        // Fila 16
        MatrizAS[16][0] = AS7;
        MatrizAS[16][1] = AS7;
        MatrizAS[16][2] = AS7;
        MatrizAS[16][3] = AS7;
        MatrizAS[16][4] = AS7;
        MatrizAS[16][5] = AS7;
        MatrizAS[16][6] = AS7;
        MatrizAS[16][7] = AS7;
        MatrizAS[16][8] = AS7;
        MatrizAS[16][9] = AS7;
        MatrizAS[16][10] = AS7;
        MatrizAS[16][11] = AS7;
        MatrizAS[16][12] = AS7;
        MatrizAS[16][13] = AS7;
        MatrizAS[16][14] = AS7;
        MatrizAS[16][15] = AS7;
        MatrizAS[16][16] = AS7;
        MatrizAS[16][17] = AS7;
        MatrizAS[16][18] = AS7;
        MatrizAS[16][19] = AS7;
        MatrizAS[16][20] = AS7;
        MatrizAS[16][21] = AS7;
        MatrizAS[16][22] = AS7;
        MatrizAS[16][23] = AS7;
        MatrizAS[16][24] = AS7;
        MatrizAS[16][25] = AS7;
        MatrizAS[16][26] = AS7;
        MatrizAS[16][27] = AS7;


        // Ejemplo para fila 17
        MatrizAS[17][0] = AS7;
        MatrizAS[17][1] = AS7;
        MatrizAS[17][2] = AS7;
        MatrizAS[17][3] = AS7;
        MatrizAS[17][4] = AS7;
        MatrizAS[17][5] = AS7;
        MatrizAS[17][6] = AS7;
        MatrizAS[17][7] = AS7;
        MatrizAS[17][8] = AS7;
        MatrizAS[17][9] = AS7;
        MatrizAS[17][10] = AS7;
        MatrizAS[17][11] = AS7;
        MatrizAS[17][12] = AS7;
        MatrizAS[17][13] = AS7;
        MatrizAS[17][14] = AS7;
        MatrizAS[17][15] = AS7;
        MatrizAS[17][16] = AS7;
        MatrizAS[17][17] = AS7;
        MatrizAS[17][18] = AS7;
        MatrizAS[17][19] = AS7;
        MatrizAS[17][20] = AS7;
        MatrizAS[17][21] = AS7;
        MatrizAS[17][22] = AS7;
        MatrizAS[17][23] = AS7;
        MatrizAS[17][24] = AS7;
        MatrizAS[17][25] = AS7;
        MatrizAS[17][26] = AS7;
        MatrizAS[17][27] = AS7;
    }
    

    private AnalizadorLexico() {}
    public static AnalizadorLexico getInstance() { // Singleton
    	return unicaInstancia;
    }
    
    private int identificarSimbolo(char entrada) {
    	return 0;
    			
    			
    }
    
}
