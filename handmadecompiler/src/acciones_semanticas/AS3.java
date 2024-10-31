package acciones_semanticas;

import compilador.*;

import java.io.BufferedReader;
/*  - Devolver a la entrada el último carácter leído
    - Buscar en la TS (ids y pal reservadas)
        - Si está,
            - Si es PR, devolver la Palabra Reservada
            - Si no, Devolver ID + Punt TS + *Tipo.*
        - Si no está,
            - Alta en la TS (chequea si es TAG o ID para ponerle el numero correspondiente del token)
            - Devolver ID + Punt TS + *Tipo.* verifica su longitud y envía un *warning* si la supera */
import java.util.ArrayList;

public class AS3 implements AccionSemantica {
    private static volatile AccionSemantica unicaInstancia = new AS3();
    private final int NUMEROID = 257;
    private final int NUMEROTAG = 275;
    private Token tokenRetorno;
    private TablaSimbolos ts = TablaSimbolos.getInstance();
    private TablaPalabrasReservadas tpr = TablaPalabrasReservadas.getInstance();

    private AS3() {
    }

    public static AccionSemantica getInstance() { // Singleton
        return unicaInstancia;
    }

    private boolean tipoUinteger(String lexema) {
        if (lexema.startsWith("u") || lexema.startsWith("v") || lexema.startsWith("w"))
            return true;
        return false;
    }

    private boolean tipoSingle(String lexema) {
        if (lexema.startsWith("s"))
            return true;
        return false;
    }

    @Override
    public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion, int numeroLinea,
            AnalizadorLexico lex) {

        // Vuelvo a la marca de la posicion anterior
        try {
            posicion.reset();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        String s = simbolosReconocidos.toString().toLowerCase();
        // Busco en la Tabla de Palabras Reservadas
        tokenRetorno = tpr.buscar(s);

        if (tokenRetorno != null) {
            // Si es PR, devuelvo la PR
            if (tokenRetorno.getDescription() == "") {
                tokenRetorno.setDescription("Palabra reservada");
                tokenRetorno.setType("PR");
            }
            return lex.retornar(tokenRetorno);
        } else {
            // Si no es PR, busco en la TS
            if (s.length() > 15) {
                System.out.println("Warning: Identificador supera los 15 caracteres. Se trunco la variable: " + s
                        + " , por favor revisar.");
                s = s.substring(0, 14);
            }

            String copia = s;
            ArrayList<String> mangleActual = new ArrayList<>(Parser.mangling); // Crear una copia para evitar modificar el original
            for (String mangle : mangleActual) {
                copia = copia + ":" + mangle;
            }

            while (!mangleActual.isEmpty() && copia.contains(":")) {
                if ((tokenRetorno = ts.buscar(copia)) != null) {
                    return lex.retornar(tokenRetorno);
                } else {
                    mangleActual.remove(mangleActual.size() - 1); // Solo intenta remover si hay elementos
                    copia = s;
                    for (String mangle : mangleActual) {
                        copia = copia + ":" + mangle;
                    }
                }
            }
            // Si no está, doy de alta en la TS
            // Devuelvo ID + Punt TS + *Tipo.*
            // Verifico longitud y envío un warning si la supera
            if (s.endsWith("@")) {
                tokenRetorno = new Token(NUMEROTAG, s, "TAG");
                tokenRetorno.setType("TAG");
                System.out.println("Se dio de alta el TAG: " + s + " en la tabla de simbolos.");
            } else {
                tokenRetorno = new Token(NUMEROID, s, "Identificador");
                // Chequeamos es uinteger
                if (tipoUinteger(s))
                    tokenRetorno.setType("UINTEGER");
                else if (tipoSingle(s))
                    tokenRetorno.setType("SINGLE");
                else
                    tokenRetorno.setType("DESCONOCIDO"); // Pongo el tipo desconocido si no es uinteger o single
            }

            ts.insertar(tokenRetorno);

            return lex.retornar(tokenRetorno);
            
        }

    }
}