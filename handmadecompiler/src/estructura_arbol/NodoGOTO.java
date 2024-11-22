package estructura_arbol;
import manejo_archivos.*;
public class NodoGOTO extends NodoCompuesto{

    public NodoGOTO(String valor, Nodo izq, Nodo der){
        super(valor,izq,der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]).substring(0);
        String codigo = "JMP " + idIzq;
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }
}