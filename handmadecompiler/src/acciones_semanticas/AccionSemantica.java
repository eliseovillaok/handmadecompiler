package acciones_semanticas;

import java.io.BufferedReader;

//interfaz de las acciones semánticas
public interface AccionSemantica {
    //public static int numeroID = 270;
    public abstract void ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion);
}
