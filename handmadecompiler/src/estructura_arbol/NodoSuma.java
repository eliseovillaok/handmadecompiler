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
        String auxiliarUtilizado = "aux" + GeneradorCodigo.siguienteAuxEntero();

        if (((NodoConcreto)hijos[IZQ]).getTipo().equals("UINTEGER"))
            codigo ="MOV AX, " + idIzq + "\n" +
                    "ADD AX," + idDer + "\n" +
                    "MOV " + auxiliarUtilizado + ", AX" + "\n";
        // if (((NodoConcreto)hijos[IZQ]).getTipo().equals("SINGLE"))
        // codigo ="MOV AX, " + idIzq + "\n" +
        //         "ADD AX," + idDer + "\n" +
        //         "MOV aux, AX" + "\n";
        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }

}
