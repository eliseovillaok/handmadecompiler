package acciones_semanticas;
import compilador.*;
import java.io.Reader;

// Verifica rango de constantes y la devuelve o crea en la tabla de simbolos

public class AS5 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS5(); 
    private AS5() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }

}
