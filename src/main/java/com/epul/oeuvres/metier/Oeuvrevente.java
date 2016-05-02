package com.epul.oeuvres.metier;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the oeuvrevente database table.
 * 
 */
@Entity
@NamedQuery(name="Oeuvrevente.findAll", query="SELECT o FROM Oeuvrevente o")
public class Oeuvrevente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_oeuvrevente")
	private int idOeuvrevente;

	@Column(name="etat_oeuvrevente")
	private String etatOeuvrevente;

	@Column(name="prix_oeuvrevente")
	private float prixOeuvrevente;

	@Column(name="titre_oeuvrevente")
	private String titreOeuvrevente;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_proprietaire")
	private Proprietaire proprietaire;

	public Oeuvrevente() {}

	public int getIdOeuvrevente() {
		return this.idOeuvrevente;
	}

	public void setIdOeuvrevente(int idOeuvrevente) {
		this.idOeuvrevente = idOeuvrevente;
	}

	public String getEtatOeuvrevente() {
		return this.etatOeuvrevente;
	}

	public void setEtatOeuvrevente(String etatOeuvrevente) {
		this.etatOeuvrevente = etatOeuvrevente;
	}

	public float getPrixOeuvrevente() {
		return this.prixOeuvrevente;
	}

	public void setPrixOeuvrevente(float prixOeuvrevente) {
		this.prixOeuvrevente = prixOeuvrevente;
	}

	public String getTitreOeuvrevente() {
		return this.titreOeuvrevente;
	}

	public void setTitreOeuvrevente(String titreOeuvrevente) {
		this.titreOeuvrevente = titreOeuvrevente;
	}

	public Proprietaire getProprietaire() {
		return this.proprietaire;
	}

	public void setProprietaire(Proprietaire proprietaire) {
		this.proprietaire = proprietaire;
	}

	public Reservation addReservation(Reservation reservation) {
		reservation.setOeuvrevente(this);
		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		reservation.setOeuvrevente(null);
		return reservation;
	}

}