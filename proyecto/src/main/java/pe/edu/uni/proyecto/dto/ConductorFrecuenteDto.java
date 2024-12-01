package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConductorFrecuenteDto {
	
	@JsonProperty("idConductor")
	private int idConductor;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("apellido")
	private String apellido;
	
	@JsonProperty("cantidad")
	private int cantidad;
	
	@JsonProperty("fechaInicio")
	private String fechaInicio;
	
	@JsonProperty("fechaFinal")
	private String fechaFinal;
}
