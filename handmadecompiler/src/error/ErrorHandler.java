package error;

import java.util.HashSet;
import java.util.Set;

public class ErrorHandler {
    private static Set<String> errorSet = new HashSet<String>();

    private ErrorHandler() { // Privado porque vamos a usar la clase y no una instancia.
    }

    public static boolean addError(String error) { // Agregar error
        return errorSet.add(error); // Devuelve true si se añade, false si ya existía.
    }

    private static String getError() {
        // No se puede obtener un elemento específico de un Set, así que lo convertimos a un array.
        if (!errorSet.isEmpty()) {
            String[] errors = errorSet.toArray(new String[0]);
            String lastError = errors[errors.length - 1]; // Obtener el último error del array.
            errorSet.remove(lastError); // Removerlo del Set.
            return lastError;
        }
        return null; // O manejar el caso cuando no hay errores.
    }

    public static void imprimir() { // Mientras no sea vacío el conjunto de errores, imprimirlos línea por línea
        while (!errorSet.isEmpty()) {
            System.err.println(getError());
        }
    }
}