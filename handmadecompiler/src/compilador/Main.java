package compilador;

public class Main {
	public static void main(String[] args) {
		AnalizadorLexico lexico = AnalizadorLexico.getInstance();
		TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();
		TablaSimbolos ts = TablaSimbolos.getInstance();
		//sintactico.ejecutar();

		//mostrar tabla de simbolos
		System.out.println("\n Tabla de simbolos:");
		ts.imprimir();
		System.out.println("\n Tabla de palabras reservadas:");
		//mostrar tabla de palabras reservadas
		tpr.imprimir();
	}
}
