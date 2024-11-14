package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RutaDto {
	private int id_ruta;
	private String nombre_ruta;
	private String origen;
	private String destino;
	private double dist_km;
}
