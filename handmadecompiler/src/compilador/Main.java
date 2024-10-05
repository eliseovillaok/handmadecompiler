package compilador;

public class Main {
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Error: Debes proporcionar el path del archivo como parámetro.");
            return;
        }
        String filePath = args[0]; // El path del archivo de entrada

		/*TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();
		TablaSimbolos ts = TablaSimbolos.getInstance();

		AnalizadorLexico lexico = AnalizadorLexico.getInstance(filePath);
		Sintactico sintactico = Sintactico.getInstance();
		sintactico.ejecutar(lexico);
*/
		
		Parser par = new Parser();
		par.main(filePath);
/*
		System.out.println("\n Tabla de simbolos:");
		ts.imprimir();
		System.out.println("\n Tabla de palabras reservadas:");
		tpr.imprimir();*/
	}
}
