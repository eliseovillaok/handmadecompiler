package acciones_semanticas;
import compilador.*;
import java.io.Reader;

/*  - Inicializar string (se reserva 32 bits longitud permitida)
    - Chequear que sea digito
    - AS2 */

public class AS5 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS5(); 
    private AS5() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }

}
