package manejo_archivos;

import java.io.File;

public class DirectoryManager {

    public static void clearDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        // Verificar si el directorio existe
        if (directory.exists() && directory.isDirectory()) {
            // Llamar al método para eliminar el contenido del directorio
            deleteContents(directory);
            System.out.println("Contenido del directorio eliminado: " + directoryPath);
        }
    }

    private static void deleteContents(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteContents(file); // Borrar el contenido recursivamente si es un subdirectorio
                }
                file.delete(); // Borrar el archivo o el subdirectorio vacío
            }
        }
    }
}
