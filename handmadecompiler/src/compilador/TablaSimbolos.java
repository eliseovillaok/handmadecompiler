package compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import acciones_semanticas.AccionSemantica;

public class TablaSimbolos { //
	private static volatile TablaSimbolos unicaInstancia = new TablaSimbolos(); 
    private TablaSimbolos() {}
    public static TablaSimbolos getInstance() { // Singleton
    	return unicaInstancia;
    }
	
    private final Map<String, Token> simbolos = new HashMap<>();

    public void insertar(Token token) {
        simbolos.put(token.getLexema(),token);
    }

    public Token buscar(String lexema) {
    	return simbolos.get(lexema);
    }

    public void limpiar() {
        simbolos.clear();
    }

    public void imprimir() {
        Set<String> keys = simbolos.keySet();
        for (String key : keys) {
            System.out.println(simbolos.get(key));
        }
    }

}
