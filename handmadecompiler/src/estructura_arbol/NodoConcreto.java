package estructura_arbol;

import java.util.ArrayList;
import java.util.List;

import compilador.Parser;
import compilador.TablaSimbolos;
import compilador.Token;

// Representa una nodo hijo (constante, variable, etc...)

public class NodoConcreto extends Nodo {

    private List<String> ambitoCreacion;

    public NodoConcreto(String valor) {
        super(valor);
        ambitoCreacion = new ArrayList<>(Parser.mangling);
    }

    public NodoConcreto(String valor, String tipo) {
        super(valor);
        this.tipo = tipo;
        ambitoCreacion = new ArrayList<>(Parser.mangling);
    }

    @Override
    public String implementacion(){
        return valor;
    }

    @Override
    public String generarCodigo(String tipoRetorno) {
        return implementacion();
    }

    public String getTipo() {
        return tipo;
    }

    public String getAmbito() {
        String ambito = "";
        for(String mangle : ambitoCreacion){
            ambito += ':' + mangle;
        }
        return ambito;
    }

    @Override
    public void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo) {
        if (tipo != null)
            sb.append(prefijo).append("└── ").append(valor).append(" TIPO: ").append(tipo).append("\n");
        else
            sb.append(prefijo).append("└── ").append(valor).append("\n");
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
        TablaSimbolos ts = TablaSimbolos.getInstance();
        String simbolo = valor;
        Token tokenConstante = ts.buscar(simbolo);
        ambitoCreacion = mangling;

        if (tokenConstante != null && tokenConstante.getType() != "DESCONOCIDO") {
            return tokenConstante.getType();
        }

        for (String mangle : mangling) {
            simbolo = simbolo + ":" + mangle;
        }

        if (ts.devolverTipo(simbolo) == null) {
            return "N/D";
        }

        return ts.devolverTipo(simbolo);
    }

    public String comprobarTipos() {

        if (this.tipo == null) {
            TablaSimbolos ts = TablaSimbolos.getInstance();
            this.tipo = ts.devolverTipo(valor);
        }
        return tipo;
    }

    public String devolverDescripcion() {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        String simbolo = valor;
        if (ts.buscar(simbolo) == null) {
            for (String mangle : ambitoCreacion) {
                simbolo = simbolo + ":" + mangle;
            }
        }
        if (ts.buscar(simbolo) != null){
            return ts.buscar(simbolo).getDescription();
        }
        return "auxiliar";
    }

}
