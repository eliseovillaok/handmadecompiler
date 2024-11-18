package estructura_arbol;

import manejo_archivos.FileHandler;
import compilador.TablaSimbolos;

public class NodoAsignacion extends NodoCompuestoBinario{

    private TablaSimbolos ts = TablaSimbolos.getInstance();

    public NodoAsignacion(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        String idDer = devolverId(hijos[DER]);

        if (((NodoConcreto)hijos[IZQ]).getTipo().equals("UINTEGER")){
            codigo ="MOV AX, " + idDer + "\n" +
                    "MOV " + idIzq + ", AX" + "\n";
        }
        else{
            if (ts.buscar(idDer) != null)
                idDer = "@"+idDer.replace(".", "");
            codigo ="FLD " + idDer + "\n" +
                    "FSTP " + idIzq + "\n";
        }   
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }
    
}
