package br.com.rnery.dao.generic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GenericDAO<T> implements IGenericDAO<T> {
	
	protected final void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) {
				rs.close();
			} 
			if(stm != null && !stm.isClosed()) {
				stm.close();
			}
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
