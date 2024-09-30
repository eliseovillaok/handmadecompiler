package compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Tabla{ //
    protected Map<String, Token> tabla = new HashMap<>();
    
    protected Tabla() {} // Constructor protegido.
                                       
    public void insertar(Token token) {
        tabla.put(token.getLexema(),token); // (Key,Value) = (Lexema,Token)
    }

    public Token buscar(String lexema) {
    	return tabla.get(lexema);
    }

    public void limpiar() {
    	tabla.clear();
    }

    public void imprimir() {
        Set<String> keys = tabla.keySet();
        for (String key : keys) {
            System.out.println(tabla.get(key));
        }
    }

}
