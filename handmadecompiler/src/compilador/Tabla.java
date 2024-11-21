package compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Tabla { //
    protected Map<String, Token> tabla = new HashMap<>();


    protected Tabla() {
    } // Constructor protegido.

    public void insertar(Token token) {
        tabla.put(token.getLexema(), token); // (Key,Value) = (Lexema,Token)
    }

    public Token buscar(String lexema) {
        // Convertir el lexema a minúsculas
        String lexemaLower = lexema.toLowerCase();
        
        // Crear un nuevo HashMap para almacenar las claves en minúsculas
        Map<String, Token> tablaLower = new HashMap<>();
        
        // Llenar el nuevo HashMap con claves en minúsculas
        for (Map.Entry<String, Token> entry : tabla.entrySet()) {
            tablaLower.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        
        // Buscar el token usando el lexema en minúsculas
        return tablaLower.get(lexemaLower);
    }   

    public void remover(String lexema) {
        tabla.remove(lexema);
    }

    public void limpiar() {
        tabla.clear();
    }

    public String imprimir() {
        String impresion = "";
        Set<String> keys = tabla.keySet();
        for (String key : keys) {
            impresion = impresion + "\n" + tabla.get(key);
        }
        return impresion;
    }

    protected Map<String, Token> getTabla() {
        return tabla;
    }

}
