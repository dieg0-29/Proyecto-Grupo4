package pe.edu.uni.proyecto.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MantenimientoDto {
	private int idMantenimiento;
	private int idEmpleado;
	private int idTaller;
	private int estadoMantenimiento;
	private int idCarro;
	private int calificacion;
	private LocalDate fechaInicio;
	private LocalDate fechaSalidaProgramada;
	private LocalDate fechaSalidaReal;
	private double costo;
	private String detalle;
}
