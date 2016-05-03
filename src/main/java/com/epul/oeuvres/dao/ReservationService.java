package com.epul.oeuvres.dao;

import java.util.*;

import javax.persistence.EntityTransaction;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.*;

/**
 * Classe DAO pour les réservations
 * 
 * @author GERLAND - LETOURNEUR
 */
public class ReservationService extends EntityService {
	
	/**
	 * Ajout d'une réservation en base de données
	 * 
	 * @param oeuvrePret
	 * @throws MonException
	 */
	public void insertReservation(Reservation reservation) throws MonException {
		
		EntityTransaction transac = startTransaction();
		transac.begin();
		ReservationPK cle = new ReservationPK();
		cle.setIdOeuvrevente(reservation.getOeuvrevente().getIdOeuvrevente());
		cle.setIdAdherent(reservation.getAdherent().getIdAdherent());
		reservation.setId(cle);
		entitymanager.persist(reservation);
		transac.commit();
		entitymanager.close();
	}
	
	/**
	 * Modification d'une réservation en base de données
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void updateReservation(Reservation reservation) throws MonException {
		
		this.update(reservation);
	}
	
	/**
	 * Consulter une réservation par Id
	 * Fabrique et renvoie un objet reservation contenant le résultat de la requète
	 * 
	 * @param numero integer
	 */
	public Reservation consulterReservation(int idOeuvreVente, int idAdherent) throws MonException {
		Reservation reservation = null;
		try {
			EntityTransaction transac = startTransaction();
			transac.begin();
			ReservationPK cle = new ReservationPK();
			cle.setIdOeuvrevente(idOeuvreVente);
			cle.setIdAdherent(idAdherent);
			reservation = entitymanager.find(Reservation.class, cle);
			entitymanager.close();
		} catch (Exception e) {
			new MonException("Erreur de lecture ", e.getMessage());
		}
		return reservation;
	}
	
	/**
	 * Consulter toutes les reservations
	 * Fabrique et renvoie les objets reservation contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Reservation> consulterListeReservations() throws MonException {
		return consulterListeReservations("SELECT r FROM Reservation r ORDER BY r.dateReservation");
	}
	
	/**
	 * Consulter les reservations par paquet
	 * Fabrique et renvoie les objets reservation contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Reservation> consulterListeReservations(int page, int nombreParPage) throws MonException {
		
		return findAllWithLimit("SELECT r FROM Reservation r ORDER BY r.dateReservation", page, nombreParPage);
	}
	
	/**
	 * Construire les objects Reservation en fonction de la requête passée en paramêtre
	 * 
	 * @param mysql String
	 * @throws MonException
	 */
	private List<Reservation> consulterListeReservations(String mysql) throws MonException {
		
		return findAll(mysql);
	}
	
	/**
	 * Supprimer une reservation par Id
	 * 
	 * @param numero integer
	 * @throws MonException
	 */
	public boolean deleteReservation(int idOeuvreVente, int idAdherent) throws MonException {
		
		EntityTransaction transac = startTransaction();
		transac.begin();
		ReservationPK cle = new ReservationPK();
		cle.setIdOeuvrevente(idOeuvreVente);
		cle.setIdAdherent(idAdherent);
		Reservation reservation = entitymanager.find(Reservation.class, cle);
		entitymanager.remove(reservation);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emf.close();
		
		return true;
	}

	
}
