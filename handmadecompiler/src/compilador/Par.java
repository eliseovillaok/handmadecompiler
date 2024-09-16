package compilador;

public class Par<I, A> {
 private I token;
 private A objeto;
 
	public Par(I token, A objeto) {
		super();
		this.objeto = objeto;
		this.token = token;
	 }

	public I getToken() {
		return token;
	}
	
	public void setToken(I token) {
		this.token = token;
	}
	
	public A getObjeto() {
		return objeto;
	}
	
	public void setObjeto(A objeto) {
		this.objeto = objeto;
	}

 


}



