package it.prova.pokeronline.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
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
	public Tavolo dammiIlLastGame() {
		Utente utente = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (utente == null) {
			throw new UtenteNotFoundException("Utente non trovato");
		}
		Tavolo tavoliDoveUtenteEPresente = tavoloService.findTavoloByGiocatoreContains(utente);

		if (tavoliDoveUtenteEPresente == null) {
			throw new TavoloNotFoundException("L'utente non sta partecipando a nessun tavolo!");
		}

		return tavoliDoveUtenteEPresente;

	}

}
