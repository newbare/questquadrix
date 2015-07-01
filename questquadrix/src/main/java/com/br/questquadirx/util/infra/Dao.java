package com.br.questquadirx.util.infra;

import java.io.Serializable;
import java.util.List;

public abstract interface Dao<T> extends Serializable
{

	public abstract Long count();

	public abstract List<?> consultaHQLComParametros(String queryStr, Object[] params);

	public abstract void remover(Long id);
	public abstract void remover(int id);

	public abstract List<T> updateMuitos(List<T> entities);

	public abstract T update(T entity);

	public abstract List<T> incluirMuitos(List<T> entities);

	public abstract T incluir(T entity);

	public abstract List<T> buscarTodos();

	public abstract T buscarPorId(Serializable id);
	
}

