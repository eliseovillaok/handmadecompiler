package compilador;

public class TablaSimbolos extends Tabla { //
    private static volatile TablaSimbolos unicaInstancia = new TablaSimbolos();

    private TablaSimbolos() {
    }

    public static TablaSimbolos getInstance() { // Singleton
        return unicaInstancia;
    }

    public void actualizarSimbolo(String lexema, String nuevo_lexema) {
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

    public void actualizarTipo(String nombre, String tipo) {
        // Encuentra el símbolo por su nombre (la función en este caso)
        Token nombreFuncion = tabla.get(nombre);
        // Si lo encuentra, actualiza el tipo
        if (nombreFuncion != null) {
            nombreFuncion.setType(tipo.toUpperCase());
        }
    }
    

}