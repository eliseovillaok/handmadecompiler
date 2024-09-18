package acciones_semanticas;
import compilador.*;

import java.io.BufferedReader;


// Verifica rango de constantes y la devuelve o crea en la tabla de simbolos

public class AS5 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS5(); 
    int numeroID = 470;
    private AS5() {}
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
        System.out.println("resultado de buscar en tabla de simbolos el 2: " + TablaSimbolos.getInstance().buscar(s));
        if (TablaSimbolos.getInstance().buscar(s) != null) {
            //Si está, devuelvo ID + Punt TS + *Tipo.*
            Token token = TablaSimbolos.getInstance().buscar(s);
            AnalizadorLexico.getInstance().retornar(token);
        } else {
            //Si no está, doy de alta en la TS
            //Devuelvo ID + Punt TS + *Tipo.*
            //Verifico longitud y envío un warning si la supera
            //System.out.println("el 2 se agrega correctamente");
            Token retorno = new Token( numeroID , s, "Constante");
            TablaSimbolos.getInstance().insertar(retorno);
            this.numeroID += 1;
            AnalizadorLexico.getInstance().retornar(retorno);
        }
    	
    }

}
