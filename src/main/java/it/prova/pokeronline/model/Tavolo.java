package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tavolo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tavolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "esperienzamin")
	private Integer esperienzaMin;
	@Column(name = "ciframinima")
	private Integer cifraMinima;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "datacreazione")
	private Date dataCreazione;

	@OneToMany
	@Builder.Default
	private Set<Utente> giocatori = new HashSet<Utente>(0);

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utentecreazione_id", referencedColumnName = "id", nullable = false)
	private Utente utenteCreazione;

}
