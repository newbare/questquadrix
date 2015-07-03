package com.br.questquadirx.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.br.questquadirx.dominio.Usuario;
import com.br.questquadirx.util.infra.DaoUtil;

/**
 * @author Jefera
 */
@Named
public class UsuarioRepositorio extends DaoUtil<Usuario> implements Serializable {

	private static final long serialVersionUID = 1L;

	 @SuppressWarnings("unchecked")
	public Usuario buscarUsuarioPorNome(String usuario) throws UsernameNotFoundException {
		  Query q=getEntityManager().createQuery("select u from Usuario u where name=?");
		  q.setParameter(1,usuario);
		  List<Usuario> users=q.getResultList();
		  if (users == null || users.isEmpty()) {
		    throw new UsernameNotFoundException("Usuario: '" + usuario + "' Não cadastrado...");
		  }
		 else {
		    return users.get(0);
		  }
		}

}