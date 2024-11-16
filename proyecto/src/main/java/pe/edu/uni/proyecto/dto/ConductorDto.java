package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ConductorDto {
	private int idConductor;
	private String nombre;
	private String apellido;
	private String dni; 
	private String correo;
	private String telefono;
}