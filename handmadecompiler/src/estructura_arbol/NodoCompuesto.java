package estructura_arbol;
import java.util.List;

import manejo_archivos.FileHandler;

public class NodoCompuesto extends Nodo {
    protected String filePath = "salida.asm";
    protected Nodo[] hijos;
    protected final int IZQ = 0;
    protected final int DER = 1;
    protected String codigo = "";

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

    @Override
    public String implementacion() {
        return "";
    }

    // Genera el codigo de sus dos hijos (delega la accion de generar codigo)
    public String generarCodigo() {
        boolean izq = false;
        boolean der = false;

        if (hijos[IZQ] == null && hijos[DER] == null)
            return "";  
        if (hijos[IZQ] != null && hijos[DER] == null){
            izq = true;
        }
        else if (hijos[IZQ] == null && hijos[DER] != null){
            der = true;
        }
        else {
            izq = true;
            der = true;
        }

        if (izq && (hijos[IZQ] instanceof NodoConcreto))
            izq = false;
        if (der && (hijos[DER] instanceof NodoConcreto))
            der = false;

        if (!der && !izq) {
            int ultimoDelimitador = valor.lastIndexOf("_");
            // Si hay un guion bajo, corta hasta ese índice
            String result = (ultimoDelimitador != -1) 
                ? valor.substring(0, ultimoDelimitador) 
                : valor; // Si no hay "_", devuelve el original
            switch (result) {
                case("RET"):
                    implementacion();
                    break;
                case("INVOCACION_FUNCION"):
                    implementacion();
                    break;
                case("CONDICION"):
                    implementacion();
                    break;
                case("OUTF"):
                    implementacion();
                    break;
                case("GOTO"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER GOTO");
                    break;
                case("TOS"):
                    return implementacion();
                default:
                    break;
            }
        } else {
            if (izq){
                this.hijos[IZQ] = new NodoConcreto(hijos[IZQ].generarCodigo());
            }
            if (der){
                this.hijos[DER] = new NodoConcreto(hijos[DER].generarCodigo());
            }
            this.generarCodigo();
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

    protected String devolverId(Nodo nodo){
        if (nodo instanceof NodoConcreto) {
            String retorno = nodo.generarCodigo();
            if (((NodoConcreto)nodo).devolverDescripcion().equals("Constante")){                
                if (((NodoConcreto)nodo).getTipo().equals("SINGLE")){
                    retorno = "@"+retorno.replace(".", "");
                    retorno = retorno.replace("-", "N");
                }
                return retorno;
            
            } else if (((NodoConcreto)nodo).devolverDescripcion().equals("Identificador"))
                return "_" + retorno + ((NodoConcreto)nodo).getAmbito().replaceAll(":", "_");
            else if (((NodoConcreto)nodo).devolverDescripcion().equals("Cadena"))
                return retorno.replaceAll(" ", "_");
            else
                return retorno;
        }
        return null;
    }

    public String getAmbito() {
        return hijos[IZQ].getAmbito();
    }

    public void propagarTipoFuncion(String tipoFuncion) {
        for (Nodo hijo : hijos) {
            if (hijo != null) {
                hijo.propagarTipoFuncion(tipoFuncion);
            }
        }
    }

    public void chequeoRecursion(String nombreFuncion) {
        for (Nodo hijo : hijos) {
            if (hijo != null) {
                hijo.chequeoRecursion(nombreFuncion);
            }
        }
    }


}
