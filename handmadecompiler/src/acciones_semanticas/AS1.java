package acciones_semanticas;

import java.io.BufferedReader;

/*  - Inicializar string
    - Agregar Caracter
*/

public class AS1 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS1(); 
    private AS1() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    
	public void ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea) {
		if (simbolosReconocidos.length()==0) {
			simbolosReconocidos.append(entrada);
		} else{
			System.out.println("Error: AS1");
		}
	}
}
