package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.IncidenteDto;

@Service
public class IncidenteService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> obtenerListaIncidente() {
		String sql = "SELECT id_incidente Id_incidente,id_empleado Empleado, "
				+ "id_programacion Programacion,id_tipo Tipo,fecha_incidente Fecha, "
				+ "detalle Detalle "
				+ "FROM INCIDENTE "
				+ "ORDER BY 1";
		return jdbcTemplate.queryForList(sql);
	}

	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void reportarincidente(IncidenteDto bean) {
		// Validaciones
		validarEmpleado(bean.getEmpleado());
		validarProgramacion(bean.getProgramacion());
		validarTipo(bean.getTipoIncidente());
		LocalDate fecha = convertirFecha(bean.getFecha());
		//convertirFecha(bean.getFecha());
		//validarFecha(bean.getProgramacion(),bean.getFecha());
		
		
		// Proceso
		registrarIncidente(bean.getEmpleado(), bean.getProgramacion(),
<<<<<<< HEAD
				bean.getTipoIncidente(), fecha, bean.getDetalle());
		actualizarEstadoCarro(bean.getProgramacion());
=======
				bean.getTipoIncidente(), bean.getFecha(), bean.getDetalle());
		
>>>>>>> c084c0402e70d1b97fb5e087bbcc34fe1818f615
		// Reporte final
		System.out.println("Proceso ok.");		

	}
	// Validaciones para registrar el incidente
		// validar el empleado
		@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
		private void validarEmpleado(int empleado) {
			String sql = "SELECT COUNT(1) cont FROM EMPLEADO where id_empleado = ?";
			int cont = jdbcTemplate.queryForObject(sql, Integer.class, empleado);
			if (cont == 0) {
				throw new RuntimeException("El empleado no existe.");
			}
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
		private void validarTipo(int id) {
			String sql = "SELECT COUNT(1) cont FROM Tipo_incidente where id_tipo = ?";
			int cont = jdbcTemplate.queryForObject(sql, Integer.class, id);
			if (cont != 1) {
				throw new RuntimeException("El tipo ingresado no existe.");
			}
		}
		// validar que la programación exista
		@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
		private void validarProgramacion(int programacion) {
			String sql = "SELECT COUNT(1) cont FROM PROGRAMACION where id_programacion = ?";
			int cont = jdbcTemplate.queryForObject(sql, Integer.class, programacion);
			if (cont != 1) {
				throw new RuntimeException("La programacion no existe.");
			}
		}
		// registrar el incidente
		@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
		private void registrarIncidente(int empleado, int programacion, int tipo_incidente, LocalDate fecha, String detalle) {
			String sql = "INSERT INTO INCIDENTE(id_empleado, "
					+ "id_programacion,id_tipo,fecha_incidente, "
					+ "detalle) VALUES(?,?,?,?,?)";
			String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			jdbcTemplate.update(sql, empleado, programacion, tipo_incidente, fechaFormateada, detalle);
		}

		

		
}
