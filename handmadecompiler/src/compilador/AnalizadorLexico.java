package compilador;

import java.io.*;
import java.util.*;

public class analizadorLexico {    
    private static final String ARCHIVO_MATRIZ_ESTADOS = "src/matrizEstados.txt";
    private static final String ARCHIVO_MATRIZ_ACCIONES = "src/MatrizAccionesSemanticas.txt";

    public static final char TABULACION = '\t';
    public static final char BLANCO = ' ';
    public static final char NUEVA_LINEA = '\n';
    

    public static int getLineaActual() {
        return linea_actual;
    
    }
    public static void setLineaActual(int numero_linea) {
        linea_actual = numero_linea;
    }
