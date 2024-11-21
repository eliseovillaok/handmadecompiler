package compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TablaPalabrasReservadas extends Tabla { //
    private static volatile TablaPalabrasReservadas unicaInstancia = new TablaPalabrasReservadas();

    private TablaPalabrasReservadas() {
    }

    public static TablaPalabrasReservadas getInstance() { // Singleton
        return unicaInstancia;
    }

    public void insertar(Token token) {
        token.setType("PR");
        token.setDescription("Palabra reservada");
        tabla.put(token.getLexema(),token);
    }

    // Método para cargar palabras reservadas desde un archivo
    public void cargarDesdeArchivo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/PalabrasReservadas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separar palabra reservada y código
                String[] partes = linea.split(" ");
                if (partes.length == 2) {
                    String palabra = partes[0].toLowerCase();
                    int codigo = Integer.parseInt(partes[1]);
                    Token token = new Token(codigo, palabra);
                    this.insertar(token);
                }
            }
        }
    }
}
