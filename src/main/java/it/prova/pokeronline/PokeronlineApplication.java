package it.prova.pokeronline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private TavoloService tavoloServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance
					.inserisciNuovo(Ruolo.builder().descrizione("Administrator").codice(Ruolo.ROLE_ADMIN).build());
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Player", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance
					.inserisciNuovo(Ruolo.builder().descrizione("Player").codice(Ruolo.ROLE_PLAYER).build());
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(
					Ruolo.builder().descrizione("Special Player").codice(Ruolo.ROLE_SPECIAL_PLAYER).build());
		}

		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = Utente.builder().username("admin").password("admin").nome("Mario").cognome("Rossi")
					.dataRegistrazione(new Date()).build();
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			utenteServiceInstance.inserisciNuovo(admin);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("player") == null) {
			Utente player = Utente.builder().username("player").password("player").nome("Cristian").cognome("Dollaro")
					.dataRegistrazione(new Date()).build();
			player.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(player);
			utenteServiceInstance.changeUserAbilitation(player.getId());
		}

		if (utenteServiceInstance.findByUsername("specialplayer") == null) {
			Utente specialPlayer = Utente.builder().username("specialplayer").password("specialplayer").nome("Lorenzo")
					.cognome("Mago").dataRegistrazione(new Date()).build();
			specialPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
			utenteServiceInstance.inserisciNuovo(specialPlayer);
			utenteServiceInstance.changeUserAbilitation(specialPlayer.getId());
		}

		Set<Utente> utentiAlTavolo = new HashSet<Utente>();
		utentiAlTavolo.add(utenteServiceInstance.findByUsername("player"));
		utentiAlTavolo.add(utenteServiceInstance.findByUsername("specialplayer"));

		Utente utenteCreazione1 = utenteServiceInstance.findByUsername("admin");
		String denominazione1 = "H501";
		Tavolo tavolo1 = tavoloServiceInstance.findByDenominazione(denominazione1);

		if (tavolo1 == null) {
			tavolo1 = new Tavolo(1L, 0, 0, denominazione1, new SimpleDateFormat("dd/MM/yyyy").parse("18/12/2010"),
					utentiAlTavolo, utenteCreazione1);
			tavoloServiceInstance.inserisciNuovo(tavolo1);
		}

	}

}
