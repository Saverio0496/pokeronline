package it.prova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.gioca.SimulazionePartita;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.CreditoMinimoInsufficienteException;
import it.prova.pokeronline.web.api.exception.NonHaiAbbastanzaEsperienzaException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web.api.exception.UtenteNotFoundException;

@RestController
@RequestMapping("/api/playManagement")
public class PlayManagementController {

	@Autowired
	private TavoloService tavoloService;

	@Autowired
	private UtenteService utenteService;

	@GetMapping("/compraCredito/{creditoDaAggiungere}")
	public Integer compraCredito(
			@PathVariable(value = "creditoDaAggiungere", required = true) Integer creditoDaAggiungere) {

		Utente giocatoreCheVuoleAumentareIlProprioCredito = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (giocatoreCheVuoleAumentareIlProprioCredito.getId() < 1
				|| giocatoreCheVuoleAumentareIlProprioCredito.getId() == null
				|| giocatoreCheVuoleAumentareIlProprioCredito == null) {
			throw new UtenteNotFoundException("Utente non trovato!");
		}
		Integer nuovoCredito = giocatoreCheVuoleAumentareIlProprioCredito.getCreditoAccumulato() + creditoDaAggiungere;
		giocatoreCheVuoleAumentareIlProprioCredito.setCreditoAccumulato(nuovoCredito);
		utenteService.aggiorna(giocatoreCheVuoleAumentareIlProprioCredito);
		return giocatoreCheVuoleAumentareIlProprioCredito.getCreditoAccumulato();
	}

	@GetMapping("/dammiIlLastGame")
	public List<TavoloDTO> dammiIlLastGame() {
		List<TavoloDTO> tavoliDoveUtenteEPresente = TavoloDTO
				.createTavoloDTOListFromModelList(tavoloService.findTavoloByGiocatoreContains(utenteService
						.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
		if (tavoliDoveUtenteEPresente.isEmpty()) {
			throw new TavoloNotFoundException("L'utente non sta partecipando a nessun tavolo!");
		}
		return tavoliDoveUtenteEPresente;
	}

	@GetMapping("/abbandonaPartita/{idTavolo}")
	public void abbandonaPartita(@PathVariable(value = "idTavolo", required = true) Long idTavolo) {
		Utente giocatoreCheVuoleAbbandonareIlTavolo = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		tavoloService.abbandonaPartita(idTavolo, giocatoreCheVuoleAbbandonareIlTavolo);
	}

	@GetMapping("/ricerca")
	public List<TavoloDTO> ricerca() {
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.ricercaTavoli(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
						.getEsperienzaAccumulata()));
	}

	@GetMapping("/giocaPartitaAQuelTavolo/{idTavolo}")
	public Integer gioca(@PathVariable(value = "idTavolo", required = true) Long idTavolo) {

		Utente giocatore = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Tavolo tavolo = tavoloService.caricaSingoloTavoloConGiocatori(idTavolo);

		if (giocatore.getEsperienzaAccumulata() < tavolo.getEsperienzaMin()) {
			throw new NonHaiAbbastanzaEsperienzaException("Non hai abbastanza esperienza per giocare a questo tavolo!");
		}

		if (giocatore.getCreditoAccumulato() < tavolo.getCifraMinima()) {
			throw new CreditoMinimoInsufficienteException("Non possiedi abbastanza soldi per questo tavolo!");
		}
		if (!tavolo.getGiocatori().contains(giocatore))
			tavoloService.aggiungiGiocatoreATavolo(idTavolo, idTavolo);

		giocatore.setCreditoAccumulato(giocatore.getCreditoAccumulato() + SimulazionePartita.simulaPartita());
		if (giocatore.getCreditoAccumulato() < 0) {
			giocatore.setCreditoAccumulato(0);
		}
		utenteService.aggiorna(giocatore);

		return giocatore.getCreditoAccumulato();
	}

}
