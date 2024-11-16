package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MantenimientoDto {
	
	private int id_empleado;
	private int id_taller;
	private int id_est_mant;
	private int id_carro;
	private double calificacion;
	private String fecha_inicio;
	private String fecha_salida_programada;
	private double costo;
	private String detalle;
}
