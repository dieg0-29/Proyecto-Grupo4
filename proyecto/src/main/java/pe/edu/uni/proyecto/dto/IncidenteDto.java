package pe.edu.uni.proyecto.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IncidenteDto {
	private int idIncidente;
	private int idProgramacion;
	private int idTipoIncidente;
	private LocalDate fechaIncidente;
}