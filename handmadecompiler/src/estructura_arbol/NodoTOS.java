package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoTOS extends NodoCompuesto{

    public NodoTOS(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der, "SINGLE");
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        String auxiliarUtilizado = "aux";

        auxiliarUtilizado += GeneradorCodigo.siguienteAuxEntero();
        if(!idIzq.contains("_"))
            codigo = "MOV " + auxiliarUtilizado + ", " + idIzq + "\n" +
                     "MOVZX EAX, " + auxiliarUtilizado + "\n";          
        else
            codigo = "MOVZX EAX, " + idIzq + "\n";

        
        auxiliarUtilizado = "aux" + GeneradorCodigo.siguienteAuxDoble();
        codigo +="MOV " + auxiliarUtilizado + ", EAX" + "\n"+
                "FILD " + auxiliarUtilizado + "\n" +
                "FSTP " + auxiliarUtilizado + "\n";


        FileHandler.appendToFile(filePath, codigo);
        return auxiliarUtilizado;
    }

}