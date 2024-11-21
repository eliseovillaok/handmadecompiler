package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
import compilador.Par;
import compilador.Token;
import error.ErrorHandler;
/*  - Inicializar string
    - Agregar Caracter
*/

public class AS1 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS1();

	private AS1() {
	}

	public static AccionSemantica getInstance() { // Singleton
		return unicaInstancia;
	}

	public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion, int numeroLinea,
			AnalizadorLexico lexico) {
		if (simbolosReconocidos.length() == 0) {
			simbolosReconocidos.append(entrada);
		} else {
			ErrorHandler.addError("Error AS1");
		}
		return new Par(-1, new Token(-1, null));
	}
}
