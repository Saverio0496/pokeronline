package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.web.api.exception.TavoloAncoraConGiocatoriException;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	TavoloRepository tavoloRepository;

	@Transactional(readOnly = true)
	public List<Tavolo> listAllTavoli() {
		return (List<Tavolo>) tavoloRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Tavolo> listAllTavoliCreatiDaUtente(Utente utente) {
		return (List<Tavolo>) tavoloRepository.findAllByUtenteCreazione_Id(utente.getId());
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavolo(Long id) {
		return tavoloRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloPerLoSpecialPlayer(Long id, Utente utente) {
		return tavoloRepository.findByIdAndUtenteCreazione(id, utente);
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConGiocatori(Long id) {
		return tavoloRepository.findByIdEagerGiocatori(id);
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloEager(Long id) {
		return tavoloRepository.findByIdEager(id);
	}

	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance, Tavolo tavoloCaricato) {
		if (!tavoloCaricato.getGiocatori().isEmpty()) {
			throw new TavoloAncoraConGiocatoriException(
					"Impossibile aggiornare il tavolo perchè ci sono ancora giocatori!");
		}
		return tavoloRepository.save(tavoloInstance);
	}

	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return tavoloRepository.save(tavoloInstance);
	}

	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		if (!tavoloInstance.getGiocatori().isEmpty()) {
			throw new TavoloAncoraConGiocatoriException(
					"Impossibile eliminare il tavolo perchè ci sono ancora giocatori!");
		}
		tavoloRepository.delete(tavoloInstance);
	}

	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(Tavolo example, Utente utente) {
		return tavoloRepository.findByExample(example, utente);
	}

	@Transactional
	public Tavolo findByDenominazione(String denominazione) {
		return tavoloRepository.findByDenominazione(denominazione);
	}

	@Transactional
	public List<Tavolo> findTavoloByGiocatoreContains(Utente utente) {
		return tavoloRepository.findTavoloDoveGiocatoreEPresente(utente);
	}

	@Transactional
	public void abbandonaPartita(Long idTavolo, Utente giocatore) {
		Tavolo tavolo = tavoloRepository.findById(idTavolo).orElse(null);
		if (tavolo == null) {
			return;
		}
		tavolo.getGiocatori().remove(giocatore);
		giocatore.setEsperienzaAccumulata(giocatore.getEsperienzaAccumulata() + 1);

	}

}
