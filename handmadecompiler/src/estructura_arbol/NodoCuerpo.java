package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoCuerpo extends NodoCompuesto {
    
    String codigo = "";

    public NodoCuerpo(String valor,Nodo izq,Nodo der){
        super(valor, izq, der);   
    }

    @Override
    public String generarCodigo(){
        boolean izq = false;
        boolean der = false;

        if (hijos[IZQ] == null && hijos[DER] == null)
            return "";  
        if (hijos[IZQ] != null && hijos[DER] == null){
            izq = true;
        }
        else if (hijos[IZQ] == null && hijos[DER] != null){
            der = true;
        }
        else {
            izq = true;
            der = true;
        }

        if (izq && (hijos[IZQ] instanceof NodoConcreto))
            izq = false;
        if (der && (hijos[DER] instanceof NodoConcreto))
            der = false;
        
        if (izq){
            this.hijos[IZQ] = new NodoConcreto(hijos[IZQ].generarCodigo());
            String etiquetaThen = GeneradorCodigo.pilaEtiquetas.pop();
            if(hijos[DER] != null){
                String etiquetaElse = GeneradorCodigo.siguienteEtiqueta();
                GeneradorCodigo.pilaEtiquetas.push(etiquetaElse);
                codigo = "JMP " + etiquetaElse + "\n";
            }
            codigo += etiquetaThen + ":\n";
            FileHandler.appendToFile(filePath, codigo);
        }
        if (der){
            this.hijos[DER] = new NodoConcreto(hijos[DER].generarCodigo());
            codigo = GeneradorCodigo.pilaEtiquetas.pop() + ":\n";
            FileHandler.appendToFile(filePath, codigo);
        }
        return "";
    }

}
