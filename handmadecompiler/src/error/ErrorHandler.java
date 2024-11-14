package error;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
    private static List<String> errorList = new ArrayList<String>();

    private ErrorHandler() { // Private porque vamos a usar la clase y no una instancia.
    }

    public static boolean addError(String error) { // Agregar error
        return errorList.add(error);
    }

    public static String getError() {
        return errorList.removeLast(); // Nos da el ultimo y a la vez lo saca de la lista de errores.
    }

    public static void imprimir() { // Mientras no sea vacia la lista de errores, imprimirlos linea x linea
        while (!errorList.isEmpty()) {
            System.err.println(getError());
        }
    }
}
