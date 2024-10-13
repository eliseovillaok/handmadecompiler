package estructura_arbol;

// Representa una operación aritmética o lógica entre dos expresiones

class NodoBinario extends Nodo {
    private final Nodo izquierda;
    private final Nodo derecha;
    private final String operador;

    public NodoBinario(Nodo izquierda, Nodo derecha, String operador) {
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.operador = operador;
    }

    @Override
    public void generarCodigo() {
        izquierda.generarCodigo();
        derecha.generarCodigo();
        System.out.println("OP " + operador);  // Por ejemplo: OP +
    }
}

