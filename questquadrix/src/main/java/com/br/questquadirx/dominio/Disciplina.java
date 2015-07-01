package com.br.questquadirx.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * The persistent class for the DISCIPLINA database table.
 * 
 */
@Entity
@NamedQuery(name="Disciplina.findAll", query="SELECT d FROM Disciplina d")
@JsonAutoDetect
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idDisciplina;
	private String nomeDisciplina;
	private List<Questao> questaos;

	public Disciplina() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DISCIPLINA")
	public int getIdDisciplina() {
		return this.idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}


	@Column(name="NOME_DISCIPLINA")
	public String getNomeDisciplina() {
		return this.nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	@JsonIgnore
	//bi-directional many-to-one association to Questao
	@OneToMany(mappedBy="disciplina")
	public List<Questao> getQuestaos() {
		return this.questaos;
	}

	public void setQuestaos(List<Questao> questaos) {
		this.questaos = questaos;
	}

	public Questao addQuestao(Questao questao) {
		getQuestaos().add(questao);
		questao.setDisciplina(this);

		return questao;
	}

	public Questao removeQuestao(Questao questao) {
		getQuestaos().remove(questao);
		questao.setDisciplina(null);

		return questao;
	}

}