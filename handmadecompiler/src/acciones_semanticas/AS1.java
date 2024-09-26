package acciones_semanticas;

import java.io.BufferedReader;

import compilador.Par;
import compilador.Token;

/*  - Inicializar string
    - Agregar Caracter
*/

public class AS1 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS1(); 
    private AS1() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    
	public Par<Integer, Token> ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea) {
		if (simbolosReconocidos.length()==0) {
			simbolosReconocidos.append(entrada);
		} else{
			System.out.println("Error: AS1");
		}
		return null;
	}
}
