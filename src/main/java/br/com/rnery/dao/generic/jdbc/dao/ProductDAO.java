package br.com.rnery.dao.generic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.rnery.dao.generic.jdbc.ConnectionFactory;
import br.com.rnery.domain.Product;

public class ProductDAO extends GenericDAO<Product> implements IProductDAO {

	@Override
	public Integer register(Product p) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getInsertSql();
			stm = connection.prepareStatement(sql);
			addInsertParams(stm, p);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public Integer update(Product p) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getUpdateSql();
			stm = connection.prepareStatement(sql);
			addUpdateParams(stm, p);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public Product search(String code) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Product p = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSelectSql();
			stm = connection.prepareStatement(sql);
			addSelectParams(stm, code);
			rs = stm.executeQuery();
			
			if(rs.next()) {
				p = new Product();
				p.setName(rs.getString("NAME"));
				p.setCode(rs.getString("CODE"));
				p.setPrice(rs.getDouble("PRICE"));
				p.setId(rs.getLong("ID"));
			}
		} catch (Exception e) {
			
		} finally {
			closeConnection(connection, stm, rs);
		}
		return p;
	}

	@Override
	public List<Product> getAll() throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<>();
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSelectAllSql();
			stm = connection.prepareStatement(sql);
			rs = stm.executeQuery();
			
			while(rs.next()) {
				Product p = new Product();
				p.setCode(rs.getString("CODE"));
				p.setName(rs.getString("NAME"));
				p.setPrice(rs.getDouble("PRICE"));
				p.setId(rs.getLong("ID"));
				list.add(p);
			}
			
		} catch(Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return list;
	}

	@Override
	public Integer delete(Product p) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getDeleteSql();
			stm = connection.prepareStatement(sql);
			addDeleteParams(stm, p);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}
	
	private String getInsertSql() {
		StringBuilder st = new StringBuilder();
		st.append("INSERT INTO TB_PRODUCT(NAME, CODE, PRICE) ");
		st.append("VALUES ( ?, ?, ?);");
		return st.toString();
	}
	
	private String getDeleteSql() {
		StringBuilder st = new StringBuilder();
		st.append("DELETE FROM TB_PRODUCT ");
		st.append("WHERE CODE = ?;");
		return st.toString();
	}
	
	private String getSelectSql() {
		StringBuilder st = new StringBuilder();
		st.append("SELECT * FROM TB_PRODUCT ");
		st.append("WHERE CODE = ?;");
		return st.toString();
	}
	
	private String getSelectAllSql() {
		StringBuilder st = new StringBuilder();
		st.append("SELECT * FROM TB_PRODUCT;");
		return st.toString();
	}
	
	private String getUpdateSql() {
		StringBuilder st = new StringBuilder();
		st.append("UPDATE TB_PRODUCT ");
		st.append("SET NAME = ?, CODE = ?, PRICE = ? ");
		st.append("WHERE ID = ?;");
		return st.toString();
	}
	
	private void addInsertParams(PreparedStatement stm, Product p) throws SQLException {
		stm.setString(1, p.getName());
		stm.setString(2, p.getCode());
		stm.setDouble(3, p.getPrice());
	}
	
	private void addDeleteParams(PreparedStatement stm, Product p) throws SQLException {
		stm.setString(1, p.getCode());
	}
	
	private void addSelectParams(PreparedStatement stm, String code) throws SQLException {
		stm.setString(1, code);
	}
	
	private void addUpdateParams(PreparedStatement stm, Product p) throws SQLException {
		stm.setString(1, p.getName());
		stm.setString(2, p.getCode());
		stm.setDouble(3, p.getPrice());
		stm.setLong(4, p.getId());
	}

}
