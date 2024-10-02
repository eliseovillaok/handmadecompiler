package compilador;

public class Sintactico {
    private static volatile Sintactico unicaInstancia = new Sintactico(); 
    private Sintactico() {}
    private AnalizadorLexico lexico = AnalizadorLexico.getInstance();
    
    public static Sintactico getInstance() { // Singleton
    	return unicaInstancia;
    }

    public void ejecutar() {
        Par<Integer, Token> par = null;
        while ((lexico.getProximoToken().getToken() != -2)) {
            if (lexico.getProximoToken().getToken() != -1){
                par = lexico.getProximoToken();
            }
                
        }
    }
    
}
