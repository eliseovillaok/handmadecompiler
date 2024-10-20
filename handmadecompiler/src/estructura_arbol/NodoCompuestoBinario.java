package estructura_arbol;

public class NodoCompuestoBinario extends NodoCompuesto {

    public NodoCompuestoBinario(String valor, Nodo izq, Nodo der) {
        super(valor,izq,der);
    }

    // Genera el codigo de sus dos hijos (delega la accion de generar codigo)
    public String generarCodigo(){
        Nodo izquierda = hijos[IZQ]; // Lado izquierdo
        Nodo derecha   = hijos[DER];   // Lado derecho
        return izquierda.generarCodigo() + valor + derecha.generarCodigo() + ";";
    }
    
}

