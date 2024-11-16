package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.ConductorFrecuenteDto;

@Service
public class ConsultaConductorFrecService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ConductorFrecuenteDto frecuenciaIncidenteconductor(ConductorFrecuenteDto bean ) {
		validarConductor(bean.getIdConductor());
		bean.setFechaInicio(convertirFecha(bean.getFechaInicio()));
		bean.setFechaFinal(convertirFecha(bean.getFechaFinal()));
		validarFechas(bean.getFechaInicio(),bean.getFechaFinal());
		String sql = """
				select nombre from Conductor where id_conductor = ?;
				"""; 
		String Nombre = jdbcTemplate.queryForObject(sql, String.class, bean.getIdConductor());
		bean.setNombre(Nombre);
		sql = """
				select apellido from Conductor where id_conductor = ?;
				"""; 
		String Apellido = jdbcTemplate.queryForObject(sql, String.class, bean.getIdConductor());
		bean.setApellido(Apellido);
		sql = """
				SELECT COUNT(*) AS cantidad_incidentes
				FROM incidente i
				JOIN programacion p ON i.id_programacion = p.id_programacion
				WHERE p.id_conductor = ? 
				AND i.fecha_incidente BETWEEN ? AND ?;
				""";
		int cantidad = jdbcTemplate.queryForObject(sql,Integer.class,bean.getIdConductor(), bean.getFechaInicio(), bean.getFechaFinal());
		bean.setCantidad(cantidad);
		return bean;
	}
	
	 private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 
	private void validarFechas(String fechaInicio, String fechaFinal) {
		try {
            // Parsear las fechas desde el String
            LocalDate fechaInicio1 = LocalDate.parse(fechaInicio, DATE_FORMATTER);
            LocalDate fechaFinal1 = LocalDate.parse(fechaFinal, DATE_FORMATTER);

            // Verificar que la fecha de inicio sea anterior a la fecha de fin
            if (fechaInicio1.isAfter(fechaFinal1)) {
            	 throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin.");
            }

        } catch (DateTimeParseException e) {
        	 throw new RuntimeException("Formato de fecha inválido. Asegúrese de usar el formato dd/MM/yyyy");
        } catch (NullPointerException e) {
        	 throw new RuntimeException("Las fechas no pueden ser nulas.");
        }
		
	}
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarConductor(int id_conductor) {
	    String sql = """
	            SELECT COUNT(*) FROM Conductor WHERE id_conductor = ?;
	            """;
	    try {
	        Integer cont = jdbcTemplate.queryForObject(sql, Integer.class, id_conductor);
	        
	        // Verificar si el conteo es nulo o diferente de 1
	        if (cont == null || cont != 1) {
	            throw new IllegalArgumentException("El id_conductor no es correcto o no existe.");
	        }
	    } catch (DataAccessException e) {
	        // Manejo de excepciones relacionadas con la base de datos
	        throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
	    }
	}
	
	public String convertirFecha(String fecha) {
		try {
			// Definir los formatos: de entrada (dd/MM/yyyy) y de salida (yyyy-MM-dd)
		    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		    // Parsear la fecha de entrada y formatearla al nuevo formato
		    LocalDate date = LocalDate.parse(fecha, inputFormatter);
		    return date.format(outputFormatter);
		} catch (DateTimeParseException e) {
			throw new RuntimeException("Formato de fecha inválido. Asegúrese de usar el formato dd/MM/yyyy");
		} catch (NullPointerException e) {
       	 throw new RuntimeException("Las fechas no pueden ser nulas.");
       }
	}
}
