package compilador;

public class Sintactico {
    private static volatile Sintactico unicaInstancia = new Sintactico(); 
    private Sintactico() {}
    
    public static Sintactico getInstance() { // Singleton
    	return unicaInstancia;
    }

    public void ejecutar(AnalizadorLexico lexico) {
        boolean flag = true;
        Par par;
        while (flag) {
            par = lexico.getProximoToken();
            if (par.getId() == 0)
                flag = false;
        }
        
    }
    
}
