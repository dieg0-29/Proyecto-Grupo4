package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.FinMantDto;

@Service
public class FinMantService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public FinMantDto finalizarMantenimiento(FinMantDto bean) {
		validarMant(bean.getIdMant());
		LocalDate fechaFinReal = convertirFecha(bean.getFechaFinReal());
		validarFechaReal(bean.getIdMant(), fechaFinReal);
		actualizarEstado(bean.getIdMant(), fechaFinReal);
		insertarfin(bean.getIdMant(),fechaFinReal);
		return bean;
	}
	
	private LocalDate convertirFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato de fecha inválido. Debe ser dd/MM/yyyy");
        } catch (NullPointerException e) {
			throw new RuntimeException("Las fechas no pueden ser nulas.");
		}
    }
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void insertarfin(int idMant, LocalDate fecfin) {
		String sql = """
					UPDATE MANTENIMIENTO
					SET fecha_salida_real = ?
					WHERE id_mantenimiento = ?
						""";
		String fechaFormateada = fecfin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		jdbcTemplate.update(sql,fechaFormateada ,idMant);
	}

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarMant(int idMant) {
		String sql = "select count(1) cont from MANTENIMIENTO where id_mantenimiento = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idMant);
		if (cont != 1) {
			throw new RuntimeException("El mantenimiento " + idMant + " no existe");
		}
	}
	
	@SuppressWarnings("null")
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarFechaReal(int idProg, LocalDate fecha) {
		String sql = "select fecha_inicio from MANTENIMIENTO where id_mantenimiento = ?";
		String fechaInicio = jdbcTemplate.queryForObject(sql, String.class, idProg);
		String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		sql = "select datediff(day,'" + fechaInicio + "','" + fechaFormateada + "')";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class);
		if(cont<0) {
			throw new RuntimeException("La fecha real de finalizacion no puede ser menor a la de partida");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void actualizarEstado(int idMant, LocalDate fecha) {
    	// Actualizar el estado del carro
    	String sql = "UPDATE carro SET id_estado = 1 WHERE id_carro = (SELECT id_carro FROM MANTENIMIENTO WHERE id_mantenimiento = ?)";
    	jdbcTemplate.update(sql, idMant);
    	sql = "select id_carro from mantenimiento where id_mantenimiento = ?";
    	int carro = jdbcTemplate.queryForObject(sql, Integer.class, idMant);
    	String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	sql = "update CARRO set prox_mant = (SELECT DATEADD(MONTH, 6, '" + fechaFormateada + "')) where id_carro = ?";
    	jdbcTemplate.update(sql,carro);
	}
}
