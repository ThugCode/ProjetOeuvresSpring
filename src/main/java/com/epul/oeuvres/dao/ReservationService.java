package com.epul.oeuvres.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	public void insertReservation(Reservation reservation)throws MonException {
		try {

			EntityTransaction transac = startTransaction();
			if (!entitymanager.contains(reservation)) {
				transac.begin();
				entitymanager.persist(reservation);
				entitymanager.flush();
				transac.commit();
			}
			entitymanager.close();
		} catch (Exception e) {
			new MonException("Erreur d'insertion", e.getMessage());
		}
	}
	
	/**
	 * Modification d'une réservation en base de données
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void updateReservation(Reservation reservation, int oldOeuvre, int oldAdherent) throws MonException {
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
		return consulterListeReservations("SELECT r FROM Reservation r ORDER BY r.dateReservation LIMIT "+page+","+nombreParPage);
	}
	
	/**
	 * Construire les objects Reservation en fonction de la requête passée en paramêtre
	 * 
	 * @param mysql String
	 * @throws MonException
	 */
	private List<Reservation> consulterListeReservations(String mysql) throws MonException {
		List<Reservation> Reservations= null;
		try {
			
			EntityTransaction transac = startTransaction();
			transac.begin();
			Reservations = (List<Reservation>)  entitymanager.createQuery(mysql).getResultList();
			entitymanager.close();
		}  catch (RuntimeException e){
			new MonException("Erreur de lecture ", e.getMessage());
		}
		return Reservations;
	}
	
	/**
	 * Supprimer une reservation par Id
	 * 
	 * @param numero integer
	 * @throws MonException
	 */
	public boolean deleteReservation(int idOeuvreVente, int idAdherent) throws MonException {
		return false;
	}

	
}
