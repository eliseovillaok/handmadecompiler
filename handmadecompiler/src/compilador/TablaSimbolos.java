package compilador;

public class TablaSimbolos extends Tabla { //
    private static volatile TablaSimbolos unicaInstancia = new TablaSimbolos();

    private TablaSimbolos() {
    }

    public static TablaSimbolos getInstance() { // Singleton
        return unicaInstancia;
    }

    public void actualizarSimbolo(String lexema) {
        Token token = tabla.get(lexema);
        tabla.remove(lexema);
        String valorNegativo = "-" + lexema;
        insertar(new Token(token.getId(), valorNegativo, token.getDescription(), token.getType(),""));
    }

    public void actualizarUso(String lexema,String uso) {
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
    

}