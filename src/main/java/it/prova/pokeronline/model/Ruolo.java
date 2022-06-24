package it.prova.pokeronline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ruolo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ruolo {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_PLAYER = "ROLE_PLAYER";
	public static final String ROLE_SPECIAL_PLAYER = "ROLE_SPECIAL_PLAYER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "codice")
	private String codice;
}
