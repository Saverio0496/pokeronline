package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloRepository extends CrudRepository<Tavolo, Long> {

	@Query("from Tavolo t where t.utenteCreazione=:utente")
	List<Tavolo> findAllTavoliByUtente(Utente utente);
	
	Tavolo findByDenominazione(String denominazione);

}
