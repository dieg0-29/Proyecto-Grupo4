package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class EmpleadoDto {
	private int idEmpleado;
	private String nombre;
	private String apellido;
	private int dni; 
	private String correo;
	private int telefono; 
	private double salario;
}
