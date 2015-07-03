package com.br.questquadirx.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.br.questquadirx.dominio.Prova;
import com.br.questquadirx.repositorio.ProvaRepositorio;


/**
 * @author Jefera
 */
@Named
public class ProvaService extends SpringBeanAutowiringSupport implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProvaRepositorio repository;
	/*@Inject
	PasswordEncoder passwordEncoder;*/
	
	@Inject
	public void setRepository(ProvaRepositorio repository) {
		//System.out.println("ADMIN XUXA:"+passwordEncoder.encode("admin"));
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
	public void create(Prova prova) {
		prepararInsert(prova);
		repository.inserir(prova);
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
	
	private void prepararInsert(Prova prova) {		
		if(prova.getInstituicao()==null || prova.getInstituicao().getIdInstituicao()==0){prova.getInstituicao().setIdInstituicao(1);}
		if(prova.getGrau()==null || prova.getGrau().getIdGrau()==0){prova.getGrau().setIdGrau(1);}
		if(prova.getBanca()==null || prova.getBanca().getIdBanca()==0){prova.getBanca().setIdBanca(1);}
	}

}