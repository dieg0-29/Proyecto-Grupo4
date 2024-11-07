package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReparacionesDto {
	private int id_rep;
	private int id_incidente;
	private double calificacion;
	private String fecha_entrada;
	private String fecha_salida;
	private int id_emp;
}
