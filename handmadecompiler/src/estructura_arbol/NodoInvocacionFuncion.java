package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoInvocacionFuncion extends NodoCompuesto{

    public NodoInvocacionFuncion(String valor, Nodo izq, Nodo der, String tipo) {
        super(valor, izq, der, tipo);
    }

    @Override
    public String generarCodigo(){
        String idIzq = devolverId(hijos[IZQ]);
        String valorNuevo = valor.substring(valor.lastIndexOf("_")+1, valor.length()); //INVOCACION_FUNCION_f1:program
        valorNuevo = valorNuevo.replaceAll(":", "_");
        codigo += "invoke " + valorNuevo + ", " + idIzq + "\n";
        FileHandler.appendToFile(filePath, codigo);
        if (tipo.equalsIgnoreCase("UINTEGER")){
            return "RetUint";
        } else {
            return "RetSingle";
        }
    }

    @Override
    public String implementacion(){
        return "";
    }

}
