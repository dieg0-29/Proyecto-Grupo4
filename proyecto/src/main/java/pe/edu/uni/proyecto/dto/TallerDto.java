package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TallerDto {
	
	@JsonProperty("idTaller")
	private int idtaller;

	@JsonProperty("nombreTaller")
    private String nombreTaller;
	
	@JsonProperty("direccion")
    private String direccion;
	
	@JsonProperty("telefono")
    private String telefono;

	@JsonProperty("calificacion")
    private double calificacion;
}
