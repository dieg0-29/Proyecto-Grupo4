package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FinalizarDto {
	
	@JsonProperty("idProgramacion")
	private int idProgramacion;
	
	@JsonProperty("idCarro")
	private int idCarro;
	
	@JsonProperty("idConductor")
	private int idConductor;
	
	@JsonProperty("fechaFinReal")
	private String fechaFinReal;
}