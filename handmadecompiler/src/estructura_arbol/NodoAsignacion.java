package estructura_arbol;

/* no se si se hacia ahora o en el tp4 D: */

public class NodoAsignacion extends Nodo {

    public NodoAsignacion(String valor) {
        super("asignacion", valor);
    }

    @Override
    public String generarCodigo() {
        // Ejemplo de uso:
        // Por ejemplo: "x := y + 2;"
        Nodo izquierda = hijos.get(0); // Lado izquierdo de la asignación
        Nodo derecha = hijos.get(1);   // Lado derecho de la asignación
        return izquierda.generarCodigo() + " := " + derecha.generarCodigo() + ";";
    }
}

