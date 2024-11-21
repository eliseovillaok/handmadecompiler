package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoRet extends NodoCompuesto{

    public NodoRet(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        codigo = "mov eax, " + idIzq + "\n";
        codigo += "mov RetVal, eax\n";
        FileHandler.appendToFile(filePath, codigo);
        return codigo;
    }
    
}
