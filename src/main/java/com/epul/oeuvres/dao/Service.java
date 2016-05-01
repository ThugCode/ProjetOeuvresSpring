package com.epul.oeuvres.dao;

import java.util.*;

import javax.persistence.EntityTransaction;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.*;

public class Service extends EntityService {

	// ajout 'un adh�rent
	

	public void insertAdherent(Adherent unAdherent) throws MonException {

		try {
						
			EntityTransaction transac = startTransaction();
			if (!entitymanager.contains(unAdherent)) {
			transac.begin();
			 entitymanager.persist(unAdherent);
			 entitymanager.flush();
			 transac.commit();
			}
			entitymanager.close();
		} catch (Exception e) {
			new MonException("Erreur d'insertion", e.getMessage());
		}
	}

	// gestion des adherents
	// Consultation d'un adh�rent par son num�ro
	// Fabrique et renvoie un objet adh�rent contenant le r�sultat de la requ�te
	// BDD
	public Adherent consulterAdherent(int numero) throws MonException {
		Adherent unAd = null;
		try {
			
			EntityTransaction transac = startTransaction();
			transac.begin();
			unAd = entitymanager.find(Adherent.class, numero);
			entitymanager.close();
			emf.close();
			
		} catch (Exception e) {
			new MonException("Erreur de lecture", e.getMessage());
		}
		return unAd;
	}

	// Consultation des adh�rents
	// Fabrique et renvoie une liste d'objets adh�rent contenant le r�sultat de
	// la requ�te BDD
	public List<Adherent> consulterListeAdherents() throws MonException {
		List<Adherent> mesAdherents= null;
		try {
			
			EntityTransaction transac = startTransaction();
			transac.begin();
			mesAdherents = (List<Adherent>)  entitymanager.createQuery("SELECT a FROM Adherent a ORDER BY a.nomAdherent").getResultList();
			entitymanager.close();
		}  catch (RuntimeException e){
			new MonException("Erreur de lecture ", e.getMessage());
		}
		return mesAdherents;
	}
}
