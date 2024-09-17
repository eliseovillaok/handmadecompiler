package compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import acciones_semanticas.AccionSemantica;

public class TablaPalabrasReservadas { //
	private static volatile TablaPalabrasReservadas unicaInstancia = new TablaPalabrasReservadas(); 
    private Map<String, PalabraReservada> tabla;
    private TablaPalabrasReservadas() {
        this.tabla = new HashMap<>();
    }

    // Método para cargar palabras reservadas desde un archivo
    public void cargarDesdeArchivo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("handmadecompiler\\src\\PalabrasReservadas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separar palabra reservada y código
                String[] partes = linea.split(" ");
                if (partes.length == 2) {
                    String palabra = partes[0];
                    String codigo = partes[1];
                    // Agregar a la tabla
                    tabla.put(palabra, new PalabraReservada(palabra, codigo));
                }
            }
        }
    }

    // Método para obtener una palabra reservada
    public PalabraReservada obtenerPalabraReservada(String palabra) {
        return tabla.get(palabra);
    }

    // Método para imprimir la tabla
    public void imprimirTabla() {
        for (PalabraReservada pr : tabla.values()) {
            System.out.println(pr);
        }
    }

    public static TablaPalabrasReservadas getInstance() { // Singleton
    	return unicaInstancia;
    }
	
    private final Map<String, Token> simbolos = new HashMap<>();

    public Token buscar(String lexema) {
    	return simbolos.get(lexema);
    }

    public void imprimir() {
        Set<String> keys = simbolos.keySet();
        for (String key : keys) {
            System.out.println(simbolos.get(key));
        }
    }

}

// Clase que representa una palabra reservada
class PalabraReservada {
    private String palabra;
    private String codigo;

    public PalabraReservada(String palabra, String codigo) {
        this.palabra = palabra;
        this.codigo = codigo;
    }

    public String getPalabra() {
        return palabra;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "PalabraReservada{" +
                "palabra='" + palabra + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
