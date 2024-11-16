package compilador;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//String filePath = seleccionarArchivo(args);

		Parser par = new Parser(false);
		par.main("src/programa_correcto.txt"); // Ejecucion del parser

		TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();
		TablaSimbolos ts = TablaSimbolos.getInstance();
		System.out.println("\n Tabla de simbolos:");
		ts.imprimir();
		System.out.println("\n Tabla de palabras reservadas:");
		tpr.imprimir();

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
