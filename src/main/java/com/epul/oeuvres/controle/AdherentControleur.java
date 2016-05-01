package com.epul.oeuvres.controle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.epul.oeuvres.dao.AdherentService;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Adherent;


/**
 * Classe Servlet pour les adhérents
 * 
 * @author GERLAND - LETOURNEUR
 */

@Controller
public class AdherentControleur extends MultiActionController {
	
	private static final String FORM = "form";
	private static final String LISTE = "liste";
	private static final String INSERER = "inserer";
	private static final String AJOUTER = "ajouter";
	private static final String MODIFIER = "modifier";
	private static final String SUPPRIMER = "supprimer";
	private static final String ADHERENT = "Adherent";
	
	private int page;
	private int nombreParPage;

	public AdherentControleur() {
		super();
	}

	@RequestMapping(value = LISTE+ADHERENT)
	public ModelAndView displayListe(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String destinationPage;
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
			
			AdherentService service = new AdherentService();
			List<Adherent> listeTotal = service.consulterListeAdherents();
			float nombreAdherent = Float.parseFloat(listeTotal.size()+"");
			int nombrePage = (int) Math.ceil(nombreAdherent/nombreParPage);
			request.setAttribute("nbPage", nombrePage);
			request.setAttribute("tabTitle", "Liste des adhérents");
			request.setAttribute("module", LISTE+ADHERENT);
			
			if(Integer.parseInt(request.getAttribute("currentPage").toString()) > nombrePage) {
				page = 1;
				request.setAttribute("currentPage", page);
			}
			
			List<Adherent> liste = service.consulterListeAdherents((int)page-1,(int)nombreParPage);
			request.setAttribute("adherents", liste);
			
			destinationPage = LISTE+ADHERENT;
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";

		}
		return new ModelAndView(destinationPage);
	}
	
	/**
	 * Affichage du formulaire d'ajout
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = AJOUTER+ADHERENT)
	public ModelAndView displayAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = "";
		try {
			destinationPage = FORM+ADHERENT;
			request.setAttribute("tabTitle", "Nouvel adhérent");
			request.setAttribute("module", FORM+ADHERENT);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Ajouter");
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * Affichage du formulaire de modification
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = MODIFIER+ADHERENT)
	public ModelAndView displayUpdateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = "";
		try {
			AdherentService unService = new AdherentService();
			Adherent adherentAModifier = unService.consulterAdherent(Integer.parseInt(request.getParameter("idAdherent")));
			request.setAttribute("adherent", adherentAModifier);
			destinationPage = FORM+ADHERENT;
			request.setAttribute("tabTitle", "Modification adhérent");
			request.setAttribute("module", FORM+ADHERENT);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Modifier");
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}

		return new ModelAndView(destinationPage);
	}
	
	/**
	 * Ajout ou modification d'un adhérent
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = INSERER+ADHERENT)
	public ModelAndView insertNewObject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = "index";
		try {
			AdherentService unService = new AdherentService();
			
			int id = -1;
			if(request.getParameter("idAdherent") != null 
			&& request.getParameter("idAdherent") != "") {
				id = Integer.parseInt(request.getParameter("idAdherent"));
			}
			
			Adherent adherent;
			if(id > 0) {
				adherent = unService.consulterAdherent(id);
			} else {
				adherent = new Adherent();
			}
			adherent.setNomAdherent(request.getParameter("txtnom").replace("'", "\\\'"));
			adherent.setPrenomAdherent(request.getParameter("txtprenom").replace("'", "\\\'"));
			adherent.setVilleAdherent(request.getParameter("txtville").replace("'", "\\\'"));
			
			if(id > 0) {
				unService.updateAdherent(adherent);
			} else {
				unService.insertAdherent(adherent);
			}
			
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}
		
		return new ModelAndView(destinationPage);
	}
	
	/**
	 * Suppression d'un adhérent
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = SUPPRIMER+ADHERENT)
	protected ModelAndView deleteObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String destinationPage = "";
		try {
			AdherentService unService = new AdherentService();
			int id = Integer.parseInt(request.getParameter("idSelected"));
			unService.deleteAdherent(id);
			destinationPage = LISTE+ADHERENT;
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}
		
		destinationPage = "index";
		return new ModelAndView(destinationPage);
	}
}