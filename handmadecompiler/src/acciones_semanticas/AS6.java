package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
import compilador.Par;
import compilador.TablaPalabrasReservadas;
import compilador.Token;

// Busca y devuelve el literal de la tabla de palabras reservadas (ademas actualiza su descripcion)

public class AS6 implements AccionSemantica {
    private static volatile AccionSemantica unicaInstancia = new AS6();
    private Token tokenRetorno;
    private TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();

    private AS6() {
    }

    public static AccionSemantica getInstance() { // Singleton
        return unicaInstancia;
    }

    @Override
    public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion, int numeroLinea,
            AnalizadorLexico lex) {
        // vuelvo a la marca de la posicion anterior
        try {
            posicion.reset();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        String s = simbolosReconocidos.toString();
        tokenRetorno = tpr.buscar(s); // Debería estar siempre porque está hardcodeado, no se insertan literales.
        if (tokenRetorno.getDescription() == "") {
            tokenRetorno.setDescription("Literal");
            tokenRetorno.setType("LIT");
        }

        return lex.retornar(tokenRetorno);
    }
}
