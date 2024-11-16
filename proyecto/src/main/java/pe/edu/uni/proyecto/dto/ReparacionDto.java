package pe.edu.uni.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReparacionDto {
	private int idempleado;
	private int idincidente;
	private int idtaller;
	private String fechareparacion;
	private double calificacion;
	private double costo;
	private String detalle;

}
