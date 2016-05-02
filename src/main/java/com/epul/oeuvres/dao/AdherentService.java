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
		this.inserer(adherent);
	}

	/**
	 * Modification d'un adhérent en base de données
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void updateAdherent(Adherent adherent) throws MonException {
		
		this.update(adherent);
	}

	/**
	 * Consulter un adhérent par Id Fabrique et renvoie un objet adhérent
	 * contenant le résultat de la requète
	 * 
	 * @param numero
	 *            integer
	 */
	public Adherent consulterAdherent(int numero) throws MonException {
		
		return (Adherent) find(Adherent.class, numero);
	}

	/**
	 * Consulter tous les adhérents Fabrique et renvoie les objets adhérent
	 * contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Adherent> consulterListeAdherents() throws MonException {
		
		return consulterListeAdherents("SELECT a FROM Adherent a ORDER BY a.idAdherent");
	}

	/**
	 * Consulter les adhérents par paquet Fabrique et renvoie les objets
	 * adhérent contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Adherent> consulterListeAdherents(int page, int nombreParPage) throws MonException {

		return findAllWithLimit("SELECT a FROM Adherent a ORDER BY a.idAdherent", page, nombreParPage);
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
		
		return findAll(mysql);
	}

	/**
	 * Supprimer un adhérent par Id
	 * 
	 * @param numero integer
	 * @throws MonException
	 */
	public boolean deleteAdherent(int id) throws MonException {
		
		return delete(Adherent.class, id);
	}
}
