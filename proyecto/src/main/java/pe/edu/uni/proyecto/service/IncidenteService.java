package pe.edu.uni.proyecto.service;

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
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void reportarincidente(IncidenteDto bean) {
		// Validaciones
		validarEmpleado(bean.getEmpleado());
		validarProgramacion(bean.getProgramacion());
		//convertirFecha(bean.getFecha());
		//validarFecha(bean.getProgramacion(),bean.getFecha());
		
		
		// Proceso
		registrarIncidente(bean.getEmpleado(), bean.getProgramacion(),
				bean.getTipoIncidente(), bean.getFecha(), bean.getDetalle());
		actualizarEstadoCarro(bean.getProgramacion());
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
		private void registrarIncidente(int empleado, int programacion, int tipo_incidente, String fecha, String detalle) {
			String sql = "INSERT INTO INCIDENTE(id_empleado, "
					+ "id_programacion,id_tipo,fecha_incidente, "
					+ "detalle) VALUES(?,?,?,?,?)";
			jdbcTemplate.update(sql, empleado, programacion, tipo_incidente, fecha, detalle);
		}

		

		@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
		private void actualizarEstadoCarro(int programacion) {
			String sql = "update carro set id_estado = 3 where id_carro "
					+ "= (select id_carro from programacion where id_programacion = ?)";
			jdbcTemplate.update(sql, programacion);
		}
}
