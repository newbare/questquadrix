package com.br.questquadirx.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.br.questquadirx.dominio.Prova;
import com.br.questquadirx.repositorio.ProvaRepositorio;


/**
 * @author Jefera
 */
@Named
public class ProvaService implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProvaRepositorio repository;
	
	
	@Inject
	public void setRepository(ProvaRepositorio repository) {
		this.repository = repository;
	}

	@Transactional
	public List<Prova> list() {
		return repository.listar();		
	}

	@Transactional
	public Prova read(int id) {
		return repository.buscarPor(id);
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(Prova Prova) {
		repository.inserir(Prova);
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public Prova update(Prova prova) {
		return repository.alterar(0,prova);
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public Prova update(int id, Prova prova) {
		prova = repository.buscarPor(id);
		return repository.alterar(0,prova);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Prova Prova) {
		repository.excluir(Prova);
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(int id) {
		repository.excluirPor(id);
	}

}