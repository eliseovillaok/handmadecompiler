package acciones_semanticas;

//Busca y devuelve o crea en la Tabla de Simbolos

public class AS6 implements AccionSemantica{
	private static volatile AccionSemantica unicaInstancia = new AS6(); 
    private AS6() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    
    @Override
    public void ejecutar(StringBuilder simbolosReconocidos, char entrada) {
    	// TODO Auto-generated method stub
    	
    }
}
