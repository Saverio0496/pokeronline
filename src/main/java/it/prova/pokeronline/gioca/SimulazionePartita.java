package it.prova.pokeronline.gioca;

public class SimulazionePartita {
	
	public static int simulaPartita() {
		
		Double segno = Math.random();
		Double somma = 0.0;
		if (segno >=0.5) {
			somma = Math.random() * 1000;
		}else {
			somma =  Math.random() * -1000;
		}
		
		return somma.intValue();
	}

}
