import java.util.Hashtable;

public class Token {

    private int id;
    private String lexeme, description, type = "";
    private Hashtable<String, Object> atributos = new Hashtable<String, Object>();

    public Token(int id, String lexeme, String description) {
        this.id = id;
        this.lexeme = lexeme;
        this.description = description; 
    }

    public void addAtributo(String caract, Object val) {
        atributos.put(caract, val);
    }

    public String getAttributo(String caract) {
        return (String) atributos.get(caract);
    }

    public int getID() {
        return id;
    }

    public void setLexema(String lexema) {
        this.lexeme = lexema;
    }

    public String getLexema() {
        return lexeme;
    }

    public String getDescripcion() {
        return description;
    }

    public String toString() {
        return "Token: " + id + " | Lexeme: " + lexeme + " | " + "Description: " + description ;
    }
}