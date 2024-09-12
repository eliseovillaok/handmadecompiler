package acciones_semanticas;
import compilador.*;
import java.io.Reader;

//- Devolver a la entrada el ultimo caracter leido
//- Buscar lexema en la TS

public class AS7 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS7(); 
    private AS7() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	
    @Override
    public void ejecutar(StringBuilder simbolosReconocidos, char entrada) {
    	// TODO Auto-generated method stub
    	
    }

}