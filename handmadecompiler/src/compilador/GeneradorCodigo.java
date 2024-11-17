package compilador;
import error.ErrorHandler;
import estructura_arbol.*;
import manejo_archivos.FileHandler;

public class GeneradorCodigo {
    
    static TablaSimbolos ts = TablaSimbolos.getInstance();

    static String filePathAssembly = "salida_assembler.txt";

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
                .append(".model flat, stdcall\n")
                .append("option casemap :none\n")
                .append("include \\masm32\\include\\windows.inc\n")
                .append("include \\masm32\\include\\kernel32.inc\n")
                .append("include \\masm32\\include\\user32.inc\n")
                .append("includelib \\masm32\\lib\\kernel32.lib\n")
                .append("includelib \\masm32\\lib\\user32.lib\n\n")
                .append(".data\n")
                //Constantes de error
                .append("ERROR_OVERFLOW_SUMA db \"" + ERROR_OVERFLOW_SUMA + "\", 0\n")
                .append("ERROR_RESULTADO_NEGATIVO db \"" + ERROR_RESULTADO_NEGATIVO + "\", 0\n")
                .append("ERROR_INVOCACION db \"" + ERROR_INVOCACION + "\", 0");
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
                default:
                    if(ts.buscar(key).getUso() == "Struct")
                        //declaracion struct (probablemente arreglo de longitud de las componentes) 
                        dataSegment.append("_" + key).append(" dd ?     MAQUETA STRUCT\n");
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

}
