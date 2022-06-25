package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;

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
		return (List<Tavolo>) tavoloRepository.findAllTavoliByUtente(utente);
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavolo(Long id) {
		return null;
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConGiocatori(Long id) {
		return null;
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConUtente(Long id, Utente utente) {
		return null;
	}

	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		return null;
	}

	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return tavoloRepository.save(tavoloInstance);
	}

	@Transactional
	public void rimuoviPerId(Tavolo tavoloInstance) {
	}

	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(Tavolo example) {
		return null;
	}
	
	@Transactional
	public Tavolo findByDenominazione(String denominazione) {
		return tavoloRepository.findByDenominazione(denominazione);
	}

}
