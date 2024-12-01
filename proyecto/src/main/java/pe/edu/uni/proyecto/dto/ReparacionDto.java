package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReparacionDto {
	
	@JsonProperty("idEmpleado")
	private int idEmpleado;
	 
	@JsonProperty("idIncidente")
	private int idIncidente;
	
	@JsonProperty("idTaller")
	private int idTaller;
	
	@JsonProperty("fechaReparacion")
	private String fechaReparacion;
	
	@JsonProperty("calificacion")
	private double calificacion;
	
	@JsonProperty("costo")
	private double costo;
	
	@JsonProperty("detalle")
	private String detalle;

}
