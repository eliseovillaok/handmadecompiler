package compilador;

public class Sintactico {
    private static volatile Sintactico unicaInstancia = new Sintactico(); 
    private Sintactico() {}
    
    public static Sintactico getInstance() { // Singleton
    	return unicaInstancia;
    }

    public void ejecutar(AnalizadorLexico lexico) {
        Par par = null;
        while ((lexico.getProximoToken().getId() != -2)) {
            if (lexico.getProximoToken().getId() != -1){
                par = lexico.getProximoToken();
            }
                
        }
    }
    
}
