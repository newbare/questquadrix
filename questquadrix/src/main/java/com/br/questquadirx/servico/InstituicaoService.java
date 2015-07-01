package com.br.questquadirx.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.br.questquadirx.dominio.Instituicao;
import com.br.questquadirx.repositorio.InstituicaoRepositorio;


/**
 * @author Jefera
 */
@Named
public class InstituicaoService implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InstituicaoRepositorio repository;
	
	
	@Inject
	public void setRepository(InstituicaoRepositorio repository) {
		this.repository = repository;
	}

	@Transactional
	public List<Instituicao> list() {
		return repository.listar();		
	}
	
}