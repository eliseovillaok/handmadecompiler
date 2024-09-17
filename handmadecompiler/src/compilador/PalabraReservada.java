package compilador;

// Clase que representa una palabra reservada
public class PalabraReservada {
    private String palabra;
    private int codigo;

    public PalabraReservada(String palabra, int codigo) {
        this.palabra = palabra;
        this.codigo = codigo;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getCodigo() {
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
