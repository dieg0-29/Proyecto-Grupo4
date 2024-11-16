package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TallerDto {
	
	private int idTaller;
	private String nombreTaller;
	private String direccion;
	private String telefono; 
	private double calificacion;
	
}
