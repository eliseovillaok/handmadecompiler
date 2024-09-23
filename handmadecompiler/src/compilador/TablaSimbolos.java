package compilador;


public class TablaSimbolos extends Tabla{ //
	private static volatile TablaSimbolos unicaInstancia = new TablaSimbolos(); 
    private TablaSimbolos() {}
    
    public static TablaSimbolos getInstance() { // Singleton
    	return unicaInstancia;
    }
	
}
