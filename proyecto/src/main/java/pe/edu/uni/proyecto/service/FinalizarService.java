package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.FinalizarDto;

@Service
public class FinalizarService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public FinalizarDto finalizarProgramacion(FinalizarDto bean) {
		validarProgramacion(bean.getIdProgramacion());
		validarFechaReal(bean.getIdProgramacion(), bean.getFechaFinReal());
		actualizarEstado(bean.getIdProgramacion());
		insertarfin(bean.getIdProgramacion(),bean.getFechaFinReal());
		return bean;
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void insertarfin(int idProg, String fecfin) {
		String sql = """
					UPDATE PROGRAMACION
					SET fecha_fin_real = ?
					WHERE id_programacion = ?
						""";
		jdbcTemplate.update(sql,fecfin ,idProg);
	}

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarProgramacion(int idProg) {
		String sql = "select count(1) cont from PROGRAMACION where id_programacion = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idProg);
		if (cont != 1) {
			throw new RuntimeException("La programación " + idProg + " no existe");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private String convertirFecha(String fecha) {
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
	
	@SuppressWarnings("null")
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarFechaReal(int idProg, String fecha) {
		String sql = "select fecha_asignacion from PROGRAMACION where id_programacion = ?";
		String fechaPartida = jdbcTemplate.queryForObject(sql, String.class, idProg);
		//fechaPartida = convertirFecha(fechaPartida);
		//fecha = convertirFecha(fecha);
		sql = "select datediff(day,'" + fecha + "','" + fechaPartida + "')";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class);
		if(cont<0) {
			throw new RuntimeException("La fecha real de llegada no puede ser menor a la de partida");
		}
		//sql = "select fecha_fin_programada from PROGRAMACION where id_programacion = ?";
		//String fechaFin = jdbcTemplate.queryForObject(sql, String.class, idProg);
		//fechaFin = convertirFecha(fechaFin);
		/*sql = "select datediff(day,'" + fecha + "','" + fechaFin + "')";
		cont = jdbcTemplate.queryForObject(sql, Integer.class);
		if(cont<-3 || cont>3) {
			throw new RuntimeException("La fecha real de llegada no puede tener una diferencia mayor"
					+ " a tres días de la fecha programada");
		}*/
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void actualizarEstado(int idProgramacion) {
    	// Actualizar el estado del carro
    	String sqlCarro = "UPDATE carro SET id_estado = 1 WHERE id_carro = (SELECT id_carro FROM programacion WHERE id_programacion = ?)";
    	jdbcTemplate.update(sqlCarro, idProgramacion);
    
    	// Actualizar el estado del conductor
    	String sqlConductor = "UPDATE conductor SET id_estado = 1 WHERE id_conductor = (SELECT id_conductor FROM programacion WHERE id_programacion = ?)";
    	jdbcTemplate.update(sqlConductor, idProgramacion);
	}
}