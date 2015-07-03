package com.br.questquadirx.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.br.questquadirx.dominio.Usuario;
import com.br.questquadirx.repositorio.UsuarioRepositorio;


/**
 * @author Jefera
 */
@Named
public class UsuarioService implements UserDetailsService,  Serializable {

	private static final long serialVersionUID = 1L;
	private UsuarioRepositorio repository;
	
	@Inject
	public void setRepository(UsuarioRepositorio repository) {
		this.repository = repository;
		this.repository.setPersistentClass(Usuario.class);
	}
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String usuario)
			throws UsernameNotFoundException {
		
		return repository.buscarUsuarioPorNome(usuario);
	}			

}