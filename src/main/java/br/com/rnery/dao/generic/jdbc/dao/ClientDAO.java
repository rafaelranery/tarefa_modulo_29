package br.com.rnery.dao.generic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.rnery.dao.generic.jdbc.ConnectionFactory;
import br.com.rnery.domain.Client;

public class ClientDAO extends GenericDAO<Client> implements IClientDAO{

	@Override
	public Integer register(Client c) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null; // comando a ser executado na db
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert(); // criando o comando para insert
			stm = connection.prepareStatement(sql); // verificação do comando sql;
			addInsertParams(stm, c); // adicionando valores para inserção.
			return stm.executeUpdate(); // retorn um int representando o numero de rows alteradas
		} catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null); // removendo conexão e dados da memória.
		}
	}
	
	@Override
	public Integer update(Client c) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlUpdate();
			stm = connection.prepareStatement(sql);
			addUpdateParams(stm, c);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public Client search(String code) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Client c = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelectOne();
			stm = connection.prepareStatement(sql);
			addSelectOneParams(stm, code);
			rs = stm.executeQuery();
			
			if(rs.next()) {
				c = new Client();
				c.setId(rs.getLong("ID"));
				c.setName(rs.getString("NAME"));
				c.setCode(rs.getString("CODE"));
			}
		} catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return c;
	}

	@Override
	public List<Client> getAll() throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Client> list = new ArrayList<>();
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelectAll();
			stm = connection.prepareStatement(sql);
			rs = stm.executeQuery();
			
			while(rs.next()) {
				Client c = new Client();
				c.setId(rs.getLong("ID"));
				c.setName(rs.getString("NAME"));
				c.setCode(rs.getString("CODE"));
				list.add(c);
			}
			
		} catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		
		return list;
	}


	@Override
	public Integer delete(Client c) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlDelete();
			stm = connection.prepareStatement(sql);
			addDeleteParams(stm, c);
			return stm.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_CLIENT (ID, NAME, CODE) ");
		sb.append("VALUES(nextval('SQ_CLIENT'), ?, ?)");
		return sb.toString();
	}
	
	private String getSqlUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_CLIENT ");
		sb.append("SET NAME = ?, CODE = ? ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}

	private String getSqlSelectOne() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_CLIENT ");
		sb.append("WHERE CODE = ?");
		return sb.toString();
	}
	
	private String getSqlSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_CLIENT ");
		return sb.toString();
	}
	
	private String getSqlDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TB_CLIENT ");
		sb.append("WHERE CODE = ?");
		return sb.toString();
	}
	
	private void addInsertParams(PreparedStatement stm, Client c) throws SQLException {
		stm.setString(1, c.getName());
		stm.setString(2, c.getCode());
	}
	
	private void addUpdateParams(PreparedStatement stm, Client c) throws SQLException {
		stm.setString(1, c.getName());
		stm.setString(2, c.getCode());
		stm.setLong(3, c.getId());
		
	}
	
	private void addSelectOneParams(PreparedStatement stm, String code) throws SQLException {
		stm.setString(1, code);
	}
	
	private void addDeleteParams(PreparedStatement stm, Client c) throws SQLException {
		stm.setString(1, c.getCode());
	}

}
