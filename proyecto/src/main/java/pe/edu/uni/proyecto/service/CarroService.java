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

import pe.edu.uni.proyecto.dto.CarroDto;

@Service 

public class CarroService {
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> obtenerCarrosConDescripcionEstado() {
		String sql = """
				SELECT t1.id_carro, t2.descripcion AS estado, t1.placa, CONVERT(VARCHAR, t1.prox_mant, 103) AS prox_mant
                FROM CARRO t1 INNER JOIN EST_CARRO t2 ON t1.id_estado = t2.id_estado
					    """;
		return jdbcTemplate.queryForList(sql);
	}
	
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void registrarCarro(CarroDto bean) {
		Validarcarro(bean.getPlaca());
		bean.setProxMant(bean.getProxMant());
		//Registrar carro
		String sql="""
				insert into carro(id_estado, placa, prox_mant) values(?,?,?)
				""";
	int estado = 1;
	Object[] datos = {estado, bean.getPlaca(), bean.getProxMant()};
	jdbcTemplate.update(sql,datos);
	System.out.print("Todo ok");
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void eliminarCarro(String placa) {
        validarCarroNoExiste(placa);
        String sql1 = """
				 select COUNT(*) from MANTENIMIENTO t1 inner join CARRO t2 on t1.id_carro = t2.id_carro
				where t2.placa = ?
				            """;
		int cont1 = jdbcTemplate.queryForObject(sql1, Integer.class, placa);
		if (cont1 >= 1) {
			throw new RuntimeException("El CARRO NO PUEDE SER ELIMINADO");
		}
		String sql2 = """
				 select COUNT(*) from PROGRAMACION t1 inner join CARRO t2 on t1.id_carro = t2.id_carro
				where t2.placa = ?
				            """;
		int cont2 = jdbcTemplate.queryForObject(sql2, Integer.class, placa);
		if (cont2 >= 1) {
			throw new RuntimeException("El CARRO NO PUEDE SER ELIMINADO");
		}
        String sql = """
            DELETE FROM CARRO WHERE placa = ?
        """;
        jdbcTemplate.update(sql, placa);
        System.out.println("Carro con la placa" + placa + "eliminado correctamente.");
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void editarCarro(CarroDto bean) {
        validarCarroNoExiste(bean.getPlaca());
        validarEstado(bean.getIdEstado());
        bean.setProxMant(convertirFecha(bean.getProxMant()));
        String sql = """
            UPDATE carro SET id_estado = ?, prox_mant = ? WHERE placa = ?
        """;
        Object[] datos = { bean.getIdEstado(), bean.getProxMant(), bean.getPlaca() };
        jdbcTemplate.update(sql, datos);
        System.out.println("Carro con placa " + bean.getPlaca() + " actualizado correctamente.");
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
	private void validarEstado(int estado) {
		String sql = """
				select Count(*) from EST_CARRO where id_estado = ?
				""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, estado);
		if (cont == 0) {
			throw new RuntimeException("El estado no existe.");
		}
		
	}
    
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarCarroNoExiste(String placa) {
	        String sql = """
	            SELECT COUNT(*) FROM Carro WHERE placa = ?
	        """;
	        int cont = jdbcTemplate.queryForObject(sql, Integer.class, placa);
	        if (cont == 0) {
	            throw new RuntimeException("El carro con la placa " + placa + " no existe.");
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
