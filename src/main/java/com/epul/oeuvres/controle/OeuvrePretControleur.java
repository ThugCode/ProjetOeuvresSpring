package com.epul.oeuvres.controle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.epul.oeuvres.dao.OeuvrePretService;
import com.epul.oeuvres.dao.ProprietaireService;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Oeuvrepret;
import com.epul.oeuvres.metier.Proprietaire;

/**
 * Classe Servlet pour les oeuvres pret
 * 
 * @author GERLAND - LETOURNEUR
 */

@Controller
public class OeuvrePretControleur extends MultiActionController {
	
	private static final String FORM = "form";
	private static final String LISTE = "liste";
	private static final String INSERER = "inserer";
	private static final String AJOUTER = "ajouter";
	private static final String MODIFIER = "modifier";
	private static final String SUPPRIMER = "supprimer";
	private static final String OEUVREPRET = "OeuvrePret";
	
	private int page;
	private int nombreParPage;

	public OeuvrePretControleur() {
		super();
	}

	/**
	 * Affichage de la liste des oeuvres
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREPRET+"/")
	public ModelAndView displayListe(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String destinationPage = LISTE+OEUVREPRET;
		
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
		
		OeuvrePretService service = new OeuvrePretService();
		List<Oeuvrepret> listeTotal = service.consulterListeOeuvresPret();
		float nombreOeuvre = Float.parseFloat(listeTotal.size()+"");
		int nombrePage = (int) Math.ceil(nombreOeuvre/nombreParPage);
		request.setAttribute("nbPage", nombrePage);
		request.setAttribute("tabTitle", "Liste des prêts");
		//request.setAttribute("module", LISTE+OEUVREPRET);
		
		if(Integer.parseInt(request.getAttribute("currentPage").toString()) > nombrePage) {
			page = 1;
			request.setAttribute("currentPage", page);
		}
		
		List<Oeuvrepret> liste = service.consulterListeOeuvresPret((int)page-1,(int)nombreParPage);
		request.setAttribute("oeuvres", liste);
		
		return new ModelAndView(destinationPage);
	}
	
	/**
	 * Affichage du formulaire d'ajout
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREPRET+"/"+AJOUTER)
	public ModelAndView displayAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = FORM+OEUVREPRET;
		
		request.setAttribute("tabTitle", "Nouvelle oeuvre pret");
		request.setAttribute("module", FORM+OEUVREPRET);
		request.setAttribute("vue", FORM);
		request.setAttribute("action", "Ajouter");
		
		ProprietaireService service = new ProprietaireService();
		List<Proprietaire> liste;
		liste = service.consulterListeProprietaires();
		request.setAttribute("proprietaires", liste);
		
		return new ModelAndView(destinationPage);
	}
	
	/**
	 * Affichage du formulaire de modification
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREPRET+"/"+MODIFIER+"/{idOeuvrePret}")
	public ModelAndView displayUpdateForm(HttpServletRequest request, HttpServletResponse response, @PathVariable("idOeuvrePret")int idOeuvrePret) throws Exception {

		String destinationPage = FORM+OEUVREPRET;
		
		ProprietaireService service = new ProprietaireService();
		List<Proprietaire> liste = service.consulterListeProprietaires();
		request.setAttribute("proprietaires", liste);
		
		OeuvrePretService serviceO = new OeuvrePretService();
		Oeuvrepret oeuvreAModifier = serviceO.consulterOeuvrePret(idOeuvrePret);
		request.setAttribute("oeuvrePret", oeuvreAModifier);
		request.setAttribute("tabTitle", "Modification oeuvre pret");
		request.setAttribute("module", FORM+OEUVREPRET);
		request.setAttribute("vue", FORM);
		request.setAttribute("action", "Modifier");
		
		return new ModelAndView(destinationPage);
	}
	
	/**
	 * Ajout ou modification d'une oeuvre pret
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREPRET+"/"+INSERER)
	public ModelAndView insertNewObject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = "/"+OEUVREPRET+"/";
		try {
			
			OeuvrePretService service = new OeuvrePretService();
			
			int id = -1;
			if(request.getParameter("idOeuvre") != null 
			&& request.getParameter("idOeuvre") != "") {
				id = Integer.parseInt(request.getParameter("idOeuvre"));
			}
			
			Oeuvrepret oeuvrePret;
			if(id > 0) {
				oeuvrePret = service.consulterOeuvrePret(id);
			} else {
				oeuvrePret = new Oeuvrepret();
			}
			
			ProprietaireService pService = new ProprietaireService();
			Proprietaire proprietaire = pService.consulterProprietaire(Integer.parseInt(request.getParameter("txtProprietaire")));
			
			oeuvrePret.setTitreOeuvrepret(request.getParameter("txtTitre").replace("'", "\\\'"));
			oeuvrePret.setProprietaire(proprietaire);
			
			if(id > 0) {
				service.updateOeuvrePret(oeuvrePret);
			} else {
				service.insertOeuvrePret(oeuvrePret);
			}
			
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}
		
		return new ModelAndView("redirect:"+destinationPage);
	}
	
	/**
	 * Suppression d'une oeuvre pret
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = OEUVREPRET+"/"+SUPPRIMER)
	protected ModelAndView deleteObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String destinationPage = "/"+OEUVREPRET+"/";
		try {
			OeuvrePretService service = new OeuvrePretService();
			int id = Integer.parseInt(request.getParameter("idSelected"));
			service.deleteOeuvrePret(id);

		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}
		
		return new ModelAndView("redirect:"+destinationPage);
	}
}
