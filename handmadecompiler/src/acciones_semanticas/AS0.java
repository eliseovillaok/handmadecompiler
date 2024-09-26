package acciones_semanticas;

import java.io.BufferedReader;

import compilador.Par;
import compilador.Token;

//lee siguiente token

public class AS0 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS0(); 
	
    private AS0() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	
	@Override
	public Par<Integer, Token> ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea) {
		simbolosReconocidos.delete(0,simbolosReconocidos.length());
		return new Par<Integer,Token>(-1, new Token(-1, null));
		
	}
    
}