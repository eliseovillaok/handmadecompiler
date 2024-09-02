package acciones_semanticas;
import compilador.*;
import java.io.Reader;

//Inicializar un token que pueda contener hasta un char(2 Byte para operadores simples)

public class AS8 implements AccionSemantica {
    /*@Override
    public int ejecutar(Reader lector, StringBuilder token) {
        token.delete(0, token.length());
        token.append((char)leerSiguienteCaracter(lector));
        return AccionSemantica.TOKEN_ACTIVO;
    }*/
}