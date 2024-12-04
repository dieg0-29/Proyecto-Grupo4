package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FinalizarDto {
	
	@JsonProperty("idProgramacion")
	private int idProgramacion;

	@JsonProperty("fechaFinReal")
	private String fechaFinReal;
}