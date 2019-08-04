package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//Gerar conexão com o banco de dados
public class DB {
	
	//connectar com o banco
	private static Connection conn = null;
	
	//metodo para conectar com o banco
	public static Connection getConnection() {
		
		if(conn == null) {
			try {
				//pega propriedades do banco de dados com o loadProperties
				Properties props = loadProperties();
				
				//pega a url do BD no db.priperties
				String url = props.getProperty("dburl");
				
				//obter conexão com o BD, passa url e propriedade
				conn = DriverManager.getConnection(url, props);
				
			//se acontecer alguma exceção SLQ lança excessão personalisada	
			}catch(SQLException e) {
				//DbException é derivada da RuntimeException. Uma para ter excessão personalizada e outro motivo para o programa não ficar pedindo try catch toda hora
				throw new DbException(e.getMessage());
			}
		}
		
		return conn;		
	}
	
	//fechar a conexão
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}catch (SQLException e){
				throw new DbException(e.getMessage());
			}
		}
	}
	
	//private pq vou usar só nessa classe, retorna objetivo tipo Properties
	private static Properties loadProperties() {
		//abrir o arquivo que está na raiz do projeto
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			//faz a leitura do arquivo propertires apontado pelo 
			props.load(fs);
			
			return props;
		}catch(IOException e){
			//se deu erro lança a excessão personalisada DbException 
			throw new DbException(e.getMessage());
		}
	}
	
	//método para fechar o Statement st lá do Program
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				//Se acontecer alguma exceção eu lanço a exceção personalizada. Assim não precisa ficar tratatando com try catch lá toda hora
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultset(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
