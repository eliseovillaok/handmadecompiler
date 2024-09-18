package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
import compilador.TablaSimbolos;
import compilador.Token;

//Busca y devuelve o crea en la Tabla de Simbolos

public class AS6 implements AccionSemantica{
	private static volatile AccionSemantica unicaInstancia = new AS6();
    int numeroID = 570; 
    private AS6() {}
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
            Token retorno = new Token( numeroID , s, "Literal");
            TablaSimbolos.getInstance().insertar(retorno);
            this.numeroID += 1;
            AnalizadorLexico.getInstance().retornar(retorno);
        }
    	
    }
}
