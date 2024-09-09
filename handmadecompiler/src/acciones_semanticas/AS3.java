package acciones_semanticas;
import compilador.*;
import java.io.Reader;

/*  - Devolver a la entrada el último carácter leído
    - Buscar en la TS
        - Si está,
            - Si es PR, devolver la Palabra Reservada
            - Si no, Devolver ID + Punt TS + *Tipo.*
        - Si no está,
            - Alta en la TS
            - Devolver ID + Punt TS + *Tipo.* verifica su longitud y envía un *warning* si la supera */

public class AS3 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS3(); 
    private AS3() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	/*@Override
    public int ejecutar(Reader lector, StringBuilder token) {
        token.deleteCharAt(token.length() - 1);
        return AccionSemantica.TOKEN_ACTIVO;
    }*/
}