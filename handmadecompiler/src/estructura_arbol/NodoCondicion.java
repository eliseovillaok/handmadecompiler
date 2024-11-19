package estructura_arbol;


import compilador.GeneradorCodigo;

public class NodoCondicion extends NodoCompuestoBinario{
    
    public NodoCondicion(String valor, Nodo izq , Nodo der) {
        super(valor, izq, der);
    }

    @Override
    public String implementacion(){
        String idIzq = devolverId(hijos[IZQ]);
        String idDer = devolverId(hijos[DER]);
        String etiqueta = GeneradorCodigo.siguienteEtiqueta();
        boolean esEntero = false;
        String codigo = "";

        if (((NodoConcreto)hijos[IZQ]).getTipo().equalsIgnoreCase("UINTEGER")){
            esEntero = true;
            codigo += "MOV AX, " + idIzq + "\n" +
                      "CMP AX, " + idDer + "\n";
        }else{
            codigo = "MOV EAX, " + idIzq + "\n" +
                    "CMP EAX, " + idDer + "\n";
        }

        switch (valor) {
            case "<":
                if (esEntero)
                    codigo += "JNB " + etiqueta + "\n";
                else
                    codigo += "JNL " + etiqueta + "\n";
            break;
            case ">":
                if (esEntero)
                    codigo += "JBE " + etiqueta + "\n";
                else
                    codigo += "JLE " + etiqueta + "\n";
            break;
            case "<=":
                if (esEntero)
                    codigo += "JNBE " + etiqueta + "\n";
                else
                    codigo += "JNLE " + etiqueta + "\n";
            break;
            case ">=":
                if (esEntero)
                    codigo += "JB " + etiqueta + "\n";
                else
                    codigo += "JL " + etiqueta + "\n";
            break;
            case "=":
                codigo += "JNE " + etiqueta + "\n";
            break;
            case "!=":
                codigo += "JE " + etiqueta + "\n";
            break;
            default:
                return "";
        }
        GeneradorCodigo.pilaEtiquetas.push(etiqueta);
        manejo_archivos.FileHandler.appendToFile(filePath, codigo);
        return "";
    }
}
