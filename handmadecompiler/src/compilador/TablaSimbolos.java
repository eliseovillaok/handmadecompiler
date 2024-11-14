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
        if(token != null){
            tabla.remove(lexema);
            token.setLexeme(nuevo_lexema);
            insertar(token);
        }
    }

    public void actualizarUso(String lexema, String uso) {
        Token token = tabla.get(lexema);
        token.setUso(uso);
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
    
    public void borrarSimbolos(String simbolos){
        if(simbolos.contains(",")){
            String[] simbolosArray = simbolos.split(",");
            for (String simbolo : simbolosArray) {
                tabla.remove(simbolo);
            }
        } else
            tabla.remove(simbolos);


    }

}