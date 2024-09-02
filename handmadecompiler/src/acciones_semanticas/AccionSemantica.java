package acciones_semanticas;
import compilador.*;

//interfaz de las acciones semánticas
public interface AccionSemantica {
    public void ejecutar(AnalizadorLexico lexico/*, Pueden haber mas parametros*/);
}
