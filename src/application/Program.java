package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.spi.TransactionalWriter;

import db.DB;
import db.DBIntegrityException;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		
		try {
			
			conn = DB.getConnection();
			
			//Não é para confirmar as alterações automaticamente. Vao depender confirmação explícita do programador
			conn.setAutoCommit(false);
			
			st = conn.createStatement(); 
					
			//saber quant linhas afetadas. SQL - todo vendedor que pertencer ao department 1 vou atualizar seu salário para 2090		
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2091 WHERE departmentId = 1");		
			
			//simular falha no meio da transação.Processamento vai ser interrompido e cair no catch
			int x = 1;
			if(x < 2) {
				throw new SQLException("Fake error");
			}
			                             
										//vendedor depart.2 vai ganhar 3090
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE departmentId = 2");
			
			//transação terminou
			conn.commit();
			
			//mostra quantas linhas atualizou de cada um
			System.out.println("rows1: " + rows1);
			System.out.println("rows2: " + rows2);
			
			
		}catch(SQLException e){
			
			try {
				//voltar no estado inicial do banco. Aconteceu um erro. Informar a aplicação
				conn.rollback();
				
				//lançar excecao personalizada
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
				
			} catch (SQLException e1) {
				// tentei voltar e aconteceu erro. Lança excecao personalizada
				throw new DbException("Error trying to rollback! Caused by: " + e.getMessage());
			}
			
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}

}
