package manejo_archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static void insertarFunciones(String archivoPath, String inicioFuncion, String finFuncion) throws IOException {
        // Leer el archivo
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }

        // Buscamos la línea identificadora y el primer 'ret'
        StringBuilder funcion = new StringBuilder();
        int indiceIdentificador = -1;
        int indiceRet = -1;

        // Buscar la línea identificadora y cortar la función
        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);

            // Identificar la línea de la función (línea identificadora)
            if (linea.contains(inicioFuncion) && indiceIdentificador == -1) {
                indiceIdentificador = i;
                funcion.append(linea).append("\n");
                continue;
            }

            // Si hemos encontrado la línea identificadora, buscamos el 'ret'
            if (indiceIdentificador != -1) {
                funcion.append(linea).append("\n");
                if (linea.trim().equals(finFuncion)) {
                    indiceRet = i;
                    break;
                }
            }
        }

        // Ahora removemos la sección de la función de su lugar original
        if (indiceIdentificador != -1 && indiceRet != -1) {
            // Eliminar desde la línea identificadora hasta el primer 'ret'
            for (int i = indiceIdentificador; i <= indiceRet; i++) {
                lineas.set(i, null);  // Marcamos como nulas las líneas que deben eliminarse
            }
        }

        // Insertamos la función debajo de la sección '.code'
        StringBuilder archivoModificado = new StringBuilder();
        boolean codigoInsertado = false;
        for (String linea : lineas) {
            if (linea != null) {
                if (!linea.contains("START:")){
                    archivoModificado.append(linea).append("\n");
                } else if (!codigoInsertado){
                    archivoModificado.append(funcion);
                    archivoModificado.append("\n");
                    archivoModificado.append("START:");
                    codigoInsertado = true;
                }
            }
        }

        // Escribimos el archivo modificado
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoPath))) {
            bw.write(archivoModificado.toString());
        }
    }
}
