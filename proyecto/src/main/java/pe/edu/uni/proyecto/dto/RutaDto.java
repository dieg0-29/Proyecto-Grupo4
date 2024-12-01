package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaDto {

    @JsonProperty("Id_ruta")
    private int idRuta;

    @JsonProperty("Nombre")
    private String nombre;

    @JsonProperty("Origen")
    private String origen;

    @JsonProperty("Destino")
    private String destino;

    @JsonProperty("Distancia")
    private double distancia;
}
