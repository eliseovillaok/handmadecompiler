package compilador;

public class Sintactico {
    private static volatile Sintactico unicaInstancia = new Sintactico(); 
    private Sintactico() {}
    private AnalizadorLexico lexico = AnalizadorLexico.getInstance();
    
    public static Sintactico getInstance() { // Singleton
    	return unicaInstancia;
    }

    public void ejecutar() {
    	
        Par<Integer, Token> token = lexico.getProximoToken();
    }
    
}
