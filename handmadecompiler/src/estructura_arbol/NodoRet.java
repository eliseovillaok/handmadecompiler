package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoRet extends NodoCompuesto{

    public NodoRet(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    public String implementacion(String tipoRetorno){
        String idIzq = devolverId(hijos[IZQ]);
        if(tipoRetorno.equalsIgnoreCase("UINTEGER")){
            codigo = "mov ax, " + idIzq + "\n";
            codigo += "mov retEntero, ax\n";
        }else{
            codigo = "mov eax, " + idIzq + "\n";
            codigo += "mov retSingle, eax\n";
        }
        
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }

    @Override
    public String generarCodigo(String tipoRetorno){
        return implementacion(tipoRetorno);
    }
    
}
