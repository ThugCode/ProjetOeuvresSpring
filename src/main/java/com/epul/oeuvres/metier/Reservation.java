package com.epul.oeuvres.metier;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the reservation database table.
 * 
 */
@Entity
@NamedQuery(name="Reservation.findAll", query="SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReservationPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="date_reservation")
	private Date dateReservation;

	private String statut;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_oeuvrevente", insertable=false, updatable=false)
	private Oeuvrevente oeuvrevente;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_adherent", insertable=false, updatable=false)
	private Adherent adherent;

	public Reservation() {}

	public ReservationPK getId() {
		return this.id;
	}

	public void setId(ReservationPK id) {
		this.id = id;
	}

	public Date getDateReservation() {
		return this.dateReservation;
	}

	public void setDateReservation(Date dateReservation) {
		this.dateReservation = dateReservation;
	}

	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Oeuvrevente getOeuvrevente() {
		return this.oeuvrevente;
	}

	public void setOeuvrevente(Oeuvrevente oeuvrevente) {
		this.oeuvrevente = oeuvrevente;
	}

	public Adherent getAdherent() {
		return this.adherent;
	}

	public void setAdherent(Adherent adherent) {
		this.adherent = adherent;
	}

}