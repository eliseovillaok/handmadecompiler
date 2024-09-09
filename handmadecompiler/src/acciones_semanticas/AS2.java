package acciones_semanticas;
import compilador.*;
import java.io.Reader;

//lee el siguiente char y lo agrega al string token

public class AS2 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS2(); 
    private AS2() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	@Override
	public void ejecutar(StringBuilder simbolosReconocidos, char entrada) {
		simbolosReconocidos.append(entrada);
	}
}

