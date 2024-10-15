package estructura_arbol;

public class NodoPrograma extends Nodo {

    public NodoPrograma(String nombre) {
        super("programa", nombre);
    }

    @Override
    public String generarCodigo() {
        StringBuilder codigo = new StringBuilder();
        codigo.append("Programa: ").append(valor).append("\n");
        
        // Recorre los hijos (las sentencias del programa)
        for (Nodo hijo : hijos) {
            codigo.append(hijo.generarCodigo()).append("\n");
        }

        return codigo.toString();
    }
}

