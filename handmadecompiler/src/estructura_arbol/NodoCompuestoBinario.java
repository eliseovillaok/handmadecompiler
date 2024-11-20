package estructura_arbol;

import compilador.Parser;

public class NodoCompuestoBinario extends NodoCompuesto {

    public NodoCompuestoBinario(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
        this.tipo = comprobarTipos();
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
        } else if (valor != ",") {
            Parser.yyerror("falta un tipo");
        }
        return null;
    }

}
