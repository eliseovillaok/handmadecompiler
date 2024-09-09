package acciones_semanticas;
import compilador.*;
import java.io.Reader;


//lee siguiente token

public class AS0 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS0(); 
    private AS0() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	
	@Override
	public void ejecutar(StringBuilder simbolosReconocidos, char entrada) {
		// TODO Auto-generated method stub
		
	}
    
}