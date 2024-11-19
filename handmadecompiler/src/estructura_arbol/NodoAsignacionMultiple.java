package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoAsignacionMultiple extends NodoCompuestoBinario{
    
    public NodoAsignacionMultiple(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String[] vars = hijos[IZQ].generarCodigo().split(",");
        String[] exps = hijos[DER].generarCodigo().split(",");
        for (int i = 0; i < vars.length; i++) {
            vars[i] = "_" + vars[i].replaceAll(":", "_");
            if (((NodoConcreto)hijos[IZQ]).getTipo().equals("UINTEGER")){
                codigo ="MOV AX, " + exps[i] + "\n" +
                        "MOV " + vars[i] + ", AX" + "\n";
            }
            else{
                codigo ="FLD " + exps[i] + "\n" +
                        "FSTP " + vars[i] + "\n";
            }   
            FileHandler.appendToFile(filePath, codigo);
        }
        return "";
    }

}
