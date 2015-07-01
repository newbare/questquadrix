package com.br.questquadirx.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * The persistent class for the QUESTAO database table.
 * 
 */
@Entity
@NamedQuery(name="Questao.findAll", query="SELECT q FROM Questao q")
@JsonAutoDetect
public class Questao implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idQuestao;
	private byte[] imgComplementar;
	private String txtComplementar;
	private String txtEnunciado;
	private Disciplina disciplina;
	private Prova prova;
	private List<Resposta> respostas;

	public Questao() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_QUESTAO")
	public int getIdQuestao() {
		return this.idQuestao;
	}

	public void setIdQuestao(int idQuestao) {
		this.idQuestao = idQuestao;
	}


	@Lob
	@Column(name="IMG_COMPLEMENTAR")
	public byte[] getImgComplementar() {
		return this.imgComplementar;
	}

	public void setImgComplementar(byte[] imgComplementar) {
		this.imgComplementar = imgComplementar;
	}


	@Column(name="TXT_COMPLEMENTAR")
	public String getTxtComplementar() {
		return this.txtComplementar;
	}

	public void setTxtComplementar(String txtComplementar) {
		this.txtComplementar = txtComplementar;
	}


	@Column(name="TXT_ENUNCIADO")
	public String getTxtEnunciado() {
		return this.txtEnunciado;
	}

	public void setTxtEnunciado(String txtEnunciado) {
		this.txtEnunciado = txtEnunciado;
	}

	@JsonIgnore
	//bi-directional many-to-one association to Disciplina
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISCIPLINA")
	public Disciplina getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}


	//bi-directional many-to-one association to Prova
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROVA")
	public Prova getProva() {
		return this.prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}


	@JsonIgnore
	//bi-directional many-to-one association to Resposta
	@OneToMany(mappedBy="questao")
	public List<Resposta> getRespostas() {
		return this.respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public Resposta addResposta(Resposta resposta) {
		getRespostas().add(resposta);
		resposta.setQuestao(this);

		return resposta;
	}

	public Resposta removeResposta(Resposta resposta) {
		getRespostas().remove(resposta);
		resposta.setQuestao(null);

		return resposta;
	}

}