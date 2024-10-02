package compilador;

import java.io.*;
import acciones_semanticas.*;

public class AnalizadorLexico {    
	private static volatile AnalizadorLexico unicaInstancia;
	private static BufferedReader reader;
    private int estadoActual = 0;
    private int entrada;
    private String pathPrograma;
    private final int[][] MATRIZ_TRANCISION_ESTADOS = { //-1 representa fin de cadena, -2 representa error
    
    		/*E0*/ {1, 2, -2, 9, 9, 9, 13, 12, 10, 10, 9, 12, 9, 9, 9, 9, 9, -2, 7, 1, 1, 1, 16, -2, 0, 0, -1, -2},
    		/*E1*/ {1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, -2, -2, -1, -1, -1, 18},
    		/*E2*/ {-2, 2, -2, -1, -1, -1, -1, -2, -1, -1, -1, -1, -2, -1, -1, 3, -1, -2, 2, -2, -2, -2, -1, -1, -1, -1, -1, -2},
    		/*E3*/ {-2, 4, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 4, -2, -2, -2, -2, -2, -2, -2, -2, -2},
       		/*E4*/ {-2, 4, -2, -1, -1, -1, -1, -2, -1, -1, -1, -1, -2, -1, -1, -2, -1, -2, 4, -2, -2, 5, -1, -2, -1, -1, -1, -2},
    		/*E5*/ {-2, 6, -2, 6, 6, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 6, -2, -2, -2, -2, -2, -2, -2, -2, -2},
    		/*E6*/ {-2, 6, -2, -1, -1, -1, -1, -2, -1, -1, -1, -1, -2, -1, -1, -2, -1, -2, 6, -2, -2, -2, -1, -1, -1, -1, -1, -2},
    		/*E7*/ {-2, 2, -2, -1, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1, -1, 3, -1, -2, 2, 8, -2, -2, -1, -1, -1, -1, -1, -2},
    		/*E8*/ {-2, 8, -2, -1, -1, -1, -1, -2, -1, -1, -1, -1, -2, -1, -1, -2, -1, -1, 8, -2, 8, -2, -1, -1, -1, -1, -1, -2},
    		/*E9*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    		/*E10*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    		/*E11*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    		/*E12*/ {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 11, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
    		/*E13*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    		/*E14*/ {14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 14, 14, 14, 14, 14, 14, 14, 14, -2, 14},
    		/*E15*/ {14, 14, 14, 14, 14, 14, 0, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 14, 14, 14, 14, 14, 14, 14, 14, -2, 14},
    		/*E16*/ {16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, -2, 16, -2, 16},
    		/*E17*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            /*E18*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    };
    private AccionSemantica[][] MatrizAS;
    private int numeroLinea = 1;
    private void loadSAMatrix() {
        // Crear acciones semánticas
        AccionSemantica accion0 = AS0.getInstance();
        AccionSemantica accion1 = AS1.getInstance();
        AccionSemantica accion2 = AS2.getInstance();
        AccionSemantica accion3 = AS3.getInstance();
        AccionSemantica accion4 = AS4.getInstance();
        AccionSemantica accion5 = AS5.getInstance();
        AccionSemantica accion6 = AS6.getInstance();
        AccionSemantica accion7 = AS7.getInstance();
        AccionSemantica accionError = ASE.getInstance();
        
        // Inicializar matriz de acciones semánticas
        MatrizAS = new AccionSemantica[19][28];
    
        // Simbolo: @
    	MatrizAS[0][27] = accionError;
    	MatrizAS[1][27] = accion2;
    	MatrizAS[2][27] = accionError;
    	MatrizAS[3][27] = accionError;
    	MatrizAS[4][27] = accionError;
    	MatrizAS[5][27] = accionError;
    	MatrizAS[6][27] = accionError;
    	MatrizAS[7][27] = accionError;
    	MatrizAS[8][27] = accionError;
    	MatrizAS[9][27] = accion6;
    	MatrizAS[10][27] = accion6;
    	MatrizAS[11][27] = accion6;
    	MatrizAS[12][27] = accionError;
    	MatrizAS[13][27] = accion6;
    	MatrizAS[14][27] = null;
    	MatrizAS[15][27] = null;
    	MatrizAS[16][27] = accion2;
    	MatrizAS[17][27] = accion7;
        MatrizAS[18][27] = accion3;

        // Fila 0
    	MatrizAS[0][0] = accion1;
        MatrizAS[0][1] = accion1;
        MatrizAS[0][2] = accionError;
        MatrizAS[0][3] = accion1;
        MatrizAS[0][4] = accion1;
        MatrizAS[0][5] = accion1;
        MatrizAS[0][6] = accion1;
        MatrizAS[0][7] = accion1;
        MatrizAS[0][8] = accion1;
        MatrizAS[0][9] = accion1;
        MatrizAS[0][10] = accion1;
        MatrizAS[0][11] = accion1;
        MatrizAS[0][12] = accion1;
        MatrizAS[0][13] = accion1;
        MatrizAS[0][14] = accion1;
        MatrizAS[0][15] = accion1;
        MatrizAS[0][16] = accion1;
        MatrizAS[0][17] = accionError;
        MatrizAS[0][18] = accion1;
        MatrizAS[0][19] = accion1;
        MatrizAS[0][20] = accion1;
        MatrizAS[0][21] = accion1;
        MatrizAS[0][22] = accion1;
        MatrizAS[0][23] = accionError;
        MatrizAS[0][24] = accion0;
        MatrizAS[0][25] = accion0;
        MatrizAS[0][26] = accion0;
    
        // Fila 1
        MatrizAS[1][0] = accion2;
        MatrizAS[1][1] = accion2;
        MatrizAS[1][2] = accion2;
        MatrizAS[1][3] = accion3;
        MatrizAS[1][4] = accion3;
        MatrizAS[1][5] = accion3;
        MatrizAS[1][6] = accion3;
        MatrizAS[1][7] = accion3;
        MatrizAS[1][8] = accion3;
        MatrizAS[1][9] = accion3;
        MatrizAS[1][10] = accion3;
        MatrizAS[1][11] = accion3;
        MatrizAS[1][12] = accion3;
        MatrizAS[1][13] = accion3;
        MatrizAS[1][14] = accion3;
        MatrizAS[1][15] = accion3;
        MatrizAS[1][16] = accion3;
        MatrizAS[1][17] = accion3;
        MatrizAS[1][18] = accion2;
        MatrizAS[1][19] = accion2;
        MatrizAS[1][20] = accion2;
        MatrizAS[1][21] = accion2;
        MatrizAS[1][22] = accionError;
        MatrizAS[1][23] = accionError;
        MatrizAS[1][24] = accion3;
        MatrizAS[1][25] = accion3;
        MatrizAS[1][26] = accion3;
    
        // Fila 2
        MatrizAS[2][0] = accionError;
        MatrizAS[2][1] = accion2;
        MatrizAS[2][2] = accionError;
        MatrizAS[2][3] = accion5;
        MatrizAS[2][4] = accion5;
        MatrizAS[2][5] = accion5;
        MatrizAS[2][6] = accion5;
        MatrizAS[2][7] = accionError;
        MatrizAS[2][8] = accion5;
        MatrizAS[2][9] = accion5;
        MatrizAS[2][10] = accion5;
        MatrizAS[2][11] = accion5;
        MatrizAS[2][12] = accionError;
        MatrizAS[2][13] = accion5;
        MatrizAS[2][14] = accion5;
        MatrizAS[2][15] = accion2;
        MatrizAS[2][16] = accion5;
        MatrizAS[2][17] = accionError;
        MatrizAS[2][18] = accion2;
        MatrizAS[2][19] = accionError;
        MatrizAS[2][20] = accionError;
        MatrizAS[2][21] = accionError;
        MatrizAS[2][22] = accion5;
        MatrizAS[2][23] = accion5;
        MatrizAS[2][24] = accion5;
        MatrizAS[2][25] = accion5;
        MatrizAS[2][26] = accion5;

        // Fila 3
        MatrizAS[3][0] = accionError;
        MatrizAS[3][1] = accion2;
        MatrizAS[3][2] = accionError;
        MatrizAS[3][3] = accionError;
        MatrizAS[3][4] = accionError;
        MatrizAS[3][5] = accionError;
        MatrizAS[3][6] = accionError;
        MatrizAS[3][7] = accionError;
        MatrizAS[3][8] = accionError;
        MatrizAS[3][9] = accionError;
        MatrizAS[3][10] = accionError;
        MatrizAS[3][11] = accionError;
        MatrizAS[3][12] = accionError;
        MatrizAS[3][13] = accionError;
        MatrizAS[3][14] = accionError;
        MatrizAS[3][15] = accionError;
        MatrizAS[3][16] = accionError;
        MatrizAS[3][17] = accionError;
        MatrizAS[3][18] = accion2;
        MatrizAS[3][19] = accionError;
        MatrizAS[3][20] = accionError;
        MatrizAS[3][21] = accionError;
        MatrizAS[3][22] = accionError;
        MatrizAS[3][23] = accionError;
        MatrizAS[3][24] = accionError;
        MatrizAS[3][25] = accionError;
        MatrizAS[3][26] = accionError;

        // Fila 4
        MatrizAS[4][0] = accionError;
        MatrizAS[4][1] = accion2;
        MatrizAS[4][2] = accionError;
        MatrizAS[4][3] = accion5;
        MatrizAS[4][4] = accion5;
        MatrizAS[4][5] = accion5;
        MatrizAS[4][6] = accion5;
        MatrizAS[4][7] = accionError;
        MatrizAS[4][8] = accion5;
        MatrizAS[4][9] = accion5;
        MatrizAS[4][10] = accion5;
        MatrizAS[4][11] = accion5;
        MatrizAS[4][12] = accionError;
        MatrizAS[4][13] = accion5;
        MatrizAS[4][14] = accion5;
        MatrizAS[4][15] = accionError;
        MatrizAS[4][16] = accion5;
        MatrizAS[4][17] = accionError;
        MatrizAS[4][18] = accion2;
        MatrizAS[4][19] = accionError;
        MatrizAS[4][20] = accionError;
        MatrizAS[4][21] = accion2;
        MatrizAS[4][22] = accion5;
        MatrizAS[4][23] = accionError;
        MatrizAS[4][24] = accion5;
        MatrizAS[4][25] = accion5;
        MatrizAS[4][26] = accion5;

        // Fila 5
        MatrizAS[5][0] = accionError;
        MatrizAS[5][1] = accion2;
        MatrizAS[5][2] = accionError;
        MatrizAS[5][3] = accion2;
        MatrizAS[5][4] = accion2;
        MatrizAS[5][5] = accionError;
        MatrizAS[5][6] = accionError;
        MatrizAS[5][7] = accionError;
        MatrizAS[5][8] = accionError;
        MatrizAS[5][9] = accionError;
        MatrizAS[5][10] = accionError;
        MatrizAS[5][11] = accionError;
        MatrizAS[5][12] = accionError;
        MatrizAS[5][13] = accionError;
        MatrizAS[5][14] = accionError;
        MatrizAS[5][15] = accionError;
        MatrizAS[5][16] = accionError;
        MatrizAS[5][17] = accionError;
        MatrizAS[5][18] = accion2;
        MatrizAS[5][19] = accionError;
        MatrizAS[5][20] = accionError;
        MatrizAS[5][21] = accionError;
        MatrizAS[5][22] = accionError;
        MatrizAS[5][23] = accionError;
        MatrizAS[5][24] = accionError;
        MatrizAS[5][25] = accionError;
        MatrizAS[5][26] = accionError;

        // Fila 6
        MatrizAS[6][0] = accionError;
        MatrizAS[6][1] = accion2;
        MatrizAS[6][2] = accionError;
        MatrizAS[6][3] = accion5;
        MatrizAS[6][4] = accion5;
        MatrizAS[6][5] = accion5;
        MatrizAS[6][6] = accion5;
        MatrizAS[6][7] = accionError;
        MatrizAS[6][8] = accion5;
        MatrizAS[6][9] = accion5;
        MatrizAS[6][10] = accion5;
        MatrizAS[6][11] = accion5;
        MatrizAS[6][12] = accionError;
        MatrizAS[6][13] = accion5;
        MatrizAS[6][14] = accion5;
        MatrizAS[6][15] = accionError;
        MatrizAS[6][16] = accion5;
        MatrizAS[6][17] = accionError;
        MatrizAS[6][18] = accion2;
        MatrizAS[6][19] = accionError;
        MatrizAS[6][20] = accionError;
        MatrizAS[6][21] = accionError;
        MatrizAS[6][22] = accion5;
        MatrizAS[6][23] = accion5;
        MatrizAS[6][24] = accion5;
        MatrizAS[6][25] = accion5;
        MatrizAS[6][26] = accion5;

        // Fila 7
        MatrizAS[7][0] = accionError;
        MatrizAS[7][1] = accion2;
        MatrizAS[7][2] = accionError;
        MatrizAS[7][3] = accion5;
        MatrizAS[7][4] = accion5;
        MatrizAS[7][5] = accion5;
        MatrizAS[7][6] = accion5;
        MatrizAS[7][7] = accionError;
        MatrizAS[7][8] = accion5;
        MatrizAS[7][9] = accion5;
        MatrizAS[7][10] = accion5;
        MatrizAS[7][11] = accion5;
        MatrizAS[7][12] = accion5;
        MatrizAS[7][13] = accion5;
        MatrizAS[7][14] = accion5;
        MatrizAS[7][15] = accion2;
        MatrizAS[7][16] = accion5;
        MatrizAS[7][17] = accionError;
        MatrizAS[7][18] = accion5;
        MatrizAS[7][19] = accion2;
        MatrizAS[7][20] = accionError;
        MatrizAS[7][21] = accionError;
        MatrizAS[7][22] = accion5;
        MatrizAS[7][23] = accion5;
        MatrizAS[7][24] = accion5;
        MatrizAS[7][25] = accion5;
        MatrizAS[7][26] = accion5;

        // Fila 8
        MatrizAS[8][0] = accionError;
        MatrizAS[8][1] = accion2;
        MatrizAS[8][2] = accionError;
        MatrizAS[8][3] = accion4;
        MatrizAS[8][4] = accion4;
        MatrizAS[8][5] = accion4;
        MatrizAS[8][6] = accion4;
        MatrizAS[8][7] = accionError;
        MatrizAS[8][8] = accion4;
        MatrizAS[8][9] = accion4;
        MatrizAS[8][10] = accion4;
        MatrizAS[8][11] = accion4;
        MatrizAS[8][12] = accionError;
        MatrizAS[8][13] = accion4;
        MatrizAS[8][14] = accion4;
        MatrizAS[8][15] = accionError;
        MatrizAS[8][16] = accion4;
        MatrizAS[8][17] = accion4;
        MatrizAS[8][18] = accion2;
        MatrizAS[8][19] = accionError;
        MatrizAS[8][20] = accion2;
        MatrizAS[8][21] = accionError;
        MatrizAS[8][22] = accion4;
        MatrizAS[8][23] = accion4;
        MatrizAS[8][24] = accion4;
        MatrizAS[8][25] = accion4;
        MatrizAS[8][26] = accion4;

        // Fila 9
        MatrizAS[9][0] = accion6;
        MatrizAS[9][1] = accion6;
        MatrizAS[9][2] = accion6;
        MatrizAS[9][3] = accion6;
        MatrizAS[9][4] = accion6;
        MatrizAS[9][5] = accion6;
        MatrizAS[9][6] = accion6;
        MatrizAS[9][7] = accion6;
        MatrizAS[9][8] = accion6;
        MatrizAS[9][9] = accion6;
        MatrizAS[9][10] = accion6;
        MatrizAS[9][11] = accion6;
        MatrizAS[9][12] = accion6;
        MatrizAS[9][13] = accion6;
        MatrizAS[9][14] = accion6;
        MatrizAS[9][15] = accion6;
        MatrizAS[9][16] = accion6;
        MatrizAS[9][17] = accion6;
        MatrizAS[9][18] = accion6;
        MatrizAS[9][19] = accion6;
        MatrizAS[9][20] = accion6;
        MatrizAS[9][21] = accion6;
        MatrizAS[9][22] = accion6;
        MatrizAS[9][23] = accion6;
        MatrizAS[9][24] = accion6;
        MatrizAS[9][25] = accion6;
        MatrizAS[9][26] = accion6;

        // Fila 10
        MatrizAS[10][0] = accion6;
        MatrizAS[10][1] = accion6;
        MatrizAS[10][2] = accion6;
        MatrizAS[10][3] = accion6;
        MatrizAS[10][4] = accion6;
        MatrizAS[10][5] = accion6;
        MatrizAS[10][6] = accion6;
        MatrizAS[10][7] = accion6;
        MatrizAS[10][8] = accion6;
        MatrizAS[10][9] = accion6;
        MatrizAS[10][10] = accion2;
        MatrizAS[10][11] = accion6;
        MatrizAS[10][12] = accion6;
        MatrizAS[10][13] = accion6;
        MatrizAS[10][14] = accion6;
        MatrizAS[10][15] = accion6;
        MatrizAS[10][16] = accion6;
        MatrizAS[10][17] = accion6;
        MatrizAS[10][18] = accion6;
        MatrizAS[10][19] = accion6;
        MatrizAS[10][20] = accion6;
        MatrizAS[10][21] = accion6;
        MatrizAS[10][22] = accion6;
        MatrizAS[10][23] = accion6;
        MatrizAS[10][24] = accion6;
        MatrizAS[10][25] = accion6;
        MatrizAS[10][26] = accion6;

        // Fila 11
        MatrizAS[11][0] = accion6;
        MatrizAS[11][1] = accion6;
        MatrizAS[11][2] = accion6;
        MatrizAS[11][3] = accion6;
        MatrizAS[11][4] = accion6;
        MatrizAS[11][5] = accion6;
        MatrizAS[11][6] = accion6;
        MatrizAS[11][7] = accion6;
        MatrizAS[11][8] = accion6;
        MatrizAS[11][9] = accion6;
        MatrizAS[11][10] = accion6;
        MatrizAS[11][11] = accion6;
        MatrizAS[11][12] = accion6;
        MatrizAS[11][13] = accion6;
        MatrizAS[11][14] = accion6;
        MatrizAS[11][15] = accion6;
        MatrizAS[11][16] = accion6;
        MatrizAS[11][17] = accion6;
        MatrizAS[11][18] = accion6;
        MatrizAS[11][19] = accion6;
        MatrizAS[11][20] = accion6;
        MatrizAS[11][21] = accion6;
        MatrizAS[11][22] = accion6;
        MatrizAS[11][23] = accion6;
        MatrizAS[11][24] = accion6;
        MatrizAS[11][25] = accion6;
        MatrizAS[11][26] = accion6;

        // Fila 12
        MatrizAS[12][0] = accionError;
        MatrizAS[12][1] = accionError;
        MatrizAS[12][2] = accionError;
        MatrizAS[12][3] = accionError;
        MatrizAS[12][4] = accionError;
        MatrizAS[12][5] = accionError;
        MatrizAS[12][6] = accionError;
        MatrizAS[12][7] = accionError;
        MatrizAS[12][8] = accionError;
        MatrizAS[12][9] = accionError;
        MatrizAS[12][10] = accion2;
        MatrizAS[12][11] = accionError;
        MatrizAS[12][12] = accionError;
        MatrizAS[12][13] = accionError;
        MatrizAS[12][14] = accionError;
        MatrizAS[12][15] = accionError;
        MatrizAS[12][16] = accionError;
        MatrizAS[12][17] = accionError;
        MatrizAS[12][18] = accionError;
        MatrizAS[12][19] = accionError;
        MatrizAS[12][20] = accionError;
        MatrizAS[12][21] = accionError;
        MatrizAS[12][22] = accionError;
        MatrizAS[12][23] = accionError;
        MatrizAS[12][24] = accionError;
        MatrizAS[12][25] = accionError;
        MatrizAS[12][26] = accionError;

        // Fila 13
        MatrizAS[13][0] = accion6;
        MatrizAS[13][1] = accion6;
        MatrizAS[13][2] = accion6;
        MatrizAS[13][3] = accion6;
        MatrizAS[13][4] = accion6;
        MatrizAS[13][5] = accion6;
        MatrizAS[13][6] = accion6;
        MatrizAS[13][7] = accion6;
        MatrizAS[13][8] = accion6;
        MatrizAS[13][9] = accion6;
        MatrizAS[13][10] = accion6;
        MatrizAS[13][11] = accion6;
        MatrizAS[13][12] = accion6;
        MatrizAS[13][13] = accion6;
        MatrizAS[13][14] = accion6;
        MatrizAS[13][15] = accion6;
        MatrizAS[13][16] = accion6;
        MatrizAS[13][17] = null;  // Campo vacío
        MatrizAS[13][18] = accion6;
        MatrizAS[13][19] = accion6;
        MatrizAS[13][20] = accion6;
        MatrizAS[13][21] = accion6;
        MatrizAS[13][22] = accion6;
        MatrizAS[13][23] = accion6;
        MatrizAS[13][24] = accion6;
        MatrizAS[13][25] = accion6;
        MatrizAS[13][26] = accion6;

        // Fila 14
        MatrizAS[14][0] = null;  // Campo vacío
        MatrizAS[14][1] = null;  // Campo vacío
        MatrizAS[14][2] = null;  // Campo vacío
        MatrizAS[14][3] = null;  // Campo vacío
        MatrizAS[14][4] = null;  // Campo vacío
        MatrizAS[14][5] = null;  // Campo vacío
        MatrizAS[14][6] = null;  // Campo vacío
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
        MatrizAS[14][26] = accionError;  // EOF (AS Error)

        // Fila 15
        MatrizAS[15][0] = null;  // Campo vacío
        MatrizAS[15][1] = null;  // Campo vacío
        MatrizAS[15][2] = null;  // Campo vacío
        MatrizAS[15][3] = null;  // Campo vacío
        MatrizAS[15][4] = null;  // Campo vacío
        MatrizAS[15][5] = null;  // Campo vacío
        MatrizAS[15][6] = accion0;
        MatrizAS[15][7] = null;  // Campo vacío
        MatrizAS[15][8] = null;  // Campo vacío
        MatrizAS[15][9] = null;  // Campo vacío
        MatrizAS[15][10] = null;  // Campo vacío
        MatrizAS[15][11] = null;  // Campo vacío
        MatrizAS[15][12] = null;  // Campo vacío
        MatrizAS[15][13] = null;  // Campo vacío
        MatrizAS[15][14] = null;  // Campo vacío
        MatrizAS[15][15] = null;  // Campo vacío
        MatrizAS[15][16] = null;  // Campo vacío
        MatrizAS[15][17] = null;  // Campo vacío
        MatrizAS[15][18] = null;  // Campo vacío
        MatrizAS[15][19] = null;  // Campo vacío
        MatrizAS[15][20] = null;  // Campo vacío
        MatrizAS[15][21] = null;  // Campo vacío
        MatrizAS[15][22] = null;  // Campo vacío
        MatrizAS[15][23] = null;  // Campo vacío
        MatrizAS[15][24] = null;  // Campo vacío
        MatrizAS[15][25] = null;  // Campo vacío
        MatrizAS[15][26] = accionError;  // EOF (AS Error)

        // Fila 16
        MatrizAS[16][0] = accion2;
        MatrizAS[16][1] = accion2;
        MatrizAS[16][2] = accion2;
        MatrizAS[16][3] = accion2;
        MatrizAS[16][4] = accion2;
        MatrizAS[16][5] = accion2;
        MatrizAS[16][6] = accion2;
        MatrizAS[16][7] = accion2;
        MatrizAS[16][8] = accion2;
        MatrizAS[16][9] = accion2;
        MatrizAS[16][10] = accion2;
        MatrizAS[16][11] = accion2;
        MatrizAS[16][12] = accion2;
        MatrizAS[16][13] = accion2;
        MatrizAS[16][14] = accion2;
        MatrizAS[16][15] = accion2;
        MatrizAS[16][16] = accion2;
        MatrizAS[16][17] = accion2;
        MatrizAS[16][18] = accion2;
        MatrizAS[16][19] = accion2;
        MatrizAS[16][20] = accion2;
        MatrizAS[16][21] = accion2;
        MatrizAS[16][22] = accion2;
        MatrizAS[16][23] = accion2;
        MatrizAS[16][24] = accionError;  // AS Error
        MatrizAS[16][25] = accion2;
        MatrizAS[16][26] = accionError;  // AS Error

        // Fila 17
        MatrizAS[17][0] = accion7;
        MatrizAS[17][1] = accion7;
        MatrizAS[17][2] = accion7;
        MatrizAS[17][3] = accion7;
        MatrizAS[17][4] = accion7;
        MatrizAS[17][5] = accion7;
        MatrizAS[17][6] = accion7;
        MatrizAS[17][7] = accion7;
        MatrizAS[17][8] = accion7;
        MatrizAS[17][9] = accion7;
        MatrizAS[17][10] = accion7;
        MatrizAS[17][11] = accion7;
        MatrizAS[17][12] = accion7;
        MatrizAS[17][13] = accion7;
        MatrizAS[17][14] = accion7;
        MatrizAS[17][15] = accion7;
        MatrizAS[17][16] = accion7;
        MatrizAS[17][17] = accion7;
        MatrizAS[17][18] = accion7;
        MatrizAS[17][19] = accion7;
        MatrizAS[17][20] = accion7;
        MatrizAS[17][21] = accion7;
        MatrizAS[17][22] = accion7;
        MatrizAS[17][23] = accion7;
        MatrizAS[17][24] = accion7;
        MatrizAS[17][25] = accion7;
        MatrizAS[17][26] = accion7;

        // Fila 18
        MatrizAS[18][0] = accion3;
        MatrizAS[18][1] = accion3;
        MatrizAS[18][2] = accion3;
        MatrizAS[18][3] = accion3;
        MatrizAS[18][4] = accion3;
        MatrizAS[18][5] = accion3;
        MatrizAS[18][6] = accion3;
        MatrizAS[18][7] = accion3;
        MatrizAS[18][8] = accion3;
        MatrizAS[18][9] = accion3;
        MatrizAS[18][10] = accion3;
        MatrizAS[18][11] = accion3;
        MatrizAS[18][12] = accion3;
        MatrizAS[18][13] = accion3;
        MatrizAS[18][14] = accion3;
        MatrizAS[18][15] = accion3;
        MatrizAS[18][16] = accion3;
        MatrizAS[18][17] = accion3;
        MatrizAS[18][18] = accion3;
        MatrizAS[18][19] = accion3;
        MatrizAS[18][20] = accion3;
        MatrizAS[18][21] = accion3;
        MatrizAS[18][22] = accion3;
        MatrizAS[18][23] = accion3;
        MatrizAS[18][24] = accion3;
        MatrizAS[18][25] = accion3;
        MatrizAS[18][26] = accion3;
    }
    
    private AnalizadorLexico(String filePath) {
    	this.loadSAMatrix();
        this.pathPrograma = filePath;
    	
        // Mostrar Tabla de palabras reservadas por pantalla
		TablaPalabrasReservadas tablaPR = TablaPalabrasReservadas.getInstance();
        // Pre carga de palabras reservadas
		try {
			tablaPR.cargarDesdeArchivo();
		} catch (Exception e) {
			System.out.println("Error al cargar tabla de palabras reservadas");
		}
        //
    }
    
    public static AnalizadorLexico getInstance(String filePath) { // Singleton
        if (unicaInstancia == null) {
            synchronized (AnalizadorLexico.class) {
                if (unicaInstancia == null) {
                	unicaInstancia = new AnalizadorLexico(filePath);
                }
            }
        }
        return unicaInstancia;
    }

    private BufferedReader getFileReader() {
        try {
            if (reader == null) {
                // Asignar directamente al atributo de la clase, sin declarar nuevamente
            	reader = new BufferedReader(new FileReader(this.pathPrograma));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return reader;
    }
    
    private int getProximoSimbolo() {
    	int proximoSimbolo = -1;
    	try {
    		proximoSimbolo = this.getFileReader().read();
    	} catch (IOException e) {
    		e.printStackTrace();
    		System.exit(1);
    	}
    	return proximoSimbolo;
    }
    
    private int identificarSimbolo(int entrada) {
        if ((entrada >= 71 && entrada <= 90) || (entrada >= 97 && entrada <= 114) || (entrada >= 116 && entrada <= 119) || (entrada >= 121 && entrada <= 122)) 
            return 0;
         else if (entrada >= 49 && entrada <= 57)  // [0-9]
            return 1;
         else if (entrada == 95)  // _ 
        	return 2;
         else if (entrada == 43)  // +
            return 3;
         else if (entrada == 45) // -
            return 4;
         else if (entrada == 42)  // *
            return 5;
         else if (entrada == 47)  // / 
        	return 6;
         else if (entrada == 58)  // :
            return 7;
         else if (entrada == 62)    // >
            return 8;
         else if (entrada == 60)    // <
            return 9;
         else if (entrada == 61)  // =
            return 10;
         else if (entrada == 33)  // !
            return 11;
         else if (entrada == 40)  // (
             return 12;
         else if (entrada == 41)  // )
             return 13;
         else if (entrada == 44)  // ,
             return 14;
         else if (entrada == 46)  // .
             return 15;
         else if (entrada == 59)  // ;
             return 16;
         else if (entrada == 35)  // #
             return 17;
         else if (entrada == 48)  // 0
             return 18;
         else if (entrada == 120)  // x
             return 19;
         else if (entrada >= 65 && entrada <= 70)  // [A-F]
             return 20;
         else if (entrada == 115)  // s 
             return 21;
         else if (entrada == 123)  // { 
             return 22;
         else if (entrada == 125)  // } 
             return 23;
         else if (entrada == 10)  // Salto de linea
             return 24;
         else if (entrada == 9 || entrada == 32 || entrada == 13)  // \t ' ' enter
            return 25;
         else if (entrada == -1) // $ end of file
        	 return 26;
         else if (entrada == 64) //@
        	 return 27;
        	 
        return 26; // Si no está en el alfabeto, lo tomo como fin del archivo (FIXEAR)
    }
    
    private void reiniciarEstado() {
    	this.estadoActual = 0;
    }
    
    // public void ejecutar(){
    //     int valorSimbolo = -1;
    //     while ((valorSimbolo = this.getProximoToken()) != -1);
    //     System.out.println("Se ha alcanzado el final del archivo.");
    // }


    public Par<Integer, Token> getProximoToken() {
    	StringBuilder reconocido = new StringBuilder(100); // Empezamos sin reconocer nada...
    	AccionSemantica as;
    	int simbolo = 0;
        char entrada_caracter;
        Par<Integer, Token> salida = null;
    	
    	while (estadoActual >= 0) { // Si no estamos en F o en estado de error
            // Marcar el archivo para poder volver atras
            try {
                reader.mark(1);
            } catch (Exception e) {
            }
            simbolo = getProximoSimbolo(); // ASCII
            entrada = identificarSimbolo(simbolo); // Columna mapeada con el ASCII
            entrada_caracter = (char) simbolo; // caracter ASCII
            //System.out.println("["+estadoActual+"]["+entrada_caracter+"]"+" ASCII:"+simbolo+" Numero de linea: " + numeroLinea);

            if ((simbolo == 10 || simbolo == 13) && (estadoActual == 0 || estadoActual == 14 || estadoActual == 15))
            	numeroLinea++;
            
    		as = MatrizAS[estadoActual][entrada]; // Accion semantica o null
    		estadoActual = MATRIZ_TRANCISION_ESTADOS[estadoActual][entrada]; // Prox estado
    		if (as != null)
                salida = as.ejecutar(reconocido, entrada_caracter,reader,numeroLinea);
            
            if (simbolo == -1) {
                //System.out.println("Fin de archivo");
                return new Par<Integer, Token>(-2, null);
            }
    	}
    	
    	this.reiniciarEstado();
    	return salida;
    }

    public int yylex() {
        Integer token = getProximoToken().getToken();  
        if (token != null) {  
            return token;
        } 
        return -1; // error
    }

    public Par<Integer, Token> retornar(Token token) {
        //System.out.println("Token retornado: " + token);
    	return new Par<Integer, Token>(token.getId(), token);
    }
}
