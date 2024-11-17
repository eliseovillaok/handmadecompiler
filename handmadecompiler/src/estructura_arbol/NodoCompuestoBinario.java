package estructura_arbol;

import compilador.Parser;
import manejo_archivos.FileHandler;

public class NodoCompuestoBinario extends NodoCompuesto {

    public NodoCompuestoBinario(String valor, Nodo izq, Nodo der) {
        super(valor, izq, der);
        this.tipo = comprobarTipos();
    }

    // Genera el codigo de sus dos hijos (delega la accion de generar codigo)
    public String generarCodigo() {
        Nodo izquierda = hijos[IZQ]; // Lado izquierdo
        Nodo derecha = hijos[DER]; // Lado derecho
        String codigo = "";
        if (izquierda instanceof NodoConcreto && derecha instanceof NodoConcreto) {
            switch (valor) {
                case "+":
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                            "ADD R1, _" + derecha.generarCodigo() + "\n" +
                            "MOV @aux,R1";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                    case "-":
                    // Generación de código para la resta
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "SUB R1, _" + derecha.generarCodigo() + "\n" +
                             "MOV @aux, R1";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case "*":
                    // Generación de código para la multiplicación
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "MOV R2, _" + derecha.generarCodigo() + "\n" +
                             "MUL R1, R2\n" +
                             "MOV @aux, R1";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case "/":
                    // Generación de código para la división
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "MOV R2, _" + derecha.generarCodigo() + "\n" +
                             "DIV R1, R2\n" +
                             "MOV @aux, R1";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case ":=":
                    // Generación de código para la asignación
                    codigo = "MOV R1, _" + derecha.generarCodigo() + "\n" +
                             "MOV _" + izquierda.generarCodigo() + ", R1";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case "!=":
                    // Generación de código para la desigualdad
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "CMP R1, _" + derecha.generarCodigo() + "\n" +
                             "JNE @aux"; // Usa etiqueta auxiliar para saltos
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case "<=":
                    // Comparación menor o igual
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "CMP R1, _" + derecha.generarCodigo() + "\n" +
                             "JLE @aux";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case ">=":
                    // Comparación mayor o igual
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "CMP R1, _" + derecha.generarCodigo() + "\n" +
                             "JGE @aux";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case ">":
                    // Comparación mayor
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "CMP R1, _" + derecha.generarCodigo() + "\n" +
                             "JG @aux";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case "<":
                    // Comparación menor
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "CMP R1, _" + derecha.generarCodigo() + "\n" +
                             "JL @aux";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                
                case "=":
                    // Igualdad
                    codigo = "MOV R1, _" + izquierda.generarCodigo() + "\n" +
                             "CMP R1, _" + derecha.generarCodigo() + "\n" +
                             "JE @aux";
                    FileHandler.appendToFile(filePath, codigo);
                    return "registro";
                

                default:
                    break;
            }
        } else if (!(izquierda instanceof NodoConcreto)){
            this.hijos[IZQ] = new NodoConcreto(izquierda.generarCodigo()); 
            this.generarCodigo();
        }
        else {
            this.hijos[DER] = new NodoConcreto(derecha.generarCodigo());
            this.generarCodigo();
        }
        return "";
    }

    public String comprobarTipos() {
        String tipoIzq = null;
        String tipoDer = null;
        if (hijos[IZQ] != null && hijos[DER] != null) {
            tipoIzq = hijos[IZQ].comprobarTipos();
            tipoDer = hijos[DER].comprobarTipos();
        }
        if (tipoIzq != null && tipoDer != null) {
            if (tipoIzq.equals(tipoDer)) {
                tipo = tipoIzq;
                return tipo;
            } else {
                Parser.yyerror("no coinciden los tipos");
            }
        } else {
            Parser.yyerror("falta un tipo");
        }
        return null;
    }

}
