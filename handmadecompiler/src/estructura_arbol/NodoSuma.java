package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoSuma extends NodoCompuestoBinario{

    public NodoSuma(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        String idDer = devolverId(hijos[DER]);
        String auxiliarUtilizado = "aux";

        if (((NodoConcreto)hijos[IZQ]).getTipo().equals("UINTEGER")){
            auxiliarUtilizado += GeneradorCodigo.siguienteAuxEntero();
            codigo ="MOV AX, " + idIzq + "\n" +
                    "ADD AX, " + idDer + "\n" +
                    "MOV " + auxiliarUtilizado + ", AX" + "\n";
        }else{
            auxiliarUtilizado += GeneradorCodigo.siguienteAuxDoble();
            codigo = "FLD " + idIzq + "\n" +         // Cargar el primer flotante en ST(0)
                    "FADD " + idDer + "\n" +         // Sumar el segundo flotante con ST(0)
                    "FLD ST(0)\n" +                  // Duplicar el resultado en ST(0) para comparación
                    "FLDZ\n" +                       // Cargar cero en ST(0)
                    "FADD ST, ST\n" +                // Multiplicar por dos para obtener +Inf si hubo overflow
                    "FXCH\n" +                       // Intercambiar ST(0) con el resultado duplicado
                    "FCOMPP\n" +                     // Comparar ST(0) y ST(1) y eliminar ambos
                    "FSTSW AX\n" +                   // Almacenar el registro de estado en AX
                    "SAHF\n" +                       // Cargar los flags de comparación en el registro de flags
                    "JP ERROR_OVERFLOW_SUMA\n" +          // Comprobar si la comparación dio infinito
                    "FSTP " + auxiliarUtilizado + "\n"; // Guardar el resultado si no hay overflow
                }   
        //TODO: Agregar el manejo de errores por overflow
        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }

}
