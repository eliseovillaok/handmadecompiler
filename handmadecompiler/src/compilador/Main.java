package compilador;

import java.util.Scanner;
import manejo_archivos.*;

import manejo_archivos.DirectoryManager;

public class Main {
	public static void main(String[] args) {
		DirectoryManager.clearDirectory("src/salida"); // Borramos previo, para empezar clean.
		String filePath = seleccionarArchivo(args);

		Parser par = new Parser(false);
		par.main(filePath); // Ejecucion del parser

		TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();
		TablaSimbolos ts = TablaSimbolos.getInstance();
		FileHandler.appendToFile("salida_tabla_simbolos.txt", "Tabla de simbolos:" + ts.imprimir());
		FileHandler.appendToFile("salida_tabla_simbolos.txt", "Tabla de palabras reservadas:" + tpr.imprimir());
	}

	public static String seleccionarArchivo(String[] args) {
		if (args.length < 2) {
			System.out.println("Error: Debes proporcionar el path de los archivos como parámetro.");
			System.exit(1);
		}
		try (Scanner s = new Scanner(System.in)) {
			System.out.println("Compilar programa sin errores (1) -- Compilar programa con errores (2)");
			int input = s.nextInt();
			switch (input) {
				case 1:
					return args[0];
				case 2:
					return args[1];
				default:
					break;
			}
		}
		return args[0];
	}

}
