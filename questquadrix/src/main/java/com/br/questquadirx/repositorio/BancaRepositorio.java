package com.br.questquadirx.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.br.questquadirx.dominio.Banca;
import com.br.questquadirx.dominio.Prova;
import com.br.questquadirx.util.infra.DaoUtil;

/**
 * @author Jefera
 */
@Named
public class BancaRepositorio extends DaoUtil<Banca> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Banca> listar() {
		StringBuilder jpql = new StringBuilder() //
		.append("SELECT x ") //
		.append("FROM " + Banca.class.getName() + " x ") ;
		return (List<Banca>) this.consultaHQLComParametros(jpql.toString(), null);
	}

}