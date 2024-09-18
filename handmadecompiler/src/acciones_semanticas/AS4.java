package acciones_semanticas;

import java.io.BufferedReader;

// Concatena un 0 al final del string (hexa)

public class AS4 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS4();
	int numeroID = 370; 
    private AS4() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	@Override
   public void ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion) {
	simbolosReconocidos.append('0');
	AccionSemantica accion_semantica_5 = AS5.getInstance();
	accion_semantica_5.ejecutar(simbolosReconocidos, entrada, posicion);
   }
}