package com.br.questquadirx.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.br.questquadirx.dominio.Country;
import com.br.questquadirx.util.infra.DaoUtil;


/**
 * @author Jefera
 */
@SuppressWarnings("serial")
@Named
public class CountryRepository extends DaoUtil<Country> implements Serializable {	

	@SuppressWarnings("unchecked")
	public List<Country> listar() {
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT x ") //
				.append("FROM " + Country.class.getName() + " x ") //
				.append("ORDER BY x.id ASC ");				
		return (List<Country>) this.consultaHQLComParametros(jpql.toString(), null);
	}

	public Country buscarPor(Long id) {
		return this.buscarPorId(id);
	}
	
	public void inserir(Country country) {
		this.incluir(country);
	}
	
	public Country alterar(Country country) {
		 this.update(country);
		 return this.buscarPor(country.getId());
	}	
	
	public void excluir(Long id) {
		this.remover(id);
	}
	
	

}