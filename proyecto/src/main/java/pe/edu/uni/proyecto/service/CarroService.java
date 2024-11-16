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

<<<<<<< HEAD
@Service
=======
@Service 
>>>>>>> bd4250111447cc7f287439f8c8f29a8589965b9b
public class CarroService {
	@Autowired
    JdbcTemplate jdbcTemplate;
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public CarroDto registrarCarro(CarroDto bean) {
		Validarcarro(bean.getPlaca());
		bean.setProx_mant(convertirFecha(bean.getProx_mant()));
		//Registrar carro
		String sql="""
				insert into carro(id_estado, placa, prox_mant) values(?,?,?)
				""";
	int estado = 1;
	Object[] datos = {estado, bean.getPlaca(), bean.getProx_mant()};
	jdbcTemplate.update(sql,datos);	
	return bean;
	}

<<<<<<< HEAD
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
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
=======
    private void Validarcarro(String placa) {
        String sql = """
                select Count(*) from Carro where placa = ?
                """;
        int cont = jdbcTemplate.queryForObject(sql, Integer.class, placa);
        if (cont > 0) { // Cambia esta condición
            throw new RuntimeException("El carro con la placa " + placa + " ya existe.");
        }
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void validarFecha(String fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas.");
        }

        try {
            LocalDate.parse(fecha, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Asegúrese de usar el formato yyyy-MM-dd.");
        }
    }
>>>>>>> bd4250111447cc7f287439f8c8f29a8589965b9b
}
