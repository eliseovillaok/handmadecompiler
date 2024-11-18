package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoAsignacion extends NodoCompuestoBinario{

    public NodoAsignacion(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        String idDer = devolverId(hijos[DER]);

        if (((NodoConcreto)hijos[IZQ]).getTipo().equals("UINTEGER"))
            codigo ="MOV AX, " + idDer + "\n" +
                    "MOV " + idIzq + ", AX" + "\n";
        if (((NodoConcreto)hijos[IZQ]).getTipo().equals("SINGLE"))
            //PENDIENTE DE IMPLEMENTAR CORRECTAMENTE
            codigo ="MOV EAX," + idDer + "\n" +
                    "MOV " + idIzq + ", EAX \n" +
                    "MOV aux32, EAX" + "\n"; //HARDCODEADO PARA PRUEBA
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }
    
}
