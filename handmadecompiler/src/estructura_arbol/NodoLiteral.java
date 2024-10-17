package estructura_arbol;

public class NodoLiteral extends Nodo {
    public NodoLiteral(String valor) {
        super("literal", valor);
    }

    @Override
    public String generarCodigo() {
        return valor;  // Retorna el valor literal directamente.
    }
}

