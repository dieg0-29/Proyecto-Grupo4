package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ConductorDto {
	
	@JsonProperty("idConductor")
	private int idConductor;

	@JsonProperty("idEstado")
	private int idEstado;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("apellido")
	private String apellido;
	
	@JsonProperty("dni")
	private String dni; 
	
	@JsonProperty("correo")
	private String correo;
	
	@JsonProperty("telefono")
	private String telefono;
}