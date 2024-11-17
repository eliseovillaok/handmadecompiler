package estructura_arbol;

import java.util.List;

import manejo_archivos.FileHandler;

public class NodoCompuesto extends Nodo {
    protected String filePath = "salida_assembler.txt";
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
        boolean izq = false;
        boolean der = false;

        if (izquierda == null && derecha == null)
            return "";
        if (izquierda != null && derecha == null)
            izq = true;
        else if (izquierda == null && derecha != null)
            der = true;
        else {
            izq = true;
            der = true;
        }

        if (izq && (izquierda instanceof NodoConcreto))
            izq = false;
        if (der && (derecha instanceof NodoConcreto))
            der = false;

        switch (valor) {
            case ("programa"):
                FileHandler.appendToFile(filePath, "ASSEMBLER PROGRAMA");
                if (izq)
                    izquierda.generarCodigo();
                if (der)
                    derecha.generarCodigo();
                break;

            case ("s"):
                if (izq)
                    izquierda.generarCodigo();
                if (der)
                    derecha.generarCodigo();
                break;
            default:
                break;
        }

        return "";

    }

    @Override
    public void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo) {
        if (tipo != null)
            sb.append(prefijo).append(esUltimo ? "└── " : "├── ").append(valor).append(" TIPO: ").append(tipo)
                    .append("\n");
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
