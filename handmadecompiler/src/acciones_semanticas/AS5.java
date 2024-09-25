package acciones_semanticas;
import compilador.*;

import java.io.BufferedReader;


// Verifica rango de constantes y la devuelve o crea en la tabla de simbolos

public class AS5 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS5(); 
    private final int NUMEROCTE_UINTEGER = 281;
    private final int NUMEROCTE_SINGLE = 282;
    private final int NUMEROCTE_HEXA = 283;
    private Token tokenRetorno;
    private AnalizadorLexico lex = AnalizadorLexico.getInstance();
    private TablaSimbolos ts = TablaSimbolos.getInstance();
    
    private AS5() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    
    private int tipoCTE(String lexema) {
    	if (lexema.contains("."))
    		return NUMEROCTE_SINGLE;
    	if (lexema.contains("x")) {
    		return NUMEROCTE_HEXA;
    	}
    		
    	return NUMEROCTE_UINTEGER;
    }
    
    private boolean cumpleRango(String lexema,int numeroLinea) {
    	boolean cumple = false;
    	
    	switch (tipoCTE(lexema)) {
		case NUMEROCTE_SINGLE:{
			try {
    			Float.parseFloat(lexema.replace('s', 'e')); // Comprobamos rango cambiando "s" por "e" temporalmente, no se guarda...
    			cumple = true;
			} catch (NumberFormatException e) { // Si entra al catch, significa que se fue de rango
				System.out.println("Warning: Linea "+numeroLinea+" constante flotante fuera de rango.");
			}
			break;
		}
		case NUMEROCTE_HEXA:{
			System.out.println(lexema);
			if ((lexema.length() > 6))
    			System.out.println("Warning: Linea "+numeroLinea+" constante hexadecimal positiva fuera de rango");
			else
				cumple = true;
			break;
		}
		case NUMEROCTE_UINTEGER:{
			try {
				if (Integer.parseInt(lexema) < 65535) // < 2¹⁶-1
					cumple = true;
			} catch (NumberFormatException e) {
				System.out.println("Warning: Linea "+numeroLinea+" constante entera positiva fuera de rango.");
			}
			break;
		}
		default:
			break;
		}
    	return cumple;
    }
    
    @Override
    public void ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,int numeroLinea) {
    	// Vuelvo a la marca de la posicion anterior
        try {
            posicion.reset(); 
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(1);
        }
        String s = simbolosReconocidos.toString(); 
        if ((tokenRetorno = ts.buscar(s))!= null) {
            //Si está, devuelvo ID + Punt TS + *Tipo.*
            lex.retornar(tokenRetorno);
        } else {
            //Si no está, doy de alta en la TS
            //Devuelvo ID + Punt TS + *Tipo.*
            //Verifico longitud y envío un warning si la supera
            //System.out.println("el 2 se agrega correctamente");
        	if (cumpleRango(s,numeroLinea)) {
	        	tokenRetorno = new Token();
	    		switch (tipoCTE(s)) { // Creamos el token especifico para cada tipo de constante.
				case NUMEROCTE_SINGLE:{
					tokenRetorno.setId(NUMEROCTE_SINGLE);
					tokenRetorno.setType("Float");
					break;}
				case NUMEROCTE_HEXA:{
					tokenRetorno.setId(NUMEROCTE_HEXA);
					tokenRetorno.setType("Hexadecimal");
					break;}
				case NUMEROCTE_UINTEGER:{
					tokenRetorno.setId(NUMEROCTE_UINTEGER);
					tokenRetorno.setType("uinteger");
					break;}
				default:
					break;
				}
	    		tokenRetorno.setDescription("Constante");
	    		tokenRetorno.setLexeme(s);
	    		// Insertamos y retornamos.
	            ts.insertar(tokenRetorno);
	            lex.retornar(tokenRetorno);
    		}
        }
    	
    }

}
