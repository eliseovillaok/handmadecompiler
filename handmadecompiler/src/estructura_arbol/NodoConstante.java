package estructura_arbol;

// Representa una constante numérica.

class NodoConstante extends Nodo {
    private final int valor;

    public NodoConstante(int valor) {
        this.valor = valor;
    }

    @Override
    public void generarCodigo() {
        System.out.println("PUSH " + valor);  // Ejemplo: Instrucción para empujar el valor en la pila
    }
}

