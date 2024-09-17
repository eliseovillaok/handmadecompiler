package acciones_semanticas;

import java.io.FileReader;

/*  - Inicializar string
    - Agregar Caracter
*/

public class AS1 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS1(); 
    private AS1() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    
	public void ejecutar(StringBuilder simbolosReconocidos, char entrada, FileReader posicion) {
		System.out.println(entrada);
		if (simbolosReconocidos == null) {
			simbolosReconocidos = new StringBuilder(100);
			simbolosReconocidos.append(entrada);
			System.out.println("contenido de simbolos " + simbolosReconocidos);
		}
	}
}
