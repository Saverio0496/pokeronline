package it.prova.pokeronline;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance
					.inserisciNuovo(Ruolo.builder().descrizione("Administrator").codice(Ruolo.ROLE_ADMIN).build());
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance
					.inserisciNuovo(Ruolo.builder().descrizione("Classic User").codice(Ruolo.ROLE_PLAYER).build());
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
		}

		if (utenteServiceInstance.findByUsername("specialplayer") == null) {
			Utente specialPlayer = Utente.builder().username("specialplayer").password("specialplayer").nome("Lorenzo")
					.cognome("Mago").dataRegistrazione(new Date()).build();
			specialPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(specialPlayer);
		}
	}

}
