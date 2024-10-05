package acciones_semanticas;
import compilador.*;

import java.io.BufferedReader;


//- Devolver a la entrada el ultimo caracter leido
//- Buscar lexema en la TS

public class AS7 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS7(); 
    private final int NUMEROCADENA = 279;
    private Token tokenRetorno;
    private TablaSimbolos ts = TablaSimbolos.getInstance();
    
    private AS7() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	
    @Override
    public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea,AnalizadorLexico lex) {
    	//vuelvo a la marca de la posicion anterior
        try {
            posicion.reset(); 
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        String s = simbolosReconocidos.toString(); 
        if ((tokenRetorno = ts.buscar(s)) != null) {
            //Si está, devuelvo ID + Punt TS + *Tipo.*
            return lex.retornar(tokenRetorno);
        } else {
            //Si no está, doy de alta en la TS
            //Devuelvo ID + Punt TS + *Tipo.*
            //Verifico longitud y envío un warning si la supera
            tokenRetorno = new Token(NUMEROCADENA , s, "Cadena");
            tokenRetorno.setType("CADENA");
            ts.insertar(tokenRetorno);
            return lex.retornar(tokenRetorno);
        }
    	
    }

}