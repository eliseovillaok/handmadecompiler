package estructura_arbol;

import java.util.List;

import compilador.GeneradorCodigo;
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
        String idIzq = "";
        String idDer = "";
        String auxiliarUtilizado = "aux";

        if (hijos[IZQ] == null && hijos[DER] == null)
            return "";
        if (hijos[IZQ] != null && hijos[DER] == null){
            izq = true;
            idIzq = devolverId(hijos[IZQ]);
        }
        else if (hijos[IZQ] == null && hijos[DER] != null){
            der = true;
            idDer = devolverId(hijos[DER]);
        }
        else {
            izq = true;
            der = true;
            idIzq = devolverId(hijos[IZQ]);
            idDer = devolverId(hijos[DER]);
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
                case ("programa"):
                    FileHandler.appendToFile(filePath, "");
                    break;
                case ("FUNCION"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER FUNCION");
                    break;
                case("RET"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER RET");
                    break;
                case("INVOCACION_FUNCION"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER INVOCACION_FUNCION");
                    break;
                case("IF"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER IF");
                    break;
                case("CONDICION"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER CONDICION");
                    break;
                case("CUERPO"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER CUERPO");
                    break;
                case("THEN"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER THEN");
                    break;
                case("ELSE"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER ELSE");
                    break;
                case("OUTF"):
                    implementacion();
                    break;
                case("REPEAT_UNTIL"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER REPEAT_UNTIL");
                    break;
                case("GOTO"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER GOTO");
                    break;
                case("TOS"):
                    FileHandler.appendToFile(filePath, "ASSEMBLER TOS");
                    break;
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
            else
                return retorno;
        }
        return null;
    }

    public String getAmbito() {
        return hijos[IZQ].getAmbito();
    }

}
