package acciones_semanticas;

import java.io.BufferedReader;

//lee siguiente token

public class AS0 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS0(); 
    private AS0() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	
	@Override
	public void ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion) {
		simbolosReconocidos.delete(0,simbolosReconocidos.length());
		
	}
    
}