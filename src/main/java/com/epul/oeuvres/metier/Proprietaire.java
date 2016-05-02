package com.epul.oeuvres.metier;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the proprietaire database table.
 * 
 */
@Entity
@NamedQuery(name="Proprietaire.findAll", query="SELECT p FROM Proprietaire p")
public class Proprietaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_proprietaire")
	private int idProprietaire;

	@Column(name="nom_proprietaire")
	private String nomProprietaire;

	@Column(name="prenom_proprietaire")
	private String prenomProprietaire;

	

	public Proprietaire() {
	}

	public int getIdProprietaire() {
		return this.idProprietaire;
	}

	public void setIdProprietaire(int idProprietaire) {
		this.idProprietaire = idProprietaire;
	}

	public String getNomProprietaire() {
		return this.nomProprietaire;
	}

	public void setNomProprietaire(String nomProprietaire) {
		this.nomProprietaire = nomProprietaire;
	}

	public String getPrenomProprietaire() {
		return this.prenomProprietaire;
	}

	public void setPrenomProprietaire(String prenomProprietaire) {
		this.prenomProprietaire = prenomProprietaire;
	}

	

	public Oeuvrepret addOeuvrepret(Oeuvrepret oeuvrepret) {
		
		oeuvrepret.setProprietaire(this);

		return oeuvrepret;
	}

	public Oeuvrepret removeOeuvrepret(Oeuvrepret oeuvrepret) {
		
		oeuvrepret.setProprietaire(null);

		return oeuvrepret;
	}

	

	public Oeuvrevente addOeuvrevente(Oeuvrevente oeuvrevente) {
		
		oeuvrevente.setProprietaire(this);

		return oeuvrevente;
	}

	public Oeuvrevente removeOeuvrevente(Oeuvrevente oeuvrevente) {
		
		oeuvrevente.setProprietaire(null);

		return oeuvrevente;
	}

}