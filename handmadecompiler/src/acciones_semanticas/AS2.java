package acciones_semanticas;

import java.io.BufferedReader;


//lee el siguiente char y lo agrega al string token

public class AS2 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS2(); 
	
    private AS2() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	@Override
	public void ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea) {
		simbolosReconocidos.append(entrada);
	}
}

