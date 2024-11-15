package estructura_arbol;

import java.util.List;

public class NodoCompuesto extends Nodo {
    protected Nodo[] hijos;
    protected final int IZQ = 0;
    protected final int DER = 1;

    public NodoCompuesto(String valor, Nodo izq, Nodo der) {
        super(valor);
        this.hijos = new Nodo[2];
        this.hijos[IZQ] = izq;
        this.hijos[DER] = der;
        this.tipo = comprobarTipos();
    }

    public NodoCompuesto(String valor, Nodo izq, Nodo der, String tipo) {
        super(valor);
        this.hijos = new Nodo[2];
        this.hijos[IZQ] = izq;
        this.hijos[DER] = der;
        this.tipo = tipo;
    }

    // Genera el codigo de sus dos hijos (delega la accion de generar codigo)
    public String generarCodigo() {
        Nodo izquierda = hijos[IZQ]; // Lado izquierdo
        Nodo derecha = hijos[DER]; // Lado derecho
        return izquierda.generarCodigo() + derecha.generarCodigo() + ";";
    }

    @Override
    public void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo) {
        if (tipo != null)
            sb.append(prefijo).append(esUltimo ? "└── " : "├── ").append(valor).append(" TIPO: ").append(tipo).append("\n");
        else
            sb.append(prefijo).append(esUltimo ? "└── " : "├── ").append(valor).append("\n");
        for (int i = 0; i < 2; i++) { // Mirar mas este enfoque
            if (hijos[i] != null)
                hijos[i].imprimirNodo(sb, prefijo + (esUltimo ? "    " : "│   "), i == DER);
        }
    }

    // toString para visualizar el árbol sintáctico
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        imprimirNodo(sb, "", true);
        return sb.toString();
    }

    @Override
    public String devolverTipo(List<String> mangling) {
        return hijos[IZQ].devolverTipo(mangling);
    }

    public String comprobarTipos() {
        return tipo;
    }

}
