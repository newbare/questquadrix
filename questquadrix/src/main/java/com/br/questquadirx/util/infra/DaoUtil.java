package com.br.questquadirx.util.infra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.FinderException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Jefferson Leite
 * @param <T>
 */
@Named
public class DaoUtil<T> implements Dao<T> {

	private static final long serialVersionUID = 6527111403428623938L;
	private Class<T> persistentClass;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Este método deve injetar uma classe de persistência
	 * 
	 * @param persistentClass
	 */
	public final void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public T buscarPorId(Serializable id) {
		return this.entityManager.find(persistentClass, id);
	}

	@Override
	public List<T> buscarTodos() {
		CriteriaBuilder critBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = critBuilder.createQuery(persistentClass);
		Root<T> root = cq.from(persistentClass);
		cq.select(root);
		TypedQuery<T> q = entityManager.createQuery(cq);
		List<T> result = q.getResultList();
		return result;
	}

	@Override
	public T incluir(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public List<T> incluirMuitos(List<T> entities) {
		for (T type : entities) {
			incluir(type);
		}
		return entities;
	}

	@Override
	public T update(T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public List<T> updateMuitos(List<T> entities) {
		List<T> novaLista = new ArrayList<T>();
		for (T type : novaLista) {
			T alterado = update(type);
			novaLista.add(alterado);
		}
		return novaLista;
	}

	@Override
	public void remover(Long id) {
		entityManager.remove(buscarPorId(id));
	}
	
	@Override
	public void remover(int id) {
		entityManager.remove(buscarPorId(id));
	}
	

	/**
	 * Método que recebe uma consulta com HQL e um array de parametros, é importante manter a ordem dos parametros
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@Override
	public List<?> consultaHQLComParametros(String queryStr, Object[] params) {
		Query query = this.entityManager.createQuery(queryStr);
		setQueryParams(query, params);
		return query.getResultList();
	}

	private void setQueryParams(Query query, Object[] params) {
		if ((params != null) && (params.length > 0))
			for (int i = 0; i < params.length; ++i)
				query.setParameter(i + 1, params[i]);
	}
	
	@Override
	public Long count(){
		String jpql = "SELECT count(o) FROM"+ persistentClass.getSimpleName()+" o ";  
		Query q = entityManager.createQuery(jpql);  
		return (Long) q.getSingleResult(); 
	}
	

}
