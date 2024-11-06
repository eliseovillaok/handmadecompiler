package compilador;

public class TablaSimbolos extends Tabla { //
    private static volatile TablaSimbolos unicaInstancia = new TablaSimbolos();

    private TablaSimbolos() {
    }

    public static TablaSimbolos getInstance() { // Singleton
        return unicaInstancia;
    }

    public void actualizarSimbolo(String nuevo_lexema, String lexema) {
        System.out.println("Actualizando simbolo: " + lexema + " a " + nuevo_lexema);
        Token token = tabla.get(lexema);
        tabla.remove(lexema);
        token.setLexeme(nuevo_lexema);
        insertar(token);
    }

    public void actualizarUso(String lexema, String uso) {
        Token token = tabla.get(lexema);
        token.setUso(uso);
        System.out.println("Uso actualizado: " + token.getUso());
    }

    public void actualizarTipo(String lexema, String tipo) {
        // Encuentra el símbolo por su nombre (la función en este caso)
        Token token = tabla.get(lexema);
        // Si lo encuentra, actualiza el tipo
        if (token != null) {
            token.setType(tipo.toUpperCase());
        }
    }

    public String devolverTipo(String nombre) {
        Token nombreVar = tabla.get(nombre);
        if (nombreVar != null) {
            return nombreVar.getType();
        }
        return null;
    }

    
    public void actualizarTipoParamEsperado(String lexema, String tipo) {
        Token token = tabla.get(lexema);
        token.setTipoParametroEsperado(tipo.toUpperCase());
    }
    
    // public void borrarSimbolosDuplicados() {
    //     List<String> keysToRemove = new ArrayList<>();
        
    //     for (String key : tabla.keySet()) {
    //         Token token = tabla.get(key);
    //         if ((token.getType().equals("DESCONOCIDO")) || (token.getDescription().equals("Identificador") && !token.getLexema().contains(":"))) {
    //             keysToRemove.add(key);
    //         }
    //     }
        
    //     for (String key : keysToRemove) {
    //         tabla.remove(key); // Ahora puedes eliminar sin riesgo de ConcurrentModificationException
    //     }
    // }

}