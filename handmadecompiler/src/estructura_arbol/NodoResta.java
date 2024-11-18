package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoResta extends NodoCompuestoBinario{

    public NodoResta(String valor, Nodo izq, Nodo der) {
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
                    "SUB AX," + idDer + "\n" +
                    "MOV "+ auxiliarUtilizado + ", AX" + "\n";
            //TODO: NO PUEDE DAR MENOR QUE CERO
        } else{
            auxiliarUtilizado += GeneradorCodigo.siguienteAuxDoble();
            if(idDer.contains("."))
                idDer = "@"+idDer.replace(".", "");
            if(idDer.contains("-"))
                idDer = idDer.replace("-", "N");
            if(idIzq.contains("."))
                idIzq = "@"+idIzq.replace(".", "");
            if(idIzq.contains("-"))
                idIzq = idIzq.replace("-", "N");
            codigo ="FLD " + idIzq + "\n" +
                    "FSUB " + idDer + "\n" +
                    "FSTP "+ auxiliarUtilizado + "\n";
        }
        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }
    
}
