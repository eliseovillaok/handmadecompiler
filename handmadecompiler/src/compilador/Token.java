package compilador;

public class Token {
	private int id = -1;
	private String lexeme = "";
	private String description = "";
	private String type = "";

	public Token() {
	}

	public Token(int id, String lexeme, String description, String type) {
		this.id = id;
		this.lexeme = lexeme;
		this.description = description;
		this.type = type;
	}

	public Token(int id, String lexeme, String description) {
		this.id = id;
		this.lexeme = lexeme;
		this.description = description;
	}

	public Token(int id, String lexeme) {
		this.id = id;
		this.lexeme = lexeme;
	}

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

	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	@Override
	public String toString() {
		return "Token: " + id + " | Lexeme: " + lexeme + " | " + "Description: " + description + " | Tipo: " + type;
	}
}