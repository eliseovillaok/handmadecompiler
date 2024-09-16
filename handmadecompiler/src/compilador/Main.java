package compilador;

public class Main {
    public static void main(String[] args) {
    	AnalizadorLexico lexico = AnalizadorLexico.getInstance();
    	
    	// TEST MOVIMIENTO A TRAVEZ DE LAS MATRICES
    	int i=0;
    	while (i<20) {
    		lexico.getProximoToken();
    		i++;
    	}
    }
}

