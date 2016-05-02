package com.epul.oeuvres.dao;

import java.util.*;

import javax.persistence.EntityTransaction;

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
		try {

			EntityTransaction transac = startTransaction();
			if (!entitymanager.contains(oeuvreVente)) {
				transac.begin();
				entitymanager.persist(oeuvreVente);
				entitymanager.flush();
				transac.commit();
			}
			entitymanager.close();
		} catch (Exception e) {
			new MonException("Erreur d'insertion", e.getMessage());
		}
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
		Oeuvrevente oeuvreVente = null;
		try {
			EntityTransaction transac = startTransaction();
			transac.begin();
			oeuvreVente = entitymanager.find(Oeuvrevente.class, numero);
			entitymanager.close();
		} catch (Exception e) {
			new MonException("Erreur de lecture ", e.getMessage());
		}
		return oeuvreVente;
	}
	
	/**
	 * Consulter toutes les oeuvres
	 * Fabrique et renvoie les objets oeuvrevente contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Oeuvrevente> consulterListeOeuvresVentes() throws MonException {
		return consulterListeOeuvresVentes("SELECT o FROM Oeuvrevente o ORDER BY o.titreOeuvrevente");
	}
	
	/**
	 * Consulter les oeuvres par paquet
	 * Fabrique et renvoie les objets oeuvrevente contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Oeuvrevente> consulterListeOeuvresVentes(int page, int nombreParPage) throws MonException {

		return consulterListeOeuvresVentes("SELECT o FROM Oeuvrevente o ORDER BY o.titreOeuvrevente LIMIT "+page+","+nombreParPage);
	}
	
	/**
	 * Construire les objects OeuvreVente en fonction de la requête passée en paramêtre
	 * 
	 * @param mysql String
	 * @throws MonException
	 */
	private List<Oeuvrevente> consulterListeOeuvresVentes(String mysql) throws MonException {
		List<Oeuvrevente> mesOeuvresVentes= null;
		try {
			
			EntityTransaction transac = startTransaction();
			transac.begin();
			mesOeuvresVentes = (List<Oeuvrevente>)  entitymanager.createQuery(mysql).getResultList();
			entitymanager.close();
		}  catch (RuntimeException e){
			new MonException("Erreur de lecture ", e.getMessage());
		}
		return mesOeuvresVentes;
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
