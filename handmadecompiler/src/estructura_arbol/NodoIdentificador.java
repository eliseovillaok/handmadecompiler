package estructura_arbol;

// Representa una variable

class NodoIdentificador extends Nodo {
    private final String nombre;

    public NodoIdentificador(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void generarCodigo() {
        System.out.println("LOAD " + nombre);  // Cargar el valor de la variable
    }
}
