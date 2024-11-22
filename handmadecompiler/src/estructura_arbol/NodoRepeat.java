package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoRepeat extends NodoCompuesto {

    String codigo = "";

    public NodoRepeat(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String generarCodigo() {
        /*
         * REPEAT
         * etiqueta1: --> etiqueta1
         * // CUERPO
         * CMP r1,r2
         * jle etiqueta2
         * JMP etiqueta1
         * etiqueta2:
         * 
         * apilo etiqueta1
         * apilo etiqueta2
         * desapilo etiqueta2
         * desapilo etiqueta1
         */

        // Se hace ésto porque la condicion nos da el proximo salto de manera inversa,
        // pero en REPEAT-UNTIL debe ser de manera original, respetar si es != saltar, y
        // esto no es asi porque
        // se pensó primero en condicion como generador de los saltos del if
        String primerEtiqueta = GeneradorCodigo.siguienteEtiqueta(); // etiqueta2
        String numeroExtraido = primerEtiqueta.replaceAll("\\D+", ""); // Elimina todo excepto dígitos
        int numero = Integer.parseInt(numeroExtraido) + 1; // Convierte el número extraído a entero (2)
        primerEtiqueta = primerEtiqueta.replaceAll("\\d+", "") + numero; // etiqueta3

        FileHandler.appendToFile(filePath, primerEtiqueta + ":\n");
        this.hijos[IZQ] = new NodoConcreto(hijos[IZQ].generarCodigo()); // etiqueta2
        this.hijos[DER] = new NodoConcreto(hijos[DER].generarCodigo());

        GeneradorCodigo.pilaEtiquetas.pop(); // La sacamos de la pila porque ya no es relevante
        return "";
    }

}
