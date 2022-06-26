package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository {

	@EntityGraph(attributePaths = { "giocatori", "utenteCreazione" })
	List<Tavolo> findAllByUtenteCreazione_Id(Long id);

	Tavolo findByDenominazione(String denominazione);

	Tavolo findByIdAndUtenteCreazione(Long id, Utente utenteCreazione);
	
	@Query("from Tavolo t left join fetch t.giocatori where t.id=?1")
	Tavolo findByIdEagerGiocatori(Long idTavolo);
	
	@Query("select t from Tavolo t join fetch t.giocatori join fetch t.utenteCreazione where t.id = ?1")
	Tavolo findByIdEager(Long id);

	@Query("from Tavolo t join fetch t.giocatori g where g=:utente")
	List<Tavolo> findTavoloDoveGiocatoreEPresente (Utente utente);

}
