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
            codigo ="FLD " + idIzq + "\n" +
                    "FADD " + idDer + "\n" +
                    "FSTP "+ auxiliarUtilizado + "\n";
        }
        //TODO: CONTROLAR SI HAY OVERFLOW
        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }

}
