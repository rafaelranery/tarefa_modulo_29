package br.com.rnery.dao.generic.jdbc.dao;

import java.util.List;


public interface IGenericDAO<T> {
	// interface de CRUD para DAO genérica.

	public Integer register(T e) throws Exception;
	public Integer update(T e) throws Exception;
	public T search(String code) throws Exception;
	public List<T> getAll() throws Exception;
	public Integer delete(T e) throws Exception;
}
