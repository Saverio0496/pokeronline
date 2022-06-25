package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloRepository extends CrudRepository<Tavolo, Long> {

	@EntityGraph(attributePaths = { "giocatori", "utenteCreazione" })
	List<Tavolo> findAllByUtenteCreazione_Id(Long id);

	Tavolo findByDenominazione(String denominazione);

	Tavolo findByIdAndUtenteCreazione(Long id, Utente utenteCreazione);

}
