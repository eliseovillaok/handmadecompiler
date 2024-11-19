package compilador;
import error.ErrorHandler;
import estructura_arbol.*;
import manejo_archivos.FileHandler;

/*
\masm32\bin\ml /c /Zd /coff salida.asm

\masm32\bin\Link /SUBSYSTEM:CONSOLE salida.obj
 */

public class GeneradorCodigo {
    
    static TablaSimbolos ts = TablaSimbolos.getInstance();

    static String filePathAssembly = "salida.asm";

    //Errores a contemplar
    private static final String ERROR_OVERFLOW_SUMA = "ERROR: Overflow en sumas de datos de punto flotante";
    private static final String ERROR_RESULTADO_NEGATIVO = "ERROR: Resultados negativos en restas de enteros sin signo";   
    private static final String ERROR_INVOCACION = "ERROR: Recursión en invocaciones de funciones";

    public static int contadorAuxEntero = -1;
    public static int contadorAuxDoble = 31;

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
                .append("ERROR_OVERFLOW_SUMA db \"" + ERROR_OVERFLOW_SUMA + "\", 0\n")
                .append("ERROR_RESULTADO_NEGATIVO db \"" + ERROR_RESULTADO_NEGATIVO + "\", 0\n")
                .append("ERROR_INVOCACION db \"" + ERROR_INVOCACION + "\", 0\n")
                .append("ERROR_OVERFLOW_MUL db \"ERROR: Overflow en multiplicación de enteros sin signo\", 0\n")
                .append("buffer db 10 dup(0)\n");
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
            if(ts.buscar(key).getDescription().equalsIgnoreCase("Constante")){
                String nuevaKey = key;
                if(ts.buscar(key).getType().equalsIgnoreCase("SINGLE")){
                    nuevaKey = nuevaKey.replace(".", "");
                    nuevaKey = nuevaKey.replace("-", "N");
                    constantes.append("@" + nuevaKey + " sdword " + ts.buscar(key).getLexema() + "\n");
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
                        if(ts.buscar(key).getUso().equalsIgnoreCase("FUNCION"))
                            dataSegment.append("_" + nuevaKey).append(" dd ?\n");
                        else
                            dataSegment.append("_" + nuevaKey).append(" dw ?\n");
                    }
                    break;
                case "SINGLE":
                    if (!ts.buscar(key).getDescription().equalsIgnoreCase("Constante")){
                        dataSegment.append("_" + nuevaKey).append(" sdword ?\n");
                    }
                    break;
                case "CADENA":
                    String aux = key;
                    aux = aux.replaceAll(" ", "_");
                    dataSegment.append(aux).append(" db " + "\""+ nuevaKey + "\"" + "\n");
                    break;
                case "TAG":
                    dataSegment.append("_" + key).append(" dd ? \n");
                    break;
                case "NOMBRE_PROGRAMA":
                    break;
                default:
                    if(!ts.buscar(key).getType().equalsIgnoreCase("")){
                        String ambito = key.substring(key.indexOf(":"));
                        dataSegment.append("_" + key).append(" dd "+ ((TokenStruct) ts.buscar(tipo+ambito)).getCantComponentes()  +" dup(?)\n");
                    }
                break;
            }
        }
        for (int i = 0 ; i < 32; i++){
            dataSegment.append("aux" + i + " dw ?\n");
        }
        for (int i = 32 ; i < 64; i++){
            dataSegment.append("aux" + i + " sdword ?\n");
        }
        FileHandler.appendToFile(filePathAssembly, dataSegment.toString());
        FileHandler.appendToFile(filePathAssembly, "impresionFloat dq ? \n");
    }

    private static void generarCodeSegment() {
        StringBuilder codeSegment = new StringBuilder();
        codeSegment.append(".code\n");
        //Veremos si esto tiene algo o no
        FileHandler.appendToFile(filePathAssembly, codeSegment.toString());
    }

    public static String siguienteAuxEntero(){
        return "" + ++contadorAuxEntero;
    }

    public static String siguienteAuxDoble(){
        return "" + ++contadorAuxDoble;
    }

}
