package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
import compilador.Par;
import compilador.Token;

public class ASE implements AccionSemantica {
    private static volatile AccionSemantica unicaInstancia = new ASE();

    private ASE() {
    }

    public static AccionSemantica getInstance() { // Singleton
        return unicaInstancia;
    }

    @Override
    public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion, int numeroLinea,
            AnalizadorLexico lexico) {
        String s = simbolosReconocidos.toString();
        System.err.println("Error: Token no reconocido: " + s + entrada + " en la linea " + numeroLinea);
        return new Par(-1, new Token(-1, "error"));
    }

}
