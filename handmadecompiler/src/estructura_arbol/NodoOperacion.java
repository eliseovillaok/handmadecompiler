package estructura_arbol;

public class NodoOperacion extends Nodo {

    public NodoOperacion(String operador) {
        super("operacion", operador);
    }

    @Override
    public String generarCodigo() {
        Nodo operandoIzquierdo = hijos.get(0);
        Nodo operandoDerecho = hijos.get(1);
        return operandoIzquierdo.generarCodigo() + " " + valor + " " + operandoDerecho.generarCodigo();
    }
}

