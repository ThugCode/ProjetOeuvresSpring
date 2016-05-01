package com.epul.oeuvres.controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
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
	@RequestMapping(value = RESERVATION+"/"+LISTE+RESERVATION)
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
			request.setAttribute("tabTitle", "Liste des réservations");
			//request.setAttribute("module", LISTE+RESERVTION);
			
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
			
			destinationPage = LISTE+RESERVATION;
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
	@RequestMapping(value = RESERVATION+"/"+AJOUTER+RESERVATION)
	public ModelAndView displayAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = "";
		try {
			destinationPage = FORM+RESERVATION;
			
			request.setAttribute("tabTitle", "Nouvelle réservation");
			request.setAttribute("module", FORM+RESERVATION);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Ajouter");
			
			AdherentService aService = new AdherentService();
			OeuvreVenteService oService = new OeuvreVenteService();
			
			List<Adherent> adherents;
			adherents = aService.consulterListeAdherents();
			request.setAttribute("adherents", adherents);
			
			if(request.getParameter("idOeuvre") != null && request.getParameter("idOeuvre") != "") {
				Oeuvrevente oeuvre = oService.consulterOeuvrevente(Integer.parseInt(request.getParameter("idOeuvre").toString()));
				request.setAttribute("oeuvre", oeuvre);
			} 
			else {
				List<Oeuvrevente> oeuvres;
				oeuvres = oService.consulterListeOeuvresVentes();
				request.setAttribute("oeuvres", oeuvres);
			}
			
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
	@RequestMapping(value = RESERVATION+"/"+MODIFIER+RESERVATION)
	public ModelAndView displayUpdateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = "";
		try {
			
			AdherentService aService = new AdherentService();
			List<Adherent> adherents;
			adherents = aService.consulterListeAdherents();
			request.setAttribute("adherents", adherents);
			
			OeuvreVenteService oService = new OeuvreVenteService();
			List<Oeuvrevente> oeuvres;
			oeuvres = oService.consulterListeOeuvresVentes();
			request.setAttribute("oeuvres", oeuvres);
			
			Oeuvrevente oeuvre = oService.consulterOeuvrevente(Integer.parseInt(request.getParameter("idOeuvre").toString()));
			request.setAttribute("oeuvre", oeuvre);
			
			ReservationService service = new ReservationService();
			Reservation reservationAModifier = service.consulterReservation(
					Integer.parseInt(request.getParameter("idOeuvre")), 
					Integer.parseInt(request.getParameter("idAdherent")));
			
			request.setAttribute("reservation", reservationAModifier);
			
			destinationPage = FORM+RESERVATION;
			
			request.setAttribute("tabTitle", "Modification reservation");
			request.setAttribute("module", FORM+RESERVATION);
			request.setAttribute("vue", FORM);
			request.setAttribute("action", "Modifier");
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}

		return new ModelAndView(destinationPage);
	}
	
	/**
	 * Ajout ou modification d'une réservation
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/"+INSERER+RESERVATION)
	public ModelAndView insertNewObject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String destinationPage = LISTE+RESERVATION;
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
			
			OeuvreVenteService oService = new OeuvreVenteService();
			Oeuvrevente oeuvre = oService.consulterOeuvrevente(Integer.parseInt(request.getParameter("idOeuvre").toString()));
			
			AdherentService aService = new AdherentService();
			Adherent adherent = aService.consulterAdherent(Integer.parseInt(request.getParameter("idAdherent").toString()));
			
			Date date = null;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			
			reservation.setAdherent(adherent);
			reservation.setOeuvrevente(oeuvre);
			reservation.setDateReservation(date);
			reservation.setStatut("confirmee");
			
			if(ajout) {
				service.insertReservation(reservation);
			} else {
				int oldOeuvre = Integer.parseInt(request.getParameter("oldOeuvre").toString());
				int oldAdherent = Integer.parseInt(request.getParameter("oldAdherent").toString());
				service.updateReservation(reservation, oldOeuvre, oldAdherent);
			}
			
		} catch (Exception e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}
		
		return new ModelAndView("redirect:"+destinationPage);
	}
	
	/**
	 * Suppression d'une réservation
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RESERVATION+"/"+SUPPRIMER+RESERVATION)
	protected ModelAndView deleteObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String destinationPage = LISTE+RESERVATION;
		try {
			ReservationService service = new ReservationService();
			int idOeuvre = Integer.parseInt(request.getParameter("idSelected"));
			int idAdherent = Integer.parseInt(request.getParameter("idSelected2"));
			service.deleteReservation(idOeuvre, idAdherent);
			
		} catch (MonException e) {
			request.setAttribute("messageErreur", e.getMessage());
			destinationPage = "erreur";
		}
		
		return new ModelAndView("redirect:"+destinationPage);
	}
}
