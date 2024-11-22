package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoInvocacionFuncion extends NodoCompuesto{

    private String funcionPadre = null;

    public NodoInvocacionFuncion(String valor, Nodo izq, Nodo der, String tipo) {
        super(valor, izq, der, tipo);
    }

    @Override
    public String generarCodigo(){
        String idIzq = devolverId(hijos[IZQ]);
        System.out.println("IDIZQ: " + idIzq);
        String valorNuevo = valor.substring(valor.lastIndexOf("_")+1, valor.length()); //INVOCACION_FUNCION_f1:program
        if (funcionPadre != null && funcionPadre.equals(valorNuevo)){
            codigo = "JMP E_RECURSION";
            FileHandler.appendToFile(filePath, codigo);
            return "";
        } else {
            valorNuevo = valorNuevo.replaceAll(":", "_");
            codigo += "invoke " + valorNuevo + ", " + idIzq + "\n";
            FileHandler.appendToFile(filePath, codigo);
            if (tipo.equalsIgnoreCase("UINTEGER")){
                return "RetUint";
            } else {
                return "RetSingle";
            }
        }
    }

    public void chequeoRecursion(String nombreFuncion) {
        this.funcionPadre = nombreFuncion;
    }

    @Override
    public String implementacion(){
        return "";
    }

}
