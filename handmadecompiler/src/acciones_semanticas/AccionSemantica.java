package acciones_semanticas;

import java.io.FileReader;

//interfaz de las acciones semánticas
public interface AccionSemantica {
    //public static int numeroID = 270;
    public abstract void ejecutar(StringBuilder simbolosReconocidos, char entrada, FileReader posicion);
}
