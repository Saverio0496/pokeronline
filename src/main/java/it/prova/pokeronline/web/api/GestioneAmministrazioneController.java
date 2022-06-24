package it.prova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("/api/gestioneAmministrazione")
public class GestioneAmministrazioneController {
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public List<UtenteDTO> getAll() {
		return UtenteDTO.createFilmDTOListFromModelList(utenteService.listAllUtenti());
	}

}
