package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoRepeat extends NodoCompuesto{

    String codigo = "";

    public NodoRepeat(String valor,Nodo izq,Nodo der){
        super(valor, izq, der);   
    }

    @Override
    public String generarCodigo(String tipoRetorno){
        String primerEtiqueta = GeneradorCodigo.siguienteEtiqueta();
        GeneradorCodigo.pilaEtiquetas.push(primerEtiqueta);
        FileHandler.appendToFile(filePath, primerEtiqueta + ":\n");
        this.hijos[IZQ] = new NodoConcreto(hijos[IZQ].generarCodigo(tipoRetorno));
        this.hijos[DER] = new NodoConcreto(hijos[DER].generarCodigo(tipoRetorno));
        String segundaEtiqueta = GeneradorCodigo.pilaEtiquetas.pop();
        primerEtiqueta = GeneradorCodigo.pilaEtiquetas.pop();
        codigo = "JMP " + primerEtiqueta + "\n" + segundaEtiqueta + ":\n";
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }

    
}
