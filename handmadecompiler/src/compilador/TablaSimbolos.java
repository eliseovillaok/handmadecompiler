package compilador;


public class TablaSimbolos extends Tabla{ //
	private static volatile TablaSimbolos unicaInstancia = new TablaSimbolos(); 
    private TablaSimbolos() {}
    
    public static TablaSimbolos getInstance() { // Singleton
    	return unicaInstancia;
    }
	
    public void actualizarSimbolo(String lexema){
        Token token = tabla.get(lexema);
        tabla.remove(lexema);
        String valorNegativo = "-" + lexema;
        insertar(new Token(token.getId(), valorNegativo,token.getDescription(), token.getType()));
    }

}