package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoMultiplicacion extends NodoCompuestoBinario{

    public NodoMultiplicacion(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        String idDer = devolverId(hijos[DER]);
        String auxiliarUtilizado = "aux";

        if (((NodoConcreto)hijos[IZQ]).getTipo().equals("UINTEGER")){
            auxiliarUtilizado += GeneradorCodigo.siguienteAuxEntero();
            codigo = 
                    "MOV AX, " + idIzq + "\n" +       // Cargar el operando izquierdo en AX
                    "MOV "+ auxiliarUtilizado +", "+ idDer + "\n" + // Cargar el operando derecho en BX
                    "MUL " + auxiliarUtilizado + "\n";        // Multiplicar AX por idDer (resultado en DX:AX)
                    auxiliarUtilizado = "aux" + GeneradorCodigo.siguienteAuxEntero();
                    codigo += ("MOV " + auxiliarUtilizado + ", AX\n"); // Si no hay overflow, guardar el resultado en AX

        } else{
            auxiliarUtilizado += GeneradorCodigo.siguienteAuxDoble();
            codigo ="FLD " + idIzq + "\n" +
                    "FMUL " + idDer + "\n" +
                    "FSTP "+ auxiliarUtilizado + "\n";
        }
        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }
}
