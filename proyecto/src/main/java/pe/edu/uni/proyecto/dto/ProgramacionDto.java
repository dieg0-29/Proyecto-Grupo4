package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProgramacionDto {
	private int id_program;
	private int id_carro;
	private int id_empleado;
	private int id_conductor;
	private int id_ruta;
	private String f_asignac;
	private String f_fin_program;
	private String f_fin_real;
}
