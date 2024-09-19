package acciones_semanticas;
import compilador.*;

import java.io.BufferedReader;


//- Devolver a la entrada el ultimo caracter leido
//- Buscar lexema en la TS

public class AS7 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS7(); 
    int numeroID = 670;
    private AS7() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	
    @Override
    public void ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion) {
    	//vuelvo a la marca de la posicion anterior
        try {
            posicion.reset(); 
        } catch (Exception e) {
            // TODO: handle exception
        }
        String s = simbolosReconocidos.toString(); 
        if (TablaSimbolos.getInstance().buscar(s) != null) {
            //Si está, devuelvo ID + Punt TS + *Tipo.*
            Token token = TablaSimbolos.getInstance().buscar(s);
            AnalizadorLexico.getInstance().retornar(token);
        } else {
            //Si no está, doy de alta en la TS
            //Devuelvo ID + Punt TS + *Tipo.*
            //Verifico longitud y envío un warning si la supera
            Token retorno = new Token( numeroID , s, "String");
            TablaSimbolos.getInstance().insertar(retorno);
            AnalizadorLexico.getInstance().retornar(retorno);
        }
    	
    }

}