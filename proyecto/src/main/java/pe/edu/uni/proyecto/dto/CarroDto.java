package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CarroDto {
	
	@JsonProperty("idCarro")
	private int idCarro;
    
	@JsonProperty("idEstado")
	private int idEstado; 
    
	@JsonProperty("placa")
	private String placa;
	
	@JsonProperty("proxMant")
    private String proxMant; 
}
