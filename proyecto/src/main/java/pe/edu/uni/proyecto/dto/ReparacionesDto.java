package pe.edu.uni.proyecto.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReparacionesDto {
	private int idReparacion;
	private int idEmpleado;
	private int idTaller;
	private LocalDate fechaReparacion;
	private int calificacion;
	private double costo;
}
