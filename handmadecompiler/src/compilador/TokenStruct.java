package compilador;

import java.util.NavigableMap;
import java.util.TreeMap;

public class TokenStruct extends Token{

    private NavigableMap<String,String> variables = null;
    
    TokenStruct (int id, String lexeme, String variablesTipos) {
        super(id, lexeme, "");
        this.setUso("Struct");
        variables = new TreeMap<String,String>();
        String[] variablesArray = variablesTipos.split("\\.");
        String[] nombres = variablesArray[0].split(",");
        String[] tipos = variablesArray[1].split(",");
        for (int i = 0; i < nombres.length; i++) {
            addVariable(nombres[i], tipos[i]);
        }
    }

    public void addVariable(String nombre, String tipo) {
        variables.put(nombre, tipo);
    }
    
    public int getCantComponentes() {
        return variables.size();
    }

    public NavigableMap<String,String> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return super.toString() + " | Variables: " + variables.toString() + " | Cantidad de componentes: "+ getCantComponentes();
    }
}
