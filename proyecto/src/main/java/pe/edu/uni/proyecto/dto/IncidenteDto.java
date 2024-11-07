package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IncidenteDto {
	private int id_incidente;
	private int id_prog;
	private String fecha;
	private String descripcion;
	private int id_tipo;
}
