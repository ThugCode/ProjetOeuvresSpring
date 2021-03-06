package com.epul.oeuvres.controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.epul.oeuvres.dao.AdherentService;
import com.epul.oeuvres.dao.OeuvreVenteService;
import com.epul.oeuvres.dao.ReservationService;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Adherent;
import com.epul.oeuvres.metier.Oeuvrevente;
import com.epul.oeuvres.metier.Reservation;


/**
 * Classe Servlet pour les réservations
 * 
 * @author GERLAND - LETOURNEUR
 */

@Controller
public class ReservationControleur extends MultiActionController {
	
	private static final String FORM = "form";
	private static final String LISTE = "liste";
	private static final String INSERER = "inserer";
	private static final String AJOUTER = "ajouter";
	private static final String MODIFIER = "modifier";
	private static final String SUPPRIMER = "supprimer";
	private static final String RESERVATION = "Reservation";
	
	private int page;
	private int nombreParPage;
	
	public ReservationControleur(){
		super();
	}

	/**
	 * Affichage de la liste des réservations
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/")
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
			request.setAttribute("tabTitle", "Liste des réservations");
			request.setAttribute("module", LISTE+RESERVATION);
			
			ReservationService service = new ReservationService();
			List<Reservation> listeTotal = service.consulterListeReservations();
			float nombreReservation = Float.parseFloat(listeTotal.size()+"");
			int nombrePage = (int) Math.ceil(nombreReservation/nombreParPage);
			request.setAttribute("nbPage", nombrePage);
			
			if(Integer.parseInt(request.getAttribute("currentPage").toString()) > nombrePage) {
				page = 1;
				request.setAttribute("currentPage", page);
			}
			
			List<Reservation> liste = service.consulterListeReservations((int)page-1,(int)nombreParPage);
			request.setAttribute("reservations", liste);
			
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");

		}
		return new ModelAndView(LISTE+RESERVATION);
	}
	
	/**
	 * Affichage du formulaire d'ajout
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/"+AJOUTER+"/{idOeuvrevente}")
	public ModelAndView displayAddFormWithOeuvre(HttpServletRequest request, 
			HttpServletResponse response, @PathVariable("idOeuvrevente")int idOeuvrevente) throws Exception {

		OeuvreVenteService oService = new OeuvreVenteService();
		Oeuvrevente oeuvre = oService.consulterOeuvrevente(idOeuvrevente);
		request.setAttribute("oeuvre", oeuvre);
		
		return displayAddForm(request, response, oeuvre);
	}
	
	/**
	 * Affichage du formulaire d'ajout
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/"+AJOUTER)
	public ModelAndView displayAddFormWithoutOeuvre(HttpServletRequest request, HttpServletResponse response) throws Exception {

		return displayAddForm(request, response, null);
	}

	
	public ModelAndView displayAddForm(HttpServletRequest request, HttpServletResponse response, Oeuvrevente oeuvre) throws Exception {

		try {
			
			request.setAttribute("tabTitle", "Nouvelle réservation");
			request.setAttribute("module", FORM+RESERVATION);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Ajouter");
			
			AdherentService aService = new AdherentService();
			List<Adherent> adherents = aService.consulterListeAdherents();
			request.setAttribute("adherents", adherents);
			
			if(oeuvre == null) {
				OeuvreVenteService oService = new OeuvreVenteService();
				List<Oeuvrevente> oeuvres = oService.consulterListeOeuvresVentes();
				request.setAttribute("oeuvres", oeuvres);
			}
			
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}

		return new ModelAndView(FORM+RESERVATION);
	}
	
	/**
	 * Affichage du formulaire de modification
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/"+MODIFIER)
	public ModelAndView displayUpdateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			OeuvreVenteService oService = new OeuvreVenteService();
			Oeuvrevente oeuvre = oService.consulterOeuvrevente(
					Integer.parseInt(request.getParameter("idOeuvrevente").toString()));
			request.setAttribute("oeuvre", oeuvre);
			
			ReservationService service = new ReservationService();
			Reservation reservationAModifier = service.consulterReservation(
					Integer.parseInt(request.getParameter("idOeuvrevente").toString()), 
					Integer.parseInt(request.getParameter("idAdherent").toString()));
			
			request.setAttribute("reservation", reservationAModifier);
			request.setAttribute("tabTitle", "Modification reservation");
			request.setAttribute("module", FORM+RESERVATION);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Modifier");
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}
		return new ModelAndView(FORM+RESERVATION);
	}
	
	/**
	 * Ajout ou modification d'une réservation
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/"+INSERER)
	public ModelAndView insertNewObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			ReservationService service = new ReservationService();
			boolean ajout = false;
			Reservation reservation = null;
			
			if(request.getParameter("oldOeuvre") != "" 
			&& request.getParameter("oldAdherent") != "") {
				reservation= service.consulterReservation(
					Integer.parseInt(request.getParameter("oldOeuvre").toString()),
					Integer.parseInt(request.getParameter("oldAdherent").toString()));
			}
			
			if(reservation == null) {
				ajout = true;
				reservation = new Reservation();
			}
			
			Date date = null;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			
			reservation.setDateReservation(date);
			reservation.setStatut("confirmee");
			
			if(ajout) {
				
				OeuvreVenteService oService = new OeuvreVenteService();
				Oeuvrevente oeuvre = oService.consulterOeuvrevente(Integer.parseInt(request.getParameter("idOeuvre").toString()));
				reservation.setOeuvrevente(oeuvre);
				
				AdherentService aService = new AdherentService();
				Adherent adherent = aService.consulterAdherent(Integer.parseInt(request.getParameter("idAdherent").toString()));
				reservation.setAdherent(adherent);
				
				if(service.consulterReservation(
						reservation.getOeuvrevente().getIdOeuvrevente(), 
						reservation.getAdherent().getIdAdherent()) != null) {
					request.setAttribute("messageErreur", "Réservation déjà effective pour cette oeuvre et cette personne");
					return new ModelAndView("erreur");
				}
				
				service.insertReservation(reservation);
			} else {
				service.updateReservation(reservation);
			}
		
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}
		
		return new ModelAndView("redirect:/"+RESERVATION+"/");
	}
	
	/**
	 * Suppression d'une réservation
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/"+SUPPRIMER)
	protected ModelAndView deleteObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			ReservationService service = new ReservationService();
			int idOeuvre = Integer.parseInt(request.getParameter("idSelected"));
			int idAdherent = Integer.parseInt(request.getParameter("idSelected2"));
			service.deleteReservation(idOeuvre, idAdherent);
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			return new ModelAndView("erreur");
		}
		
		return new ModelAndView("redirect:/"+RESERVATION+"/");
	}
}
