package db;

public class DBIntegrityException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	//Excecao personalizada de integridade referencial
	public DBIntegrityException(String msg) {
		super(msg);
	}
	

}
