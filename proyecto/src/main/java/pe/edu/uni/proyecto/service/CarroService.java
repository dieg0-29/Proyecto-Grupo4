package pe.edu.uni.proyecto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.CarroDto;

@Service 

public class CarroService {
	@Autowired
    JdbcTemplate jdbcTemplate;
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean registrarCarro(CarroDto bean) {
    	try {
		Validarcarro(bean.getPlaca());
		bean.setProx_mant(convertirFecha(bean.getProx_mant()));
		//Registrar carro
		String sql="""
				insert into carro(id_estado, placa, prox_mant) values(?,?,?)
				""";
	int estado = 1;
	Object[] datos = {estado, bean.getPlaca(), bean.getProx_mant()};
	jdbcTemplate.update(sql,datos);	
	return true;
	} catch (Exception err){
		err.printStackTrace();
		return false;
	}
 }


    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void Validarcarro(String placa) {
		String sql ="""
				select Count(*) from Carro where placa = ?
				""";
		int cont = jdbcTemplate.queryForObject(sql,Integer.class,placa);
		if (cont == 1) {
		    throw new RuntimeException("El carro con la placa " + placa + " ya existe.");
		}
	}
  
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
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
