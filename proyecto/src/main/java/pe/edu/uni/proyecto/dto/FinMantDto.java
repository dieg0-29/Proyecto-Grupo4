package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FinMantDto {
	
	@JsonProperty("idMant")
	private int idMant;

	@JsonProperty("fechaFinReal")
	private String fechaFinReal;
}