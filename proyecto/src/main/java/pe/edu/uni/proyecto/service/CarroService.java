package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.CarroDto;

public class CarroService {
	@Autowired
    JdbcTemplate jdbcTemplate;
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public CarroDto registrarCarro(CarroDto bean) {
		Validarcarro(bean.getPlaca());
		validarFecha(bean.getProx_mant());	
		//Registrar carro
		String sql="""
				insert into carro(id_estado, placa, prox_mant) values(?,?,?)
				""";
	int estado = 1;
	Object[] datos = {estado, bean.getPlaca(), bean.getProx_mant()};
	jdbcTemplate.update(sql,datos);	
	return bean;
	}

	private void Validarcarro(String placa) {
		String sql ="""
				select Count(*) from Carro where placa = ?
				""";
		int cont = jdbcTemplate.queryForObject(sql,Integer.class,placa);
		if (cont != 1) {
			throw new RuntimeException("El carro con la placa" + placa  + "existe.");
		}
	}
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 public void validarFecha(String fecha) {
	        if (fecha == null) {
	            throw new IllegalArgumentException("Las fechas no pueden ser nulas.");
	        }

	        // Intenta parsear la fecha desde el String
	        try {
	            LocalDate.parse(fecha, DATE_FORMATTER);
	        } catch (DateTimeParseException e) {
	            throw new IllegalArgumentException("Formato de fecha inválido. Asegúrese de usar el formato yyyy-MM-dd.");
	        }
	    }
}
