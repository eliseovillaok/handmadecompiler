package compilador;

public class Par<I, A> {
 private I token;
 private A objeto;
 
	public Par(I token, A objeto) {
		super();
		this.token = token;
		this.objeto = objeto;
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



