package com.br.questquadirx.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the PROVA database table.
 * 
 */
@Entity@XmlRootElement(name="prova")
@NamedQuery(name="Prova.findAll", query="SELECT p FROM Prova p")
public class Prova implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idProva;
	private String nome;
	private Banca banca;
	private Grau grau;
	private Instituicao instituicao;
	private List<Questao> questaos;

	public Prova() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_PROVA")
	public int getIdProva() {
		return this.idProva;
	}

	public void setIdProva(int idProva) {
		this.idProva = idProva;
	}


	//bi-directional many-to-one association to Banca
	
	@ManyToOne
	@JoinColumn(name="ID_BANCA",referencedColumnName = "ID_BANCA",insertable = true, updatable = true)
	public Banca getBanca() {
		return this.banca;
	}

	public void setBanca(Banca banca) {
		this.banca = banca;
	}

	
	@ManyToOne
	@JoinColumn(name="ID_GRAU",referencedColumnName = "ID_GRAU",insertable = true, updatable = true)
	public Grau getGrau() {
		return this.grau;
	}

	public void setGrau(Grau grau) {
		this.grau = grau;
	}

	
	@ManyToOne
	@JoinColumn(name="ID_INSTITUICAO",referencedColumnName = "ID_INSTITUICAO",insertable = true, updatable = true)
	public Instituicao getInstituicao() {
		return this.instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	
	@OneToMany(mappedBy="prova")
	public List<Questao> getQuestaos() {
		return this.questaos;
	}

	public void setQuestaos(List<Questao> questaos) {
		this.questaos = questaos;
	}

	public Questao addQuestao(Questao questao) {
		getQuestaos().add(questao);
		questao.setProva(this);

		return questao;
	}

	public Questao removeQuestao(Questao questao) {
		getQuestaos().remove(questao);
		questao.setProva(null);

		return questao;
	}

	@Column(name="NOME")
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((banca == null) ? 0 : banca.hashCode());
		result = prime * result + ((grau == null) ? 0 : grau.hashCode());
		result = prime * result + idProva;
		result = prime * result
				+ ((instituicao == null) ? 0 : instituicao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((questaos == null) ? 0 : questaos.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prova other = (Prova) obj;
		if (banca == null) {
			if (other.banca != null)
				return false;
		} else if (!banca.equals(other.banca))
			return false;
		if (grau == null) {
			if (other.grau != null)
				return false;
		} else if (!grau.equals(other.grau))
			return false;
		if (idProva != other.idProva)
			return false;
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (questaos == null) {
			if (other.questaos != null)
				return false;
		} else if (!questaos.equals(other.questaos))
			return false;
		return true;
	}
	
	

}