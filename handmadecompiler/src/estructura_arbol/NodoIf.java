package estructura_arbol;

// Representa una estructura condicional.

class NodoIf extends Nodo {
    private final Nodo condicion;
    private final Nodo cuerpoVerdadero;
    private final Nodo cuerpoFalso;

    public NodoIf(Nodo condicion, Nodo cuerpoVerdadero, Nodo cuerpoFalso) {
        this.condicion = condicion;
        this.cuerpoVerdadero = cuerpoVerdadero;
        this.cuerpoFalso = cuerpoFalso;
    }

    @Override
    public void generarCodigo() {
        condicion.generarCodigo();
        System.out.println("JZ ELSE_LABEL");  // Salta si la condición es falsa
        cuerpoVerdadero.generarCodigo();
        System.out.println("JMP END_LABEL");
        System.out.println("ELSE_LABEL:");
        if (cuerpoFalso != null) {
            cuerpoFalso.generarCodigo();
        }
        System.out.println("END_LABEL:");
    }
}

