package acciones_semanticas;

public class ASE implements AccionSemantica {
    private static volatile AccionSemantica unicaInstancia = new ASE(); 
    private ASE() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    @Override
    public void ejecutar(StringBuilder simbolosReconocidos, char entrada) {
        // TODO Auto-generated method stub
        System.err.println("Error: Caracter no reconocido: " + entrada);
    }
    
}
