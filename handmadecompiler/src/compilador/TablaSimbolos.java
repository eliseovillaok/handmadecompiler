package compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TablaSimbolos { //Posible implementacion de la tabla de simbolos
    public static final int NO_ENCONTRADO = -1;
    public static final String NO_ENCONTRADO_S = "No encontrado";

    private static final Map<String, Token> simbolos = new HashMap<>();

    //metodos hechos por copilot :D

    public static int insertar(Token token) {
        simbolos.put(token.getLexema(), token);
    }

    public static Token buscar(String lexema) {
        if (simbolos.containsKey(lexema)) {
            return simbolos.get(lexema);
        }
        return NO_ENCONTRADO;
    }

    public static void limpiar() {
        simbolos.clear();
    }

    public static void imprimir() {
        Set<String> keys = simbolos.keySet();
        for (String key : keys) {
            System.out.println(simbolos.get(key));
        }
    }

}
