package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    public boolean registroMantenimiento(MantenimientoDto bean) {
        try {
        	validarEmpleado( bean.getId_empleado());
        	validarCarro( bean.getId_carro());
        	validarTaller(bean.getId_taller());
        	bean.setId_est_mant(1);
        	validarFechas(bean.getFecha_inicio(),bean.getFecha_salida_programada());
        	//actualizar estado carro
        	carroMantenimiento(bean.getId_carro());
            String sql = "INSERT INTO MANTENIMIENTO (id_empleado, id_taller, id_est_mant, id_carro, calificacion, fecha_inicio, fecha_salida_programada, fecha_salida_real, costo, detalle) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
           
            jdbcTemplate.update(sql, bean.getId_empleado(), bean.getId_taller(),1, bean.getId_carro(), bean.getCalificacion(), bean.getFecha_inicio(), bean.getFecha_salida_programada(), "", bean.getCosto(), bean.getDetalle());
            return true;
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        }
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
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 5.");
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
			throw new RuntimeException("Carro " + idCarro  + " no existe");
		}
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
        	 throw new RuntimeException("Formato de fecha inválido. Asegúrese de usar el formato yyyy-MM-dd.");
        } catch (NullPointerException e) {
        	 throw new RuntimeException("Las fechas no pueden ser nulas.");
        }
	}
}
