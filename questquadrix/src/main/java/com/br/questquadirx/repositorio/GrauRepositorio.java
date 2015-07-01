package com.br.questquadirx.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.br.questquadirx.dominio.Grau;
import com.br.questquadirx.util.infra.DaoUtil;

/**
 * @author Jefera
 */
@Named
public class GrauRepositorio extends DaoUtil<Grau> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Grau> listar() {
		return this.buscarTodos();
	}

}