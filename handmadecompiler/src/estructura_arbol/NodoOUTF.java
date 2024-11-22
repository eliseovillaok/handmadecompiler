package estructura_arbol;

import manejo_archivos.FileHandler;

public class NodoOUTF extends NodoCompuesto{
    
    public NodoOUTF(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        System.out.println("IDIZQ OUTF: " + idIzq);
        if(((NodoConcreto)hijos[IZQ]).getTipo() != null){
            String tipo = ((NodoConcreto)hijos[IZQ]).getTipo();
            if( (tipo.equalsIgnoreCase("UINTEGER")) ){
                codigo = "invoke printf, cfm$(\"%u\\n\"), " + idIzq + "\n";
            }else if (tipo.equalsIgnoreCase("SINGLE") ){
                codigo = "FLD " + idIzq + "\n" +
                            "FST impresionFloat\n" +
                            "invoke printf, cfm$(\"%.5Lf\\n\"), impresionFloat\n";
            }else if (tipo.equalsIgnoreCase("CADENA") ){
                codigo = "invoke printf, ADDR " + idIzq + "\n";
            }
        }
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }

}
