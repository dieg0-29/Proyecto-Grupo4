package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConductorFrecuenteDto {
	private int idConductor;
	private String nombre;
	private String apellido;
	private int cantidad;
	private String fechaInicio;
	private String fechaFinal;
}
