package pe.edu.uni.proyecto.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProgramacionDto {
	private int idProgramacion;
	private int idCarro;
	private int idEmpleado;
	private int idConductor;
	private int idRuta;
	private LocalDate fechaAsignacion;
	private LocalDate fechaFinProgramada;
	private LocalDate fechaFinReal;
}