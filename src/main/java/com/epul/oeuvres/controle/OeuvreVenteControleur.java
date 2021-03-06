package com.epul.oeuvres.controle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.epul.oeuvres.dao.OeuvreVenteService;
import com.epul.oeuvres.dao.ProprietaireService;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Oeuvrevente;
import com.epul.oeuvres.metier.Proprietaire;


/**
 * Classe Servlet pour les oeuvres vente
 * 
 * @author GERLAND - LETOURNEUR
 */

@Controller
public class OeuvreVenteControleur extends MultiActionController {
	
	private static final String FORM = "form";
	private static final String LISTE = "liste";
	private static final String INSERER = "inserer";
	private static final String AJOUTER = "ajouter";
	private static final String MODIFIER = "modifier";
	private static final String SUPPRIMER = "supprimer";
	private static final String OEUVREVENTE = "OeuvreVente";
	
	private int page;
	private int nombreParPage;

	public OeuvreVenteControleur() {
		super();
	}
	
	/**
	 * Affichage de la liste des oeuvres
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREVENTE+"/")
	public ModelAndView displayListe(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			page = 1;
			nombreParPage = 5;
			if(request.getParameter("currentPage") != null 
			&& request.getParameter("currentPage") != "") {
				page = Integer.parseInt(request.getParameter("currentPage"));
			}
			if(request.getParameter("currentNumberPerPage") != null 
			&& request.getParameter("currentNumberPerPage") != "") {
				nombreParPage = Integer.parseInt(request.getParameter("currentNumberPerPage"));
			}
			
			request.setAttribute("currentPage", page);
			request.setAttribute("currentNumberPerPage", nombreParPage);
			request.setAttribute("vue", LISTE);
			
			OeuvreVenteService service = new OeuvreVenteService();
			List<Oeuvrevente> listeTotal = service.consulterListeOeuvresVentes();
			float nombreOeuvre = Float.parseFloat(listeTotal.size()+"");
			int nombrePage = (int) Math.ceil(nombreOeuvre/nombreParPage);
			request.setAttribute("nbPage", nombrePage);
			request.setAttribute("tabTitle", "Liste des ventes");
			//request.setAttribute("module", LISTE+OEUVREVENTE);
			
			if(Integer.parseInt(request.getAttribute("currentPage").toString()) > nombrePage) {
				page = 1;
				request.setAttribute("currentPage", page);
			}
			
			List<Oeuvrevente> liste = service.consulterListeOeuvresVentes((int)page-1,(int)nombreParPage);
			request.setAttribute("oeuvres", liste);
			
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}
		return new ModelAndView(LISTE+OEUVREVENTE);
	}
	
	/**
	 * Affichage du formulaire d'ajout
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREVENTE+"/"+AJOUTER)
	public ModelAndView displayAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			request.setAttribute("tabTitle", "Nouvelle oeuvre à vendre");
			request.setAttribute("module", FORM+OEUVREVENTE);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Ajouter");
			
			ProprietaireService service = new ProprietaireService();
			List<Proprietaire> liste;
			liste = service.consulterListeProprietaires();
			request.setAttribute("proprietaires", liste);
			
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}

		return new ModelAndView(FORM+OEUVREVENTE);
	}
	
	/**
	 * Affichage du formulaire de modification
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREVENTE+"/"+MODIFIER+"/{idOeuvreVente}")
	public ModelAndView displayUpdateForm(HttpServletRequest request, HttpServletResponse response, @PathVariable("idOeuvreVente")int idOeuvreVente) throws Exception {

		try {
			ProprietaireService service = new ProprietaireService();
			List<Proprietaire> liste = service.consulterListeProprietaires();
			request.setAttribute("proprietaires", liste);
			
			OeuvreVenteService serviceO = new OeuvreVenteService();
			Oeuvrevente oeuvreAModifier = serviceO.consulterOeuvrevente(idOeuvreVente);
			request.setAttribute("oeuvre", oeuvreAModifier);
			request.setAttribute("tabTitle", "Modification oeuvre vente");
			request.setAttribute("module", FORM+OEUVREVENTE);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Modifier");
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}

		return new ModelAndView(FORM+OEUVREVENTE);
	}

	/**
	 * Ajout ou modification d'une oeuvre vente
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREVENTE+"/"+INSERER)
	public ModelAndView insertNewObject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			
			OeuvreVenteService service = new OeuvreVenteService();
			
			int id = -1;
			if(request.getParameter("idOeuvre") != null && request.getParameter("idOeuvre") != "") {
				id = Integer.parseInt(request.getParameter("idOeuvre"));
			}
			
			Oeuvrevente oeuvre;
			if(id > 0) {
				oeuvre = service.consulterOeuvrevente(id);
			} else {
				oeuvre = new Oeuvrevente();
			}
			
			ProprietaireService serviceP = new ProprietaireService();
			Proprietaire proprietaire = serviceP.consulterProprietaire(Integer.parseInt(request.getParameter("txtProprietaire")));
			
			oeuvre.setEtatOeuvrevente("L");
			oeuvre.setTitreOeuvrevente(request.getParameter("txtTitre").replace("'", "\\\'"));
			oeuvre.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtPrix")));
			oeuvre.setProprietaire(proprietaire);
			
			if(id > 0) {
				service.updateOeuvreVente(oeuvre);
			} else {
				service.insertOeuvreVente(oeuvre);
			}
			
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}
		
		return new ModelAndView("redirect:/"+OEUVREVENTE+"/");
	}
	
	/**
	 * Suppression d'une oeuvre vente
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREVENTE+"/"+SUPPRIMER)
	protected ModelAndView deleteObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			OeuvreVenteService service = new OeuvreVenteService();
			int id = Integer.parseInt(request.getParameter("idSelected"));
			service.deleteOeuvreVente(id);
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}
		
		return new ModelAndView("redirect:/"+OEUVREVENTE+"/");
	}
}
