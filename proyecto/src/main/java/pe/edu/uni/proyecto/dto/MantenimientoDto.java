package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MantenimientoDto {
	
	@JsonProperty("idEmpleado")
	private int idEmpleado;

    @JsonProperty("idCarro")
    private int idCarro;

    @JsonProperty("idTaller")
    private int idTaller;

    @JsonProperty("calificacion")
    private double calificacion;

    @JsonProperty("fechaInicio")
    private String fechaInicio;

    @JsonProperty("fechaSalidaProgramada")
    private String fechaSalidaProgramada;

    @JsonProperty("costo")
    private double costo;

    @JsonProperty("detalle")
    private String detalle;
	
	private int id_est_mant;	
}
