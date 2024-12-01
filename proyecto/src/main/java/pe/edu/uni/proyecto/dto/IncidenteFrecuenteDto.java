package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenteFrecuenteDto {
	
	@JsonProperty("idTipo")
	private int idTipo;
	
	@JsonProperty("descripcion")
	private String descripcion;
	
	@JsonProperty("cantidad")
	private int cantidad;
}
