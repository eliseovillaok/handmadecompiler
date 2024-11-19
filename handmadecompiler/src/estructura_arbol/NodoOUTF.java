package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoOUTF extends NodoCompuesto{
    
    public NodoOUTF(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        // verificar que el valor sea integer o float
        if(((NodoConcreto)hijos[IZQ]).getTipo() != null){
            if( ((NodoConcreto)hijos[IZQ]).getTipo().equalsIgnoreCase("UINTEGER") ){
                codigo = "invoke printf, cfm$(\"%u\\n\"), " + idIzq + "\n";
            }else if (((NodoConcreto)hijos[IZQ]).getTipo().equalsIgnoreCase("SINGLE") ){
                codigo = "FLD " + idIzq + "\n" +
                            "FST impresionFloat\n" +
                            "invoke printf, cfm$(\"%.5Lf\\n\"), impresionFloat\n";
            }
        }
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }

}
