package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.MantenimientoDto;

@Service
public class MantenimientoService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public List<Map<String, Object>> obtenerListaMantenimiento() {
    	String sql = "SELECT id_mantenimiento id, id_empleado empleado, "
    			+ "id_taller taller, id_est_mant estado, id_carro carro, "
    			+ "calificacion, fecha_inicio inicio, fecha_salida_programada salida, "
    			+ "fecha_salida_real fin, costo, detalle "
    			+ "FROM MANTENIMIENTO "
    			+ "ORDER BY 1";
    	return jdbcTemplate.queryForList(sql);
    }

    
    public void registroMantenimiento(MantenimientoDto bean) {
    
        	validarEmpleado( bean.getIdEmpleado());
        	validarCarro( bean.getIdCarro());
        	validarTaller(bean.getIdTaller());
        	bean.setId_est_mant(1);
        	validarFechas(bean.getFechaInicio(),bean.getFechaSalidaProgramada());
        	//actualizar estado carro
        	carroMantenimiento(bean.getIdCarro());
        	validarCalificacion(bean.getCalificacion());
            String sql = "INSERT INTO MANTENIMIENTO (id_empleado, id_taller, id_est_mant, id_carro, calificacion, fecha_inicio, fecha_salida_programada, fecha_salida_real, costo, detalle) VALUES (?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)";
           
            jdbcTemplate.update(sql, bean.getIdEmpleado(), bean.getIdTaller(),1, 
            		bean.getIdCarro(), bean.getCalificacion(), bean.getFechaInicio(), 
            		bean.getFechaSalidaProgramada(), bean.getCosto(), bean.getDetalle());
           
            double calificacionfinal = obtenerCalificacionTaller(bean.getIdTaller());
    		actualizarpromediotaller(bean.getIdTaller(),calificacionfinal);
            
    		System.out.println("registro ok");
    }
    
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void actualizarpromediotaller(int id_taller, double calificacionfinal) {
    	String sql = """
				UPDATE TALLER
				SET calificacion = ?
				WHERE id_taller = ? 
				""";
		jdbcTemplate.update(sql, calificacionfinal, id_taller);		
	}

	private double obtenerCalificacionTaller(int id_taller) {
		String sql = """
				SELECT CAST(AVG(totales.calificacion) AS DECIMAL(10, 1)) AS promedio
				FROM (
				    SELECT t1.calificacion FROM REPARACION t1 WHERE t1.id_taller = ? UNION ALL
				    SELECT t2.calificacion FROM MANTENIMIENTO t2 WHERE t2.id_taller = ?
				) AS totales;
				""";
		double calificacionfinal= jdbcTemplate.queryForObject(sql, double.class, id_taller , id_taller );
		return calificacionfinal;
	}

	private void carroMantenimiento(int id_carro) {
		String sql = """
				UPDATE Carro
				SET  id_estado= 2
				WHERE id_carro = ?;
				""";
		jdbcTemplate.update(sql,id_carro);		
	}

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void validarCalificacion(double calificacion) {
        if (calificacion < 0 || calificacion > 5) {
        	throw new RuntimeException("La calificación debe estar entre 0 y 5.");
        }
    }
    
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarTaller(int id_taller) {
		String sql = "select count(1) cont from Taller where id_taller = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, id_taller);
		if(cont != 1) {
			throw new RuntimeException("Empleado " + id_taller  + " no existe");
		}
		
	}
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarEmpleado(int idEmpleado) {
		String sql = "select count(1) cont from EMPLEADO where id_empleado = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idEmpleado);
		if(cont != 1) {
			throw new RuntimeException("Empleado " + idEmpleado  + " no existe");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarCarro(int idCarro) {
		String sql = "select count(1) cont from CARRO where id_carro = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idCarro);
		if(cont != 1) {
			throw new IllegalArgumentException("Carro " + idCarro  + " no existe");
		}
	}
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private void validarFechas(String fechaInicio, String fechaFinal) {
            // Parsear las fechas desde el String
            LocalDate fechaInicio1 = LocalDate.parse(fechaInicio, DATE_FORMATTER);
            LocalDate fechaFinal1 = LocalDate.parse(fechaFinal, DATE_FORMATTER);

            // Verificar que la fecha de inicio sea anterior a la fecha de fin
            if (fechaInicio1.isAfter(fechaFinal1)) {
            	throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
            }
	}
	
	
}
