package error;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
    private static List<String> errorList = new ArrayList<String>();

    private ErrorHandler() { // Privado porque vamos a usar la clase y no una instancia.
    }

    public static void addError(String error) { // Agregar error
        if (!errorList.contains(error)) // Si no está, lo agrega
            errorList.add(error);
    }

    private static String getError() {
        return errorList.removeLast();
    }

    public static void imprimir() { // Mientras no sea vacío el conjunto de errores, imprimirlos línea por línea
        while (!errorList.isEmpty()) {
            System.err.println(getError());
        }
    }

    public static boolean hayErrores() {
        return !errorList.isEmpty();
    }
}