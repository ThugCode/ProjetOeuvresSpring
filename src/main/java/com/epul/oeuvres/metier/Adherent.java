package com.epul.oeuvres.metier;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the adherent database table.
 * 
 */
@Entity
@NamedQuery(name="Adherent.findAll", query="SELECT a FROM Adherent a")
public class Adherent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_adherent", insertable = false, updatable = false)
	private int idAdherent;

	@Column(name="nom_adherent")
	private String nomAdherent;

	@Column(name="prenom_adherent")
	private String prenomAdherent;

	@Column(name="ville_adherent")
	private String villeAdherent;

	public Adherent() {}

	public int getIdAdherent() {
		return this.idAdherent;
	}

	public void setIdAdherent(int idAdherent) {
		this.idAdherent = idAdherent;
	}

	public String getNomAdherent() {
		return this.nomAdherent;
	}

	public void setNomAdherent(String nomAdherent) {
		this.nomAdherent = nomAdherent;
	}

	public String getPrenomAdherent() {
		return this.prenomAdherent;
	}

	public void setPrenomAdherent(String prenomAdherent) {
		this.prenomAdherent = prenomAdherent;
	}

	public String getVilleAdherent() {
		return this.villeAdherent;
	}

	public void setVilleAdherent(String villeAdherent) {
		this.villeAdherent = villeAdherent;
	}

	public Reservation addReservation(Reservation reservation) {
		reservation.setAdherent(this);
		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		reservation.setAdherent(null);
		return reservation;
	}

}