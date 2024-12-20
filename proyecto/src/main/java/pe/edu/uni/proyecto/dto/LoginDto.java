package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LoginDto {

	@JsonProperty("idEmpleado")
	private int idEmpleado;
	
	@JsonProperty("usuario")
	private String usuario;
	
	@JsonProperty("clave")
	private String clave;
}
