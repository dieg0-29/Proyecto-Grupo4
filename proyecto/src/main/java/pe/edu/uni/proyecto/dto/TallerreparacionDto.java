package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TallerreparacionDto {
	
	@JsonProperty("nombreTaller")
	private String nombreTaller;
	
	@JsonProperty("totalOperaciones")
	private int totalOperaciones;
}