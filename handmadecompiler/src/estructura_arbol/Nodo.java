package estructura_arbol;

public abstract class Nodo{
    protected String valor;

    public Nodo(String valor) {
        this.valor = valor;    
    }    
    // Método abstracto para que cada tipo de nodo genere su código.
    public abstract String generarCodigo();
    public abstract void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo);

}

