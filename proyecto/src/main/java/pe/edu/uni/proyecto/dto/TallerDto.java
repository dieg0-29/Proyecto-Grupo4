package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TallerDto {
	private int id_taller;
	private String nom_taller;
	private String direccion;
	private int telef;
}
