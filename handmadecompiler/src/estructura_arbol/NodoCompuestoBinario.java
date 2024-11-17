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
                    break;
                case "-":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE -");
                    break;
                case "*":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE *");
                    break;
                case "/":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE /");
                    break;
                case ":=":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE :=");
                    break;
                case "!=":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE !=");
                    break;
                case "<=":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE <=");
                    break;
                case ">=":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE >=");
                    break;
                case ">":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE >");
                    break;
                case "<":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE <");
                    break;
                case "=":
                    FileHandler.appendToFile(filePath, "ASSEMBLER DE =");
                    break;

                default:
                    break;
            }
        } else if (!(izquierda instanceof NodoConcreto)){
            this.hijos[IZQ] = new NodoConcreto(izquierda.generarCodigo()); 
            this.generarCodigo();
            return "registro";
        }
        else {
            this.hijos[DER] = new NodoConcreto(derecha.generarCodigo());
            this.generarCodigo();
            return "registro";
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
