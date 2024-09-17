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
		if (simbolosReconocidos.length()==0) {
			simbolosReconocidos.append(entrada);
		} else{
			System.out.println("Error: AS1");
		}
	}
}
