package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
public class CarroDto {
	private int idCarro;
	private int idEstado;
	private String placa;
	private LocalDate proxMantenimiento;
}
