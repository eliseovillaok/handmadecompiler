package compilador;
import java.util.Stack;

import error.ErrorHandler;
import estructura_arbol.*;
import manejo_archivos.FileHandler;

/*
\masm32\bin\ml /c /Zd /coff salida.asm

\masm32\bin\Link /SUBSYSTEM:CONSOLE salida.obj
 */

public class GeneradorCodigo {
    
    static TablaSimbolos ts = TablaSimbolos.getInstance();

    public static String filePathAssembly = "salida.asm";

    //Errores a contemplar
    private static final String ERROR_OVERFLOW_SUMA = "ERROR: Overflow en sumas de datos de punto flotante";
    private static final String ERROR_RESULTADO_NEGATIVO = "ERROR: Resultados negativos en restas de enteros sin signo";   
    private static final String ERROR_INVOCACION = "ERROR: Recursion en una funcion";

    public static int contadorAuxEntero = -1;
    public static int contadorAuxDoble = 31;
    public static int contadorEtiqueta = 0;
    public static Stack<String> pilaEtiquetas = new Stack<String>();

    public static void generarAssembler(Nodo programa){
        
        if (!ErrorHandler.hayErrores()) {
            /* CABECERA */
            StringBuilder cabecera = new StringBuilder();
            cabecera.append(".386\n")
                .append("option casemap :none\n")
                .append("include \\masm32\\include\\masm32rt.inc\n")
                .append("includelib \\masm32\\lib\\kernel32.lib\n")
                .append("includelib \\masm32\\lib\\masm32.lib\n\n")
                .append("dll_dllcrt0 PROTO C\n printf PROTO C : VARARG\n")
                .append(".data\n")
                //Constantes de error
                .append("ERROR_OVERFLOW_SUMA db \"" + ERROR_OVERFLOW_SUMA + "\", 10, 0\n")
                .append("ERROR_RESULTADO_NEGATIVO db \"" + ERROR_RESULTADO_NEGATIVO + "\", 10, 0\n")
                .append("ERROR_INVOCACION db \"" + ERROR_INVOCACION + "\", 10, 0\n")
                .append("buffer db 10 dup(0)\n")
                .append("limiteFloat sdword 3400000000000000000000000000000000000.0\n");
                String constantes = generarConstantes();
                cabecera.append(constantes);
            FileHandler.appendToFile(filePathAssembly, cabecera.toString());

            /* SEGMENTOS DE DATOS */
            generarDataSegment();

            /* SEGMENTO DE CODIGO */
            generarCodeSegment();

            /* PROGRAMA ASSEMBLER */
            FileHandler.appendToFile(filePathAssembly,"START:");
            programa.generarCodigo();

            /* FIN DEL PROGRAMA */
            FileHandler.appendToFile(filePathAssembly,"invoke ExitProcess, 0\nend START");

            System.out.println("\nCódigo assembler generado con éxito\n");
        } else {
            System.out.println("No se puede generar el código assembler debido a errores en el código fuente");
            ErrorHandler.imprimir();
        }
    }

    private static String generarConstantes(){
        //le agrego un @ a todas las constantes Single al principio de su lexema y las appendeo al stringbuilder
        StringBuilder constantes = new StringBuilder();
        for(String key : ts.getTabla().keySet()){
            String descripcion = ts.buscar(key).getDescription();
            if(descripcion.equalsIgnoreCase("Constante") || descripcion.equalsIgnoreCase("Cadena")){
                String nuevaKey = key;
                if(ts.buscar(key).getType().equalsIgnoreCase("SINGLE")){
                    nuevaKey = nuevaKey.replace(".", "");
                    nuevaKey = nuevaKey.replace("-", "N");
                    constantes.append("@" + nuevaKey + " sdword " + ts.buscar(key).getLexema() + "\n");
                }else if(descripcion.equalsIgnoreCase("CADENA")){
                    nuevaKey = nuevaKey.replaceAll(" ", "_");
                    constantes.append(nuevaKey + " db \"" + ts.buscar(key).getLexema() + "\", 10, 0\n");
                }
            }
        }
        return constantes.toString();
    }

    private static void generarDataSegment() {
        StringBuilder dataSegment = new StringBuilder();
        FileHandler.appendToFile(filePathAssembly,"\n.data?");
        // Pronto vemos como pasamos la TS a assembler
         for (String key : ts.getTabla().keySet()) {
            String nuevaKey = key;
            nuevaKey = nuevaKey.replaceAll(":", "_");
            String tipo = ts.buscar(key).getType();
            switch(tipo){
                case "UINTEGER":
                    if (!ts.buscar(key).getDescription().equalsIgnoreCase("Constante")){
                        if(!ts.buscar(key).getUso().equalsIgnoreCase("FUNCION"))
                            if(!ts.buscar(key).getUso().equalsIgnoreCase("Parametro"))
                                dataSegment.append("_" + nuevaKey).append(" dw ?\n");
                    }
                    break;
                case "SINGLE":
                    if (!ts.buscar(key).getDescription().equalsIgnoreCase("Constante")){
                        if(!ts.buscar(key).getUso().equalsIgnoreCase("Parametro"))
                            dataSegment.append("_" + nuevaKey).append(" sdword ?\n");
                    }
                    break;
                default:
                break;
            }
        }
        for (int i = 0 ; i < 32; i++){
            dataSegment.append("aux" + i + " dw ?\n");
        }
        dataSegment.append("RetUint dw ?\n");
        for (int i = 32 ; i < 64; i++){
            dataSegment.append("aux" + i + " sdword ?\n");
        }
        dataSegment.append("RetSingle sdword ?\n");
        FileHandler.appendToFile(filePathAssembly, dataSegment.toString());
        FileHandler.appendToFile(filePathAssembly, "impresionFloat dq ? \n");
    }

    private static void generarCodeSegment() {
        StringBuilder codeSegment = new StringBuilder();
        codeSegment.append(".code\n")
                .append(generarErrores());

        FileHandler.appendToFile(filePathAssembly, codeSegment.toString());
    }

    private static String generarErrores(){
        StringBuilder errores = new StringBuilder();
        errores.append("E_RES_NEG:\n")
                .append("invoke printf, addr ERROR_RESULTADO_NEGATIVO\n")
                .append("invoke ExitProcess, 1\n\n")
                .append("E_OF_SUMA:\n")
                .append("invoke printf, addr ERROR_OVERFLOW_SUMA\n")
                .append("invoke ExitProcess, 1\n\n")
                .append("E_RECURSION:\n")
                .append("invoke printf, addr ERROR_INVOCACION\n")
                .append("invoke ExitProcess, 1\n\n");
        return errores.toString();
    }

    public static String siguienteAuxEntero(){
        return "" + ++contadorAuxEntero;
    }

    public static String siguienteAuxDoble(){
        return "" + ++contadorAuxDoble;
    }

    public static String siguienteEtiqueta(){
        return "etiqueta" + contadorEtiqueta++;
    }

}
