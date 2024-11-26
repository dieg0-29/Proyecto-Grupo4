package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MantenimientoDto {

	@JsonProperty("id_empleado")
	private int id_empleado;

    @JsonProperty("id_carro")
    private int id_carro;

    @JsonProperty("id_taller")
    private int id_taller;

    @JsonProperty("calificacion")
    private double calificacion;

    @JsonProperty("fecha_inicio")
    private String fecha_inicio;

    @JsonProperty("fecha_salida_programada")
    private String fecha_salida_programada;

    @JsonProperty("costo")
    private double costo;

    @JsonProperty("detalle")
    private String detalle;
	
	private int id_est_mant;	
}
