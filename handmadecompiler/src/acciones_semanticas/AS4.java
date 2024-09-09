package acciones_semanticas;

/*  - Inicializar string (se reserva 16 bits longitud permitida)
    - Chequear que sea digito
    - AS2 */

public class AS4 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS4(); 
    private AS4() {}
    public static AccionSemantica getInstance() { // Singleton
    	return unicaInstancia;
    }
	@Override
   public void ejecutar(StringBuilder simbolosReconocidos, char entrada) {
	simbolosReconocidos.append('0');
	AccionSemantica accion_semantica_5 = AS5.getInstance();
	accion_semantica_5.ejecutar(simbolosReconocidos, entrada);
   }
}