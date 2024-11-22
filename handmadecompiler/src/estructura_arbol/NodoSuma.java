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
            codigo = "FLD " + idIzq + "\n" +          // Cargar el primer flotante en ST(0)
                    "FADD " + idDer + "\n" +         // Sumar el segundo flotante con ST(0) 
                    "FCOM sdword ptr limiteFloat\n" +            // Cargar el límite de un float en ST(0)
                    "FSTSW AX\n" +                     // Comparar ST(0) y ST(1) y eliminarlos
                    "SAHF\n" +                   // Almacenar el registro de estado de la FPU en AX
                    "JA E_OF_SUMA\n" +              // Saltar si el resultado excede el límite
                    "FSTP " + auxiliarUtilizado + "\n"; // Guardar el resultado si no hay overflow
                }   
        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }

}