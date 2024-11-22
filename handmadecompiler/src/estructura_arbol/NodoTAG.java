package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoTAG extends NodoCompuesto{
    
    public NodoTAG(String valor, Nodo izq, Nodo der){
        super(valor, izq, der);
    }

    @Override
    public String generarCodigo(){
        FileHandler.appendToFile(GeneradorCodigo.filePathAssembly, valor.replaceAll(":","_") + ":\n");
        return "";
    }

    @Override
    public void imprimirNodo(StringBuilder sb, String prefijo, boolean esUltimo) {
    }
    
}
