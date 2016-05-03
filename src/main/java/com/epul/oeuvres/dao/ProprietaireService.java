package com.epul.oeuvres.dao;

import java.util.*;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.*;

/**
 * Classe DAO pour les propriétaires
 * 
 * @author GERLAND - LETOURNEUR
 */
public class ProprietaireService extends EntityService {
	
	/**
	 * Consulter un propriétaire par Id
	 * Fabrique et renvoie un objet Proprietaire contenant le résultat de la requète
	 * 
	 * @param numero integer
	 */
	public Proprietaire consulterProprietaire(int numero) throws MonException {
		
		return (Proprietaire) this.find(Proprietaire.class, numero);
	}

	/**
	 * Consulter tous les propriétaires
	 * Fabrique et renvoie les objets proprietaire contenant le résultat de la requète
	 * 
	 * @throws MonException
	 */
	public List<Proprietaire> consulterListeProprietaires() throws MonException {
		
		return consulterListeProprietaires("SELECT p FROM Proprietaire p ORDER BY p.nomProprietaire");
	}

	/**
	 * Construire les objects Proprietaire en fonction de la requête passée en paramêtre
	 * 
	 * @param mysql String
	 * @throws MonException
	 */
	private List<Proprietaire> consulterListeProprietaires(String mysql) throws MonException {
		
		return this.findAll(mysql);
	}
}
