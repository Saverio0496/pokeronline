package it.prova.pokeronline.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TavoloDTO {

	private Long id;

	@NotNull(message = "{esperienzaMin.notnull}")
	private Integer esperienzaMin;

	@NotNull(message = "{cifraMinima.notnull}")
	private Integer cifraMinima;

	@NotBlank(message = "{denominazione.notblank}")
	private String denominazione;

	private Date dataCreazione;

	@JsonIgnoreProperties(value = { "tavolo" })
	private Long[] giocatoriIds;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@NotNull(message = "{utenteCreazione.notnull}")
	private Utente utenteCreazione;

	public Tavolo buildTavoloModel(boolean includeIdGiocatori) {
		Tavolo result = Tavolo.builder().id(this.id).esperienzaMin(this.esperienzaMin).cifraMinima(this.cifraMinima)
				.denominazione(this.denominazione).dataCreazione(this.dataCreazione)
				.utenteCreazione(this.utenteCreazione).build();
		if (includeIdGiocatori && giocatoriIds != null)
			result.setGiocatori(Arrays.asList(giocatoriIds).stream().map(id -> Utente.builder().id(id).build())
					.collect(Collectors.toSet()));
		return result;
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel) {
		TavoloDTO result = TavoloDTO.builder().id(tavoloModel.getId()).esperienzaMin(tavoloModel.getEsperienzaMin())
				.cifraMinima(tavoloModel.getCifraMinima()).denominazione(tavoloModel.getDenominazione())
				.dataCreazione(tavoloModel.getDataCreazione()).utenteCreazione(tavoloModel.getUtenteCreazione())
				.build();

		if (!tavoloModel.getGiocatori().isEmpty())
			result.giocatoriIds = tavoloModel.getGiocatori().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});

		return result;
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput) {
		return modelListInput.stream().map(tavoloEntity -> {
			return TavoloDTO.buildTavoloDTOFromModel(tavoloEntity);
		}).collect(Collectors.toList());
	}
}
