package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReparacionDto {
	
	@JsonProperty("idempleado")
	private int idempleado;
	 
	@JsonProperty("idincidente")
	private int idincidente;
	
	@JsonProperty("idtaller")
	private int idtaller;
	
	@JsonProperty("fechareparacion")
	private String fechareparacion;
	
	@JsonProperty("calificacion")
	private double calificacion;
	
	@JsonProperty("costo")
	private double costo;
	
	@JsonProperty("detalle")
	private String detalle;

}
