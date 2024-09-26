package acciones_semanticas;

import java.io.BufferedReader;

import compilador.Par;
import compilador.Token;

//interfaz de las acciones semánticas
public interface AccionSemantica {
    //public static int numeroID = 270;
    public abstract Par<Integer, Token> ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion, int numeroLinea);
}
