package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
import compilador.Par;

// Concatena un 0 al final del string (hexa)

public class AS4 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS4();
	private AccionSemantica as5 = AS5.getInstance();

	private AS4() {
	}

	public static AccionSemantica getInstance() { // Singleton
		return unicaInstancia;
	}

	@Override
	public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion, int numeroLinea,
			AnalizadorLexico lex) {
		if (simbolosReconocidos.length() == 2)
			simbolosReconocidos.append('0');
		return as5.ejecutar(simbolosReconocidos, entrada, posicion, numeroLinea, lex);
	}
}