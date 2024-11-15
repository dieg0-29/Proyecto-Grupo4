package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.ProgramacionDto;

@Service
public class ProgramacionService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ProgramacionDto registrarProg(ProgramacionDto bean) {
		validarEmpleado(bean.getIdEmpleado());
		validarCarro(bean.getIdCarro());
		validarEstadoCarro(bean.getIdCarro());
		validarConductor(bean.getIdConductor());
		validarEstadoConductor(bean.getIdConductor());
		validarRuta(bean.getIdRuta());
		bean.setFechaAsignacion(convertirFecha(bean.getFechaAsignacion()));
		validarFechaPartida(bean.getIdConductor(), bean.getFechaAsignacion());
		bean.setFechaFinProgramada(convertirFecha(bean.getFechaFinProgramada()));
		validarFechaFin(bean.getFechaFinProgramada(), bean.getFechaAsignacion());
		
		//Registrar Programacion
		registrarProgamacion(bean);
		
		return bean;
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
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarEstadoCarro(int idCarro) {
		String sql = "select id_estado estado from CARRO where id_carro = ?";
		int estado = jdbcTemplate.queryForObject(sql, Integer.class, idCarro);
		if(estado != 1) {
			throw new RuntimeException("Carro " + idCarro  + " no disponible");
		}
	}

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarConductor(int idConductor) {
		String sql = "select count(1) cont from CONDUCTOR where id_conductor = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idConductor);
		if(cont != 1) {
			throw new RuntimeException("Conductor " + idConductor  + " no existe");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarEstadoConductor(int idConductor) {
		String sql = "select id_estado estado from CONDUCTOR where id_conductor = ?";
		int estado = jdbcTemplate.queryForObject(sql, Integer.class, idConductor);
		if(estado != 1) {
			throw new RuntimeException("Conductor " + idConductor  + " no disponible");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarRuta(int idRuta) {
		String sql = "select count(1) cont from RUTA where id_ruta = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idRuta);
		if(cont != 1) {
			throw new RuntimeException("Ruta " + idRuta  + " no existe");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public String convertirFecha(String fecha) {
	    // Definir los formatos: de entrada (dd/MM/yyyy) y de salida (yyyy-MM-dd)
	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    // Parsear la fecha de entrada y formatearla al nuevo formato
	    LocalDate date = LocalDate.parse(fecha, inputFormatter);
	    return date.format(outputFormatter);
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarFechaPartida(int idConductor, String fechaPartida) {
		String sql = "select top 1 cast(fecha_fin_programada as date) ult_fecha from PROGRAMACION ";
		sql += "where id_conductor = ? order by fecha_fin_programada desc";
		String fecha = jdbcTemplate.queryForObject(sql, String.class, idConductor);
		sql = "select DATEDIFF(DAY,'" + fecha + "','" + fechaPartida + "')";
		int dif = jdbcTemplate.queryForObject(sql, Integer.class);
		if(dif<0) {
			throw new RuntimeException("Fecha de partida " + fechaPartida  + " no valida");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarFechaFin(String fechaFin, String fechaPartida) {
		fechaPartida = "'" + fechaPartida + "'";
		fechaFin = "'" + fechaFin + "'";
		String sql = "select DATEDIFF(DAY," + fechaPartida + "," + fechaFin + ")";
		int dif = jdbcTemplate.queryForObject(sql, Integer.class);
		if(dif<=0) {
			throw new RuntimeException("Fecha final " + fechaFin  + " no valida");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void registrarProgamacion(ProgramacionDto bean) {
		String sql = "insert into PROGRAMACION values(?,?,?,?,?,?,NULL)";
		jdbcTemplate.update(sql,bean.getIdCarro(),bean.getIdEmpleado(),bean.getIdConductor(),
				bean.getIdRuta(),bean.getFechaAsignacion(),bean.getFechaFinProgramada());
	}
	
}
