package estructura_arbol;

// Representa una variable

public class NodoIdentificador extends Nodo {

    public NodoIdentificador(String nombre) {
        super("variable", nombre);
    }

    @Override
    public String generarCodigo() {
        // Simplemente devuelve el nombre de la variable.
        return valor;
    }
}

