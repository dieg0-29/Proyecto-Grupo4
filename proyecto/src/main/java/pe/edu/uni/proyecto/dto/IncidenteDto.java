package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IncidenteDto {
	
	private int empleado;
	private int programacion;
	private int tipo_incidente;
	private String fecha;
	private String detalle;
	
}