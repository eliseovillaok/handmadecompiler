package compilador;

public class Par {
 private int id;
 private Token token;
 
	public Par(int id, Token token) {
		super();
		this.id = id;
		this.token = token;
	 }

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
}



