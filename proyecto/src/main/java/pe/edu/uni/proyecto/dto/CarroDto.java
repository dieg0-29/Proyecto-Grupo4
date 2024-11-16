package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
public class CarroDto {
	
	private int idCarro;
    private int id_estado; 
    private String placa;
    private String prox_mant; 
}
