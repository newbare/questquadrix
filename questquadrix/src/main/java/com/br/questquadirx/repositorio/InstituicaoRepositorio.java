package com.br.questquadirx.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.br.questquadirx.dominio.Banca;
import com.br.questquadirx.dominio.Instituicao;
import com.br.questquadirx.util.infra.DaoUtil;

/**
 * @author Jefera
 */
@Named
public class InstituicaoRepositorio extends DaoUtil<Instituicao> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Instituicao> listar() {
		StringBuilder jpql = new StringBuilder() //
		.append("SELECT x ") //
		.append("FROM " + Instituicao.class.getName() + " x ") ;
		return (List<Instituicao>) this.consultaHQLComParametros(jpql.toString(), null);
	}

}