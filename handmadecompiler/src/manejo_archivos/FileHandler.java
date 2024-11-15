package manejo_archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private static final String directoryPath = "src/salida";

    public static void appendToFile(String fileName, String textToAppend) {
        // Crear un objeto File para el directorio
        File directory = new File(directoryPath);

        // Verificar si el directorio existe, y si no, crearlo
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directorio creado: " + directoryPath);
            } else {
                System.err.println("No se pudo crear el directorio: " + directoryPath);
                return;
            }
        }

        // Crear el archivo en el directorio especificado
        File file = new File(directory, fileName);

        // Usar BufferedWriter para escribir al archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Insertar una nueva línea antes de añadir el nuevo contenido
            writer.newLine();
            writer.write(textToAppend);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
