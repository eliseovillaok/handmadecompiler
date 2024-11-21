package estructura_arbol;

import java.io.IOException;

import compilador.TablaSimbolos;
import compilador.Token;
import manejo_archivos.FileHandler;

public class NodoFuncion extends NodoCompuesto{

    private TablaSimbolos ts = TablaSimbolos.getInstance();

    public NodoFuncion(String valor, Nodo izq, Nodo der, String tipo) {
        super(valor, izq, der, tipo);
    }

    @Override
    public String generarCodigo(){
        String sufijo = valor.substring(0, valor.indexOf(":"));
        Token encontrado = ts.buscarParametro(sufijo);
        String parametro = "_" + encontrado.getLexema().replaceAll(":", "_");
        String tipo = encontrado.getType();
        String tipoAssembler = "";

        hijos[IZQ].propagarTipoFuncion(this.tipo);
        hijos[IZQ].chequeoRecursion(this.valor);

        if(tipo.equalsIgnoreCase("UINTEGER")){
            tipoAssembler = "WORD";
        } else if(tipo.equalsIgnoreCase("SINGLE")){
            tipoAssembler = "DWORD";
        }

        String valorNuevo = valor.replaceAll(":", "_");
        codigo = "" + valorNuevo + " proc " + parametro + ":" + tipoAssembler;
        FileHandler.appendToFile(filePath, codigo);
        this.hijos[IZQ] = new NodoConcreto(hijos[IZQ].generarCodigo());
        codigo = "ret\n";
        codigo += valorNuevo + " endp\n";
        FileHandler.appendToFile(filePath, codigo);
        try {
            FileHandler.insertarFunciones("src/salida/salida.asm", valorNuevo + " proc " + parametro + ":" + tipoAssembler, valorNuevo + " endp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
