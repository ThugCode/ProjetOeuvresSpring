package com.epul.oeuvres.dao;

import java.util.*;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.*;

/**
 * Classe DAO pour les oeuvres vente
 * 
 * @author GERLAND - LETOURNEUR
 */
public class OeuvreVenteService extends EntityService {

	/**
	 * Ajout d'une oeuvre en base de données
	 * 
	 * @param oeuvrePret
	 * @throws MonException
	 */
	public void insertOeuvreVente(Oeuvrevente oeuvreVente)throws MonException {
	}
	
	/**
	 * Modification d'une oeuvre en base de données
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void updateOeuvreVente(Oeuvrevente oeuvreVente) throws MonException {
	}
	
	/**
	 * Consulter une oeuvre par Id
	 * Fabrique et renvoie un objet oeuvrevente contenant le résultat de la requète
	 * 
	 * @param numero integer
	 */
	public Oeuvrevente consulterOeuvrevente(int numero) throws MonException {
		return new Oeuvrevente();
	}
	
	/**
	 * Consulter toutes les oeuvres
	 * Fabrique et renvoie les objets oeuvrevente contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Oeuvrevente> consulterListeOeuvresVentes() throws MonException {
		return new ArrayList<Oeuvrevente>();
	}
	
	/**
	 * Consulter les oeuvres par paquet
	 * Fabrique et renvoie les objets oeuvrevente contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Oeuvrevente> consulterListeOeuvresVentes(int page, int nombreParPage) throws MonException {
		return new ArrayList<Oeuvrevente>();
	}
	
	/**
	 * Construire les objects OeuvreVente en fonction de la requête passée en paramêtre
	 * 
	 * @param mysql String
	 * @throws MonException
	 */
	private List<Oeuvrevente> consulterListeOeuvresVentes(String mysql) throws MonException {
		return new ArrayList<Oeuvrevente>();
	}
	
	/**
	 * Supprimer une oeuvre par Id
	 * 
	 * @param numero integer
	 * @throws MonException
	 */
	public boolean deleteOeuvreVente(int id) throws MonException {
		return false;
	}
}
