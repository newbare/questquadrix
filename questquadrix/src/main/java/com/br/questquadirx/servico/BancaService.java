package com.br.questquadirx.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.br.questquadirx.dominio.Banca;
import com.br.questquadirx.repositorio.BancaRepositorio;


/**
 * @author Jefera
 */
@Named
public class BancaService implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BancaRepositorio repository;
	
	
	@Inject
	public void setRepository(BancaRepositorio repository) {
		this.repository = repository;
	}

	@Transactional
	public List<Banca> list() {
		return repository.listar();		
	}
	
}