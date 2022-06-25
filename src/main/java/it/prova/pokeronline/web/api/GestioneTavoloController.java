package it.prova.pokeronline.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("/api/gestioneTavolo")
public class GestioneTavoloController {

	@Autowired
	private TavoloService tavoloService;

	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<TavoloDTO> getAll() {
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			List<TavoloDTO> list = TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllTavoli());
			return list;
		}
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllTavoliCreatiDaUtente(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}

	@PostMapping
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {

		if (tavoloInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Tavolo tavoloDaInserire = tavoloInput.buildTavoloModel(true);
		tavoloDaInserire.setUtenteCreazione(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloDaInserire);
		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito);

	}

	@GetMapping("/{id}")
	public TavoloDTO findById(@PathVariable(value = "id", required = true) long id) {
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			return TavoloDTO.buildTavoloDTOFromModel(tavoloService.caricaSingoloTavolo(id));
		}
		return TavoloDTO.buildTavoloDTOFromModel(tavoloService.caricaSingoloTavoloPerLoSpecialPlayer(id,
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}
	
	@PostMapping("/search")
	public List<TavoloDTO> findByExample(@RequestBody TavoloDTO exampleDTO) {

		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(exampleDTO.buildTavoloModel(true), null));

		}
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(exampleDTO.buildTavoloModel(true),
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));

	}
}
