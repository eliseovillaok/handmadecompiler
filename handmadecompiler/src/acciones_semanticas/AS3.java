package acciones_semanticas;
import compilador.*;

import java.io.BufferedReader;
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
    private final int NUMEROID = 257;
    private final int NUMEROTAG = 275;
    private Token tokenRetorno;
    private TablaSimbolos ts = TablaSimbolos.getInstance();
    private TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();
    
    private AS3() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    private boolean tipoUinteger(String lexema) {
    	if (lexema.startsWith("u") ||lexema.startsWith("v") || lexema.startsWith("w"))
    		return true;
    	return false;
    }

    private boolean tipoSingle(String lexema) {
    	if (lexema.startsWith("s"))
    		return true;
    	return false;
    }
    
    @Override
    public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea,AnalizadorLexico lex) {

    	//vuelvo a la marca de la posicion anterior
        try {
            //ESTA LINEA ESTA COMENTADA YA QUE AL ESTAR MAL CARGADAS LAS MATRICES ENTRA EN BUCLE AL VOLVER HACIA ATRAS CONSTANTEMENTE Y NO AVANZA EN EL PROGRAMA.
            posicion.reset(); 
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        String s = simbolosReconocidos.toString().toLowerCase(); 
        //Busco en la Tabla de Palabras Reservadas
        tokenRetorno = tpr.buscar(s);
        
        if (tokenRetorno != null){
            //Si es PR, devuelvo la PR
            if (tokenRetorno.getDescription() == ""){
                tokenRetorno.setDescription("Palabra reservada");
                tokenRetorno.setType("PR");
            }
            return lex.retornar(tokenRetorno);            
        }
        else {
            //Si no está, busco en la TS
            if (s.length() > 15){
                System.out.println("Warning: Identificador supera los 15 caracteres. Se trunco la variable: " + s + " , por favor revisar.");
                s = s.substring(0,14);
            }
            if ((tokenRetorno = ts.buscar(s)) != null) 
                //Si está, devuelvo ID + Punt TS + *Tipo.*
                return lex.retornar(tokenRetorno);
            else {
                //Si no está, doy de alta en la TS
                //Devuelvo ID + Punt TS + *Tipo.*
                //Verifico longitud y envío un warning si la supera
                if (s.endsWith("@")) {
                    tokenRetorno = new Token(NUMEROTAG, s, "TAG");
                    tokenRetorno.setType("TAG");
                    System.out.println("Se dio de alta el TAG: " + s + " en la tabla de simbolos.");
                } else {
                    tokenRetorno = new Token(NUMEROID, s, "Identificador");
                    // Chequeamos es uinteger
                    if (tipoUinteger(s))
                        tokenRetorno.setType("ID_UINTEGER");
                    else if (tipoSingle(s))
                        tokenRetorno.setType("ID_SINGLE");
                    else
                        tokenRetorno.setType("DESCONOCIDO"); // Pongo el tipo desconocido si no es uinteger o single
                    System.out.println("Se dio de alta el identificador: " + s + " en la tabla de simbolos.");
                }
                
                ts.insertar(tokenRetorno);
                
                return lex.retornar(tokenRetorno);
            }
        }

    }	
}