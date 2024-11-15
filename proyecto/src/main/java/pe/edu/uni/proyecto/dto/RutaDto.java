package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RutaDto {
	private int idRuta;
	private String nombreRuta;
	private String origen;
	private String destino;
	private double distanciaKm;
}