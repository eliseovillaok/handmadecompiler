package acciones_semanticas;

/*  - Inicializar string (se reserva 15 bytes longitud permitida para identificadores)
    - Chequear que sea letra la que se va a agregar
    - AS2 
*/

public class AS1 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS1(); 
    private AS1() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
    
	public void ejecutar(StringBuilder simbolosReconocidos, char entrada) {
		if (simbolosReconocidos == null) {
			simbolosReconocidos = new StringBuilder(entrada);
		}
	}
}
