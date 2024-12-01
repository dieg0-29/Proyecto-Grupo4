package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IncidenteRutaDto {
	
	@JsonProperty("nombreRuta")
	private String nombreRuta;
	
	@JsonProperty("totalIncidentes")
	private int totalIncidentes;
}
