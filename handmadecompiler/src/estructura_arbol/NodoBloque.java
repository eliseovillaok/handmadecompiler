package estructura_arbol;

public class NodoBloque extends Nodo {

    public NodoBloque() {
        super("bloque", "");
    }

    @Override
    public String generarCodigo() {
        StringBuilder codigo = new StringBuilder();
        codigo.append("Bloque:\n");
        
        // Recorre los hijos (las sentencias dentro del bloque)
        for (Nodo hijo : hijos) {
            codigo.append(hijo.generarCodigo()).append("\n");
        }

        return codigo.toString();
    }
}
