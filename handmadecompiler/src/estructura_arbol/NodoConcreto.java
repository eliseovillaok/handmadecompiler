package estructura_arbol;

import java.util.List;

import compilador.TablaSimbolos;
import compilador.Token;

// Representa una nodo hijo (constante, variable, etc...)

public class NodoConcreto extends Nodo {

    public NodoConcreto(String valor) {
        super(valor);
    }

    public NodoConcreto(String valor, String tipo) {
        super(valor);
        this.tipo = tipo;
    }

    @Override
    public String generarCodigo() {
        return valor; // Si bueno, en realidad se tendria que retornar el codigo asociado pero NP
                      // dejemoslo para el arbol
    }

    @Override
    public void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo) {
        sb.append(prefijo).append("└── ").append(valor).append(" TIPO: ").append(tipo).append("\n");
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
        Token tokenConstante = ts.buscar(valor);

        if (tokenConstante != null && tokenConstante.getType() != "DESCONOCIDO") {
            return tokenConstante.getType();
        }

        String simbolo = valor;
        for (String mangle : mangling) {
            simbolo = simbolo + ":" + mangle;
        }

        return ts.devolverTipo(simbolo);
    }

    public String comprobarTipos() {
        return tipo;
    }

}
