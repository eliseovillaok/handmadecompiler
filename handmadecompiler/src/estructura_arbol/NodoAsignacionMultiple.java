package estructura_arbol;

public class NodoAsignacionMultiple extends NodoCompuestoBinario{
    
    private String variables;
    private String expresiones;
    
    public NodoAsignacionMultiple(String valor, Nodo izq, Nodo der, String variables, String expresiones) {
        super(valor, izq, der);
        this.variables = variables;
        this.expresiones = expresiones;
    }

    @Override
    public String implementacion(){
        String[] vars = variables.split(",");
        String[] exps = expresiones.split(",");
        String codigo = "";
        for (int i = 0; i < vars.length; i++) {
            System.out.println(hijos[IZQ].generarCodigo());
            System.out.println(hijos[DER].generarCodigo());
        }
        return codigo;
    }

}
