package compilador;

public class TablaTipos {

    private static volatile TablaTipos unicaInstancia = new TablaTipos();

    // Definimos los índices para los tipos
    public static final int UINTEGER = 0;
    public static final int SINGLE = 1;

    // Matriz de compatibilidad para asignaciones
    private int[][] compatibilidadTipos;

    private TablaTipos() {
        compatibilidadTipos = new int[2][2];

        // Inicializamos la matriz de compatibilidad
        compatibilidadTipos[UINTEGER][UINTEGER] = 1;
        compatibilidadTipos[SINGLE][SINGLE] = 1;
        compatibilidadTipos[UINTEGER][SINGLE] = 0;
        compatibilidadTipos[SINGLE][UINTEGER] = 0;
    }

    public static TablaTipos getInstance() { // Singleton
        return unicaInstancia;
    }

    // Método para verificar la compatibilidad de dos tipos
    public boolean sonCompatibles(int tipo1, int tipo2) {
        return compatibilidadTipos[tipo1][tipo2] == 1;
    }

    // Método para obtener una representación de texto del tipo (opcional)
    public String obtenerNombreTipo(int tipo) {
        switch (tipo) {
            case UINTEGER: return "UINTEGER";
            case SINGLE: return "SINGLE";
            default: return "DESCONOCIDO";
        }
    }


}
