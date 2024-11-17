package estructura_arbol;

import compilador.Parser;

public class NodoCompuestoBinario extends NodoCompuesto {

    public NodoCompuestoBinario(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
        this.tipo = comprobarTipos();
    }

    protected String devolverId(Nodo nodo){
        if (nodo instanceof NodoConcreto) {
            if (((NodoConcreto)nodo).devolverDescripcion().equals("Constante"))
                return nodo.generarCodigo();
            else if (((NodoConcreto)nodo).devolverDescripcion().equals("Identificador"))
                return "_" + nodo.generarCodigo() + ((NodoConcreto)nodo).getAmbito().replaceAll(":", "_");
            else
                return nodo.generarCodigo();
        }
        return null;
    }

    public String implementacion(){
        return "";
    }

    // Genera el codigo de sus dos hijos (delega la accion de generar codigo)
    public String generarCodigo() {
        String auxUtilizado = "";
        if (hijos[IZQ] instanceof NodoConcreto && hijos[DER] instanceof NodoConcreto) {
            auxUtilizado = implementacion();
        } else if (!(hijos[IZQ] instanceof NodoConcreto)){
            this.hijos[IZQ] = new NodoConcreto(hijos[IZQ].generarCodigo(), hijos[IZQ].tipo); 
            auxUtilizado = this.generarCodigo();
        }
        else {
            this.hijos[DER] = new NodoConcreto(hijos[DER].generarCodigo(), hijos[DER].tipo);
            auxUtilizado = this.generarCodigo();
        }
        return auxUtilizado;
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
                Parser.yyerror("no coinciden los tipos");
            }
        } else {
            Parser.yyerror("falta un tipo");
        }
        return null;
    }

}
