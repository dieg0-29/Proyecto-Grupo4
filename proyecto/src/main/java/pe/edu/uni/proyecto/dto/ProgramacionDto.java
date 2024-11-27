package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProgramacionDto {
	
	@JsonProperty("id_programacion")
	private int idProgramacion;
	
	@JsonProperty("id_carro")
	private int idCarro;
	
	@JsonProperty("id_empleado")
	private int idEmpleado;
	
	@JsonProperty("id_conductor")
	private int idConductor;
	
	@JsonProperty("id_ruta")
	private int idRuta;
	
	@JsonProperty("fecha_asig")
	private String fechaAsignacion;
	
	@JsonProperty("fecha_fin")
	private String fechaFinProgramada;
	
	@JsonProperty("fecha_real")
	private String fechaFinReal;
}