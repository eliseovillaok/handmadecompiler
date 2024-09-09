package acciones_semanticas;
import compilador.*;

//interfaz de las acciones semánticas
public interface AccionSemantica {
    public abstract void ejecutar(StringBuilder simbolosReconocidos, char entrada);
}
