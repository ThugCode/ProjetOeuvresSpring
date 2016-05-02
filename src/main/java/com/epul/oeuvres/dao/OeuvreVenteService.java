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
	public void insertOeuvreVente(Oeuvrevente oeuvreVente) throws MonException {
		this.inserer(oeuvreVente);
	}
	
	/**
	 * Modification d'une oeuvre en base de données
	 * 
	 * @param adherent
	 * @throws MonException
	 */
	public void updateOeuvreVente(Oeuvrevente oeuvreVente) throws MonException {
		this.update(oeuvreVente);
	}
	
	/**
	 * Consulter une oeuvre par Id
	 * Fabrique et renvoie un objet oeuvrevente contenant le résultat de la requète
	 * 
	 * @param numero integer
	 */
	public Oeuvrevente consulterOeuvrevente(int numero) throws MonException {
		
		return (Oeuvrevente) find(Oeuvrevente.class, numero);
	}
	
	/**
	 * Consulter toutes les oeuvres
	 * Fabrique et renvoie les objets oeuvrevente contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Oeuvrevente> consulterListeOeuvresVentes() throws MonException {
		return consulterListeOeuvresVentes("SELECT o FROM Oeuvrevente o ORDER BY o.idOeuvrevente");
	}
	
	/**
	 * Consulter les oeuvres par paquet
	 * Fabrique et renvoie les objets oeuvrevente contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Oeuvrevente> consulterListeOeuvresVentes(int page, int nombreParPage) throws MonException {

		return findAllWithLimit("SELECT o FROM Oeuvrevente o ORDER BY o.idOeuvrevente", page, nombreParPage);
	}
	
	/**
	 * Construire les objects OeuvreVente en fonction de la requête passée en paramêtre
	 * 
	 * @param mysql String
	 * @throws MonException
	 */
	private List<Oeuvrevente> consulterListeOeuvresVentes(String mysql) throws MonException {
		
		return findAll(mysql);
	}
	
	/**
	 * Supprimer une oeuvre par Id
	 * 
	 * @param numero integer
	 * @throws MonException
	 */
	public boolean deleteOeuvreVente(int id) throws MonException {
		
		return delete(Oeuvrevente.class, id);
	}
}
