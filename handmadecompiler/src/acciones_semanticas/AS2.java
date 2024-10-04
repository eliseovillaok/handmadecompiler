package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
import compilador.Par;
import compilador.Token;


//lee el siguiente char y lo agrega al string token

public class AS2 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS2(); 
	
    private AS2() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	@Override
	public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea,AnalizadorLexico lexico) {
		simbolosReconocidos.append(entrada);
		return new Par(-1, new Token(-1, null));
	}
}

