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
 * The persistent class for the GRAU database table.
 * 
 */
@Entity
@NamedQuery(name="Grau.findAll", query="SELECT g FROM Grau g")
@JsonAutoDetect
public class Grau implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idGrau;
	private String txtNivel;
	private List<Prova> provas;

	public Grau() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_GRAU")
	public int getIdGrau() {
		return this.idGrau;
	}

	public void setIdGrau(int idGrau) {
		this.idGrau = idGrau;
	}


	@Column(name="TXT_NIVEL")
	public String getTxtNivel() {
		return this.txtNivel;
	}

	public void setTxtNivel(String txtNivel) {
		this.txtNivel = txtNivel;
	}


	@JsonIgnore
	//bi-directional many-to-one association to Prova
	@OneToMany(mappedBy="grau")
	public List<Prova> getProvas() {
		return this.provas;
	}

	public void setProvas(List<Prova> provas) {
		this.provas = provas;
	}

	public Prova addProva(Prova prova) {
		getProvas().add(prova);
		prova.setGrau(this);

		return prova;
	}

	public Prova removeProva(Prova prova) {
		getProvas().remove(prova);
		prova.setGrau(null);

		return prova;
	}

}