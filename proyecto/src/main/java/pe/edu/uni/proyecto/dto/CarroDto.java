package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CarroDto {
	
	private int idCarro;
    private int id_estado; 
    private String placa;
    private String prox_mant; 
}
