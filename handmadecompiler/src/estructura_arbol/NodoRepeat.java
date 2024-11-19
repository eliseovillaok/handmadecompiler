package estructura_arbol;

import compilador.GeneradorCodigo;
import manejo_archivos.FileHandler;

public class NodoRepeat extends NodoCompuesto{

    String codigo = "";

    public NodoRepeat(String valor,Nodo izq,Nodo der){
        super(valor, izq, der);   
    }

    @Override
    public String generarCodigo(){
    /*REPEAT
	etiqueta1: --> etiqueta1
	// CUERPO	
	CMP r1,r2
	jle etiqueta2
	JMP etiqueta1
	etiqueta2:
	
	apilo etiqueta1
	apilo etiqueta2
	desapilo etiqueta2
	desapilo etiqueta1 */
        String primerEtiqueta = GeneradorCodigo.siguienteEtiqueta();
        GeneradorCodigo.pilaEtiquetas.push(primerEtiqueta);
        FileHandler.appendToFile(filePath, primerEtiqueta + ":\n");
        this.hijos[IZQ] = new NodoConcreto(hijos[IZQ].generarCodigo());
        this.hijos[DER] = new NodoConcreto(hijos[DER].generarCodigo());
        String segundaEtiqueta = GeneradorCodigo.pilaEtiquetas.pop();
        primerEtiqueta = GeneradorCodigo.pilaEtiquetas.pop();
        codigo = "JMP " + primerEtiqueta + "\n" + segundaEtiqueta + ":\n";
        FileHandler.appendToFile(filePath, codigo);
        return "";
    }

    
}
