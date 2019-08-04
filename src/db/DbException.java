package db;

public class DbException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//essa msg q vou usar para criar excessão vou transmitir na super classe Runtime...
	public DbException (String msg) {
		super(msg);
	}

}
