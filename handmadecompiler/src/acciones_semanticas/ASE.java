package acciones_semanticas;

import java.io.BufferedReader;

import compilador.Par;
import compilador.Token;

public class ASE implements AccionSemantica {
    private static volatile AccionSemantica unicaInstancia = new ASE(); 
    
    private ASE() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    @Override
    public Par<Integer, Token> ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea) {
        // TODO Auto-generated method stub
        System.err.println("Error: Caracter no reconocido: " + entrada + " en la linea "+numeroLinea);
        return new Par<Integer,Token>(-1, new Token(-1, null));
    }
    
}
