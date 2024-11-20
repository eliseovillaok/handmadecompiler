package estructura_arbol;

public class NodoLista extends NodoCompuestoBinario{

    public NodoLista(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        return hijos[IZQ].generarCodigo(null) + "," + hijos[DER].generarCodigo(null);
    }
    
}
