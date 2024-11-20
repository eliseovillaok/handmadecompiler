package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoRet extends NodoCompuesto{

    public String tipoFuncion;

    public NodoRet(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public void propagarTipoFuncion(String tipoFuncion) {
        this.tipoFuncion = tipoFuncion;
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        if (this.tipoFuncion.equalsIgnoreCase("UINTEGER")){
            codigo = "mov ax, " + idIzq + "\n";
            codigo += "mov RetUint, ax\n";
        } else{
            codigo = "mov eax, " + idIzq + "\n";
            codigo += "mov RetSingle, eax\n";
        }
        FileHandler.appendToFile(filePath, codigo);
        return codigo;
    }
    
}
