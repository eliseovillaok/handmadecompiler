package estructura_arbol;

public class NodoIf extends NodoCompuesto {
    private Nodo condicion;  // La condición del if
    private Nodo bloqueThen;  // El bloque del then
    private Nodo bloqueElse;  // Opcional: el bloque del else

    public NodoIf() {
        super("if", "if");
    }

    public void agregarCondicion(Nodo condicion) {
        this.condicion = condicion;
    }
    
    public void agregarBloqueThen(Nodo bloqueThen) {
        this.bloqueThen = bloqueThen;
    }
    
    public void agregarBloqueElse(Nodo bloqueElse) {
        this.bloqueElse = bloqueElse;
    }
    

    @Override
    public String generarCodigo() {
        StringBuilder codigo = new StringBuilder();
        codigo.append("if (").append(condicion.generarCodigo()).append(") {\n");
        codigo.append(bloqueThen.generarCodigo()).append("\n");
        codigo.append("}");
        if (bloqueElse != null) {
            codigo.append(" else {\n");
            codigo.append(bloqueElse.generarCodigo()).append("\n");
            codigo.append("}");
        }
        return codigo.toString();
    }

}


