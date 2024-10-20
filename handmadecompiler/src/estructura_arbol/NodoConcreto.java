package estructura_arbol;

// Representa una nodo hijo (constante, variable, etc...)

public class NodoConcreto extends Nodo {

    public NodoConcreto(String valor) {
        super(valor);
    }

    @Override
    public String generarCodigo() {
        return valor; // Si bueno, en realidad se tendria que retornar el codigo asociado pero NP dejemoslo para el arbol
    }

    @Override
    public void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo){
    	sb.append(prefijo).append("└── ").append(valor).append("\n");
    }

    // toString para visualizar el árbol sintáctico
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        imprimirNodo(sb, "", true);
        return sb.toString();
    }
}
