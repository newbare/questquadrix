package com.br.questquadirx.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.br.questquadirx.dominio.Grau;
import com.br.questquadirx.repositorio.GrauRepositorio;


/**
 * @author Jefera
 */
@Named
public class GrauService implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GrauRepositorio repository;
	
	
	@Inject
	public void setRepository(GrauRepositorio repository) {
		this.repository = repository;
	}

	@Transactional
	public List<Grau> list() {
		return repository.listar();		
	}
	
}