package estructura_arbol;

import java.util.List;

public abstract class Nodo {
    protected String valor;
    protected String tipo;

    public Nodo(String valor) {
        this.valor = valor;
        this.tipo = null;
    }

    // Método abstracto para que cada tipo de nodo genere su código.
    public abstract String generarCodigo(String tipoRetorno);

    public abstract String devolverTipo(List<String>mangling);

    public abstract void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo);

    public abstract String comprobarTipos();

    public abstract String getAmbito();

    public abstract String implementacion();

}
