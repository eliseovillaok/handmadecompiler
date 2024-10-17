package estructura_arbol;

import java.util.ArrayList;
import java.util.List;

public abstract class Nodo {
    protected String tipo;
    protected String valor;
    protected List<Nodo> hijos;

    public Nodo(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(Nodo hijo) {
        hijos.add(hijo);
    }

    // Método abstracto para que cada tipo de nodo genere su código.
    public abstract String generarCodigo();

    // toString para visualizar el árbol sintáctico
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        imprimirNodo(sb, "", true);
        return sb.toString();
    }

    public void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo) {
        sb.append(prefijo).append(esUltimo ? "└── " : "├── ").append(valor).append("\n");
        
        // Recorre los hijos, pero omite los nodos nulos
        for (int i = 0; i < hijos.size(); i++) {
            Nodo hijo = hijos.get(i);
            if (hijo != null) {
                hijo.imprimirNodo(sb, prefijo + (esUltimo ? "    " : "│   "), i == hijos.size() - 1);
            }
        }
    }
}

