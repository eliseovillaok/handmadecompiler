package compilador;
import java.io.*;
import java.util.*;

public class AnalizadorLexico {    
	private static volatile AnalizadorLexico unicaInstancia = new AnalizadorLexico(); 
    private final String ARCHIVO_MATRIZ_ESTADOS = "src/matrizEstados.txt";
    private final String ARCHIVO_MATRIZ_ACCIONES = "src/MatrizAccionesSemanticas.txt";

    private final char TABULACION = '\t';
    private final char BLANCO = ' ';
    private final char NUEVA_LINEA = '\n';
    private int estadoActual = 0;
    private char entrada;
    
    private AnalizadorLexico() {}
    public static AnalizadorLexico getInstance() { // Singleton
    	return unicaInstancia;
    }
    
    private int identificarSimbolo(char entrada) {
    	if Character.isDigit(entrada)
    		if entrada = '0'
    			
    			
    }
    
}
