package com.epul.oeuvres.dao;

import java.util.*;

import javax.persistence.EntityTransaction;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.*;

/**
 * Classe DAO pour les adhérents
 * 
 * @author GERLAND - LETOURNEUR
 */
public class AdherentService extends EntityService {

	/**
	 * Ajout d'un adhérent en base de données
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void insertAdherent(Adherent adherent) throws MonException {
		try {

			EntityTransaction transac = startTransaction();
			if (!entitymanager.contains(adherent)) {
				transac.begin();
				entitymanager.persist(adherent);
				entitymanager.flush();
				transac.commit();
			}
			entitymanager.close();
		} catch (Exception e) {
			new MonException("Erreur d'insertion", e.getMessage());
		}
	}

	/**
	 * Modification d'un adhérent en base de données
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void updateAdherent(Adherent adherent) throws MonException {
		try {
			entitymanager.getTransaction().begin();
			entitymanager.persist(adherent);
			entitymanager.getTransaction().commit();
			} catch(Exception e){
				new MonException("Erreur de mise à jour", e.getMessage());
			}
	}

	/**
	 * Consulter un adhérent par Id Fabrique et renvoie un objet adhérent
	 * contenant le résultat de la requète
	 * 
	 * @param numero
	 *            integer
	 */
	public Adherent consulterAdherent(int numero) throws MonException {
		
		Adherent adherent = null;
		try {
			EntityTransaction transac = startTransaction();
			transac.begin();
			adherent = entitymanager.find(Adherent.class, numero);
			entitymanager.close();
			emf.close();
			
		} catch (Exception e) {
			new MonException("Erreur de lecture", e.getMessage());
		}
		return adherent;
	}

	/**
	 * Consulter tous les adhérents Fabrique et renvoie les objets adhérent
	 * contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Adherent> consulterListeAdherents() throws MonException {
		
		return consulterListeAdherents("SELECT a FROM Adherent a ORDER BY a.nomAdherent");
	}

	/**
	 * Consulter les adhérents par paquet Fabrique et renvoie les objets
	 * adhérent contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Adherent> consulterListeAdherents(int page, int nombreParPage) throws MonException {
		
		return consulterListeAdherents("SELECT * FROM Adherent ORDER BY nomAdherent LIMIT "+page+","+nombreParPage);
	}

	/**
	 * Construire les objects Adherent en fonction de la requête passée en
	 * paramêtre
	 * 
	 * @param mysql
	 *            String
	 * @throws MonException
	 */
	private List<Adherent> consulterListeAdherents(String mysql) throws MonException {
		
		System.out.println("TTTTTTTTTTTTTT");
		List<Adherent> adherents = null;
		try {
			EntityTransaction transac = startTransaction();
			transac.begin();
			adherents = (List<Adherent>) entitymanager.createQuery(mysql).getResultList();
			entitymanager.close();
		}  catch (RuntimeException e){
			new MonException("Erreur de lecture ", e.getMessage());
		}
		System.out.println("AAAAAAAAAAAAAAA");
		
		if(adherents == null) {
			System.out.println("NUUUUUUULL");
		} else {
			for(Adherent a : adherents) {
				System.out.println(a.getNomAdherent());
			}
		}
		return adherents;
	}

	/**
	 * Supprimer un adhérent par Id
	 * 
	 * @param numero
	 *            integer
	 * @throws MonException
	 */
	public boolean deleteAdherent(int id) throws MonException {
		
		try {
		Adherent adherent = entitymanager.find(Adherent.class, id);
		entitymanager.getTransaction().begin();
		entitymanager.remove(adherent);
		entitymanager.getTransaction().commit();
		} catch(Exception e){
			new MonException("Erreur de suppression ", e.getMessage());
			return false;
		}
		return true;
	}
}
