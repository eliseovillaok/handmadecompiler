package acciones_semanticas;
import compilador.*;

import java.io.FileReader;
import java.io.Reader;

/*  - Devolver a la entrada el último carácter leído
    - Buscar en la TS (ids y pal reservadas)
        - Si está,
            - Si es PR, devolver la Palabra Reservada
            - Si no, Devolver ID + Punt TS + *Tipo.*
        - Si no está,
            - Alta en la TS
            - Devolver ID + Punt TS + *Tipo.* verifica su longitud y envía un *warning* si la supera */

public class AS3 implements AccionSemantica {
    private static volatile AccionSemantica unicaInstancia = new AS3(); 
    int numeroID = 270;
    private AS3() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }

    @Override
    public void ejecutar(StringBuilder simbolosReconocidos, char entrada, FileReader posicion) {

    	//vuelvo a la marca de la posicion anterior
        try {
            posicion.reset(); 
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        String s = simbolosReconocidos.toString(); 
        //Busco en la Tabla de Palabras Reservadas
        PalabraReservada token_buscado = TablaPalabrasReservadas.getInstance().obtenerPalabraReservada(s);
        if (token_buscado != null){
            //Si es PR, devuelvo la PR
            Token token = new Token((int) token_buscado.getCodigo(), token_buscado.getPalabra(), "Palabra Reservada");
            AnalizadorLexico.getInstance().retornar(token);    
        }
        else {
            //Si no está, busco en la TS
            if (s.length() > 15){
                System.out.println("Warning: Identificador supera los 15 caracteres. Se trunco la variable: " + s + " , porfavor revisar.");
                s.substring(0,14);
            }
                if (TablaSimbolos.getInstance().buscar(s) != null) {
                    //Si está, devuelvo ID + Punt TS + *Tipo.*
                    Token token = TablaSimbolos.getInstance().buscar(s);
                    AnalizadorLexico.getInstance().retornar(token);
                } else {
                    //Si no está, doy de alta en la TS
                    //Devuelvo ID + Punt TS + *Tipo.*
                    //Verifico longitud y envío un warning si la supera
                    Token retorno = new Token( numeroID , s, "identificador");
                    TablaSimbolos.getInstance().insertar(retorno);
                    this.numeroID += 1;
                    AnalizadorLexico.getInstance().retornar(retorno);
                }
        }

    }	
}