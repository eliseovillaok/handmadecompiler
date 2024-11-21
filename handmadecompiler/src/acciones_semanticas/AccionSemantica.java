package acciones_semanticas;

import java.io.BufferedReader;

import compilador.AnalizadorLexico;
import compilador.Par;

// Interfaz de las acciones semánticas
public interface AccionSemantica {
    public abstract Par ejecutar(StringBuilder simbolosReconocidos, char entrada, BufferedReader posicion,
            int numeroLinea, AnalizadorLexico lexico);
}
