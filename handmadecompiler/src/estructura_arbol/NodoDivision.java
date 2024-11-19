package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoDivision extends NodoCompuestoBinario {
    
    public NodoDivision(String valor, Nodo izq, Nodo der) {
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
                    "MOV DX, 0" + "\n" + // Limpiar DX
                    "MOV "+ auxiliarUtilizado +", "+ idDer + "\n" + // Cargar el operando derecho en BX
                    "DIV " + auxiliarUtilizado + "\n";        // Multiplicar AX por idDer (resultado en DX:AX)
                    auxiliarUtilizado = "aux" + GeneradorCodigo.siguienteAuxEntero();
                    codigo += ("MOV " + auxiliarUtilizado + ", AX\n"); // Si no hay overflow, guardar el resultado en AX

        } else{
            auxiliarUtilizado += GeneradorCodigo.siguienteAuxDoble();
            codigo ="FLD " + idIzq + "\n" +
                    "FDIV " + idDer + "\n" +
                    "FSTP "+ auxiliarUtilizado + "\n";
        }
        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }
}
