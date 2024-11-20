package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoInvocacionFuncion extends NodoCompuesto{
    String tipoRetorno = "";
    public NodoInvocacionFuncion(String valor, Nodo izq, Nodo der, String tipo,String tipoRetorno) {
        super(valor, izq, der, tipo);
        this.tipoRetorno = tipoRetorno;
    }

    @Override
    public String generarCodigo(String tipoRetorno){
        String idIzq = devolverId(hijos[IZQ]);
        String valorNuevo = valor.substring(valor.lastIndexOf("_")+1, valor.length()); //INVOCACION_FUNCION_f1:program
        valorNuevo = valorNuevo.replaceAll(":", "_");
        codigo += "invoke " + valorNuevo + ", " + idIzq + "\n";
        FileHandler.appendToFile(filePath, codigo);
        if(this.tipoRetorno.equalsIgnoreCase("UINTEGER"))
            return "retEntero";
        else
            return "retSingle";
    }

    @Override
    public String implementacion(){
        return "";
    }

}
