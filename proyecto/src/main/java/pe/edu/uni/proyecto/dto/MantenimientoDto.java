package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MantenimientoDto {
	
	private int id_mant;
	private int id_mantEstado;
	private String f_mantenimiento;
	private int id_emp;
	private double costo;
	
}
