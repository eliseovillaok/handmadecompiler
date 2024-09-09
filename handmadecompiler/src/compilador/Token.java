package compilador;

public class Token {
    private int id;
    private String lexeme;
    private String description;
    private String type = "";
    // private Dictionary<String, String> atributos = new Hashtable<String, Object>();
    
    public Token(int id, String lexeme, String description) {
        this.id = id;
        this.lexeme = lexeme;
        this.description = description; 
    }

	/*
	 * public void addAtributo(String caract, Object val) { atributos.put(caract,
	 * val); }
	 */

//    public String getAttributo(String caract) {
//        return (String) atributos.get(caract);
//    }

	/* con posibilidad de volarlo
	 * public void setLexeme(String lexeme) { this.lexeme = lexeme; }
	 */

    public String getLexema() {
		return lexeme;
	}
	
	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}
	
	@Override
    public String toString() {
        return "Token: " + id + " | Lexeme: " + lexeme + " | " + "Description: " + description ;
    }
}