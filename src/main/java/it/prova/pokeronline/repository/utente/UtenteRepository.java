package it.prova.pokeronline.repository.utente;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

	@EntityGraph(attributePaths = { "ruoli" })
	Optional<Utente> findByUsername(String username);
}
