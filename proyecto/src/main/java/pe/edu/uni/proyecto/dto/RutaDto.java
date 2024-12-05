package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaDto {

    @JsonProperty("idRuta")
    private int idRuta;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("origen")
    private String origen;

    @JsonProperty("destino")
    private String destino;

    @JsonProperty("distancia")
    private long distancia;
}
