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
        String tipoIzq = null;
        String tipoDer = null;
        if (hijos[IZQ] != null && hijos[DER] != null) {
            tipoIzq = hijos[IZQ].comprobarTipos();
            tipoDer = hijos[DER].comprobarTipos();
        }
        if (tipoIzq != null && tipoDer != null) {
            if (tipoIzq.equals(tipoDer)) {
                tipo = tipoIzq;
                return tipo;
            } else {
                System.out.println("ERROR EN LA EXPRESION");
            }
        } else {
            System.out.println("Falta un tipo en la expresion");
        }
        return null;
    }
}
