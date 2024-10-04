package compilador;

import java.util.ArrayList;

public class Sintactico {
    private static volatile Sintactico unicaInstancia = new Sintactico(); 
    private Sintactico() {}
    
    public static Sintactico getInstance() { // Singleton
    	return unicaInstancia;
    }

    public ArrayList<Integer> ejecutar(AnalizadorLexico lexico) {
        boolean flag = true;
        ArrayList<Integer> listaDeTokens = new ArrayList<Integer>();
        Par par;
        while (flag) {
            par = lexico.getProximoToken();
            listaDeTokens.add(par.getId());
            if (par.getId() == 0)
                flag = false;
        }
        System.out.println("Lista de tokens: " + listaDeTokens);
        return listaDeTokens;
    }
    
}
