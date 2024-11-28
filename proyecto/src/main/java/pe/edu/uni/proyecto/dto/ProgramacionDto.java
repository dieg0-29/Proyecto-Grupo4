package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProgramacionDto {
	
	@JsonProperty("idProgramacion")
	private int idProgramacion;
	
	@JsonProperty("idCarro")
	private int idCarro;
	
	@JsonProperty("idEmpleado")
	private int idEmpleado;
	
	@JsonProperty("idConductor")
	private int idConductor;
	
	@JsonProperty("idRuta")
	private int idRuta;
	
	@JsonProperty("fechaAsig")
	private String fechaAsignacion;
	
	@JsonProperty("fechaFin")
	private String fechaFinProgramada;
	
	@JsonProperty("fechaFinReal")
	private String fechaFinReal;
}