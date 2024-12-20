package acciones_semanticas;

import compilador.*;
import error.*;
import java.io.BufferedReader;

// Verifica rango de constantes y la devuelve o crea en la tabla de simbolos

public class AS5 implements AccionSemantica {
	private static volatile AccionSemantica unicaInstancia = new AS5();
	private final int NUMEROCTE_UINTEGER = 276;
	private final int NUMEROCTE_SINGLE = 277;
	private final int NUMEROCTE_HEXA = 278;
	private final float MIN_POSITIVE = 1.17549435e-38f;
	private final float MAX_POSITIVE = 3.40282347e38f;
	private Token tokenRetorno;
	private TablaSimbolos ts = TablaSimbolos.getInstance();

	private AS5() {
	}

	public static AccionSemantica getInstance() { // Singleton
		return unicaInstancia;
	}

	private int tipoCTE(String lexema) {
		if (lexema.contains("."))
			return NUMEROCTE_SINGLE;
		if (lexema.contains("x"))
			return NUMEROCTE_HEXA;

		return NUMEROCTE_UINTEGER;
	}

	private boolean cumpleRango(String lexema, int numeroLinea) {
		boolean cumple = false;

		switch (tipoCTE(lexema)) {
			case NUMEROCTE_SINGLE: {
				try {
					// Convierte el string a float
					float numero_single = Float.parseFloat(lexema.replace('s', 'e').concat("f")); // Comprobamos rango
																									// cambiando "s" por
																									// "e"
																									// temporalmente, no
																									// se guarda...

					// Verifica si es infinito
					if (Float.isInfinite(numero_single)) {
						ErrorHandler.addError("Error: Linea " + numeroLinea + " el número " + lexema
								+ " es infinito (fuera del rango permitido para single).");
					} else {

						// Verifica si está dentro del rango
						if ((numero_single >= MIN_POSITIVE && numero_single <= MAX_POSITIVE) ||
								numero_single == 0.0f) {
							cumple = true;
						} else {
							ErrorHandler.addError(
									"Error: Linea " + numeroLinea + " constante single " + lexema + "fuera de rango.");
						}
					}
				} catch (NumberFormatException e) {
					ErrorHandler.addError("El token reconocido " + lexema + " no es un número válido.");
				}
				break;
			}
			case NUMEROCTE_HEXA: {
				if ((lexema.length() > 6))
					ErrorHandler.addError("Error: Linea " + numeroLinea + " constante hexadecimal " + lexema
							+ " positiva fuera de rango");
				else
					cumple = true;
				break;
			}
			case NUMEROCTE_UINTEGER: {
				try {
					if (Integer.parseInt(lexema) <= 65535) // < 2¹⁶-1^
						cumple = true;
					else {
						ErrorHandler.addError("Error: Linea " + numeroLinea + " constante entera positiva " + lexema
								+ " fuera de rango.");
					}
				} catch (NumberFormatException e) {
					ErrorHandler.addError("Error: Linea " + numeroLinea + " constante entera positiva " + lexema
							+ " fuera de rango.");
				}
				break;
			}
			default:
				break;
		}
		return cumple;
	}

	@Override
	public Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion, int numeroLinea,
			AnalizadorLexico lex) {
		// Vuelvo a la marca de la posicion anterior
		try {
			posicion.reset();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		String s = simbolosReconocidos.toString();
		if ((tokenRetorno = ts.buscar(s)) != null) {
			// Si está, devuelvo ID + Punt TS + *Tipo.*
			return lex.retornar(tokenRetorno);
		}
		// Si no está, doy de alta en la TS
		// Devuelvo ID + Punt TS + *Tipo.*
		// Verifico longitud y envío un warning si la supera
		if (cumpleRango(s, numeroLinea)) {
			tokenRetorno = new Token();
			switch (tipoCTE(s)) { // Creamos el token especifico para cada tipo de constante.
				case NUMEROCTE_SINGLE: {
					tokenRetorno.setId(NUMEROCTE_SINGLE);
					tokenRetorno.setType("SINGLE");
					break;
				}
				case NUMEROCTE_HEXA: {
					tokenRetorno.setId(NUMEROCTE_HEXA);
					tokenRetorno.setType("HEXADECIMAL");
					break;
				}
				case NUMEROCTE_UINTEGER: {
					tokenRetorno.setId(NUMEROCTE_UINTEGER);
					tokenRetorno.setType("UINTEGER");
					break;
				}
				default:
					break;
			}
			tokenRetorno.setDescription("Constante");
			tokenRetorno.setLexeme(s);
			// Insertamos y retornamos.
			ts.insertar(tokenRetorno);
			return lex.retornar(tokenRetorno);
		}
		return new Par(-1, new Token(-1, "error"));
	}

}
