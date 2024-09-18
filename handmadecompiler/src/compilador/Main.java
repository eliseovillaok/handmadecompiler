package compilador;

public class Main {
	public static void main(String[] args) {
		AnalizadorLexico lexico = AnalizadorLexico.getInstance();
		
		lexico.ejecutar();

		//mostrar tabla de simbolos
		System.out.println("\n Tabla de simbolos:");
		TablaSimbolos.getInstance().imprimir();

		//mostrar tabla de palabras reservadas
		//TablaPalabrasReservadas.getInstance().imprimirTabla();
	}
}
