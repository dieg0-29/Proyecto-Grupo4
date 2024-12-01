package pe.edu.uni.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TallerDto {

    @JsonProperty("Id_taller")
    private int idTaller;

    @JsonProperty("Nombre")
    private String nombre;

    @JsonProperty("Direccion")
    private String direccion;

    @JsonProperty("Telefono")
    private String telefono;

    @JsonProperty("Calificacion")
    private double calificacion;
}
