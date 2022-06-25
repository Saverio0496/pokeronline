package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface CustomTavoloRepository {

	List<Tavolo> findByExample(Tavolo example, Utente utente);

}
