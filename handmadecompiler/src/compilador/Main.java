package compilador;

public class Main {
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Error: Debes proporcionar el path del archivo como parámetro.");
            return;
        }
        String filePath = args[0]; // El path del archivo de entrada
		//pepe
		AnalizadorLexico lexico = AnalizadorLexico.getInstance();
		lexico.setPath(filePath);
		TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();
		TablaSimbolos ts = TablaSimbolos.getInstance();
		Sintactico sintactico = Sintactico.getInstance();
		sintactico.ejecutar();

		//mostrar tabla de simbolos
		System.out.println("\n Tabla de simbolos:");
		ts.imprimir();
		System.out.println("\n Tabla de palabras reservadas:");
		//mostrar tabla de palabras reservadas
		tpr.imprimir();
	}
}
