package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
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
	public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea,AnalizadorLexico lexico) {
		simbolosReconocidos.delete(0,simbolosReconocidos.length());
		return new Par(0, new Token(0, null));
		
	}
    
}