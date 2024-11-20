package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FinalizarDto {
	private int idProgramacion;
	private int idCarro;
	private int idConductor;
	private String fechaFinReal;
}