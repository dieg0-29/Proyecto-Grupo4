package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IncidenteDto {
	
	@JsonProperty("empleado")
	private int empleado;
	
	@JsonProperty("programacion")
	private int programacion;
	
	@JsonProperty("tipoIncidente")
	private int tipoIncidente;
	
	@JsonProperty("fecha")
	private String fecha;
	
	@JsonProperty("detalle")
	private String detalle;
	
}