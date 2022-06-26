package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloService {

	List<Tavolo> listAllTavoli();

	List<Tavolo> listAllTavoliCreatiDaUtente(Utente utenteInstance);

	Tavolo caricaSingoloTavolo(Long id);

	Tavolo caricaSingoloTavoloPerLoSpecialPlayer(Long id, Utente utente);

	Tavolo caricaSingoloTavoloConGiocatori(Long id);

	Tavolo caricaSingoloTavoloEager(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance, Tavolo tavoloCaricato);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuovi(Tavolo tavoloInstance);

	List<Tavolo> findByExample(Tavolo example, Utente utente);

	Tavolo findByDenominazione(String denominazione);

	List<Tavolo> findTavoloByGiocatoreContains(Utente utente);
}
