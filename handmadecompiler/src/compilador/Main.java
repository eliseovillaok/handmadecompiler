package compilador;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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

