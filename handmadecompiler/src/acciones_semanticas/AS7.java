package acciones_semanticas;
import compilador.*;
import java.io.Reader;

//Inicializar un token que pueda contener hasta un char(1 Byte para operadores simples)

public class AS7 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS7(); 
    private AS7() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	/*@Override
    public int ejecutar(Reader lector, StringBuilder token) {
        token.delete(0, token.length());
        token.append((char)leerSiguienteCaracter(lector));
        return AccionSemantica.TOKEN_ACTIVO;
    }*/
}