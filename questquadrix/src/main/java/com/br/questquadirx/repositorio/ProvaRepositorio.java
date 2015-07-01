package com.br.questquadirx.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.br.questquadirx.dominio.Prova;
import com.br.questquadirx.util.infra.DaoUtil;

/**
 * @author Jefera
 */
@Named
public class ProvaRepositorio extends DaoUtil<Prova> implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public List<Prova> listar() {
		StringBuilder jpql = new StringBuilder() //
				.append("SELECT x ") //
				.append("FROM " + Prova.class.getName() + " x ") 
				.append(" INNER JOIN x.grau g ")
				.append(" INNER JOIN x.banca b ")
				.append(" INNER JOIN x.instituicao i ")
				.append(" ORDER BY x.idProva ASC ");
		return (List<Prova>) this.consultaHQLComParametros(jpql.toString(), null);
	}
	
	public Prova buscarPor(int id) {
		return this.buscarPorId(id);
	}	
	
	public void inserir(Prova Prova) {
		this.incluir(Prova);
	}
	
	public Prova alterar(int index, Prova prova) {
		if(index>0)
		prova = this.buscarPorId(index);
		return this.update(prova);
	}	
	
	public void excluir(Prova prova) {		
		this.remover(prova.getIdProva());
	}	
	
	public void excluirPor(int id) {
		this.remover(id);
	}

}