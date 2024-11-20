package pe.edu.uni.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.IncidenteFrecuenteDto;

@Service
public class ConsultaIncidenteFrec {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public IncidenteFrecuenteDto frecuenciaIncidente(int id_tipo) {
		validarTipo(id_tipo);
		IncidenteFrecuenteDto bean = new IncidenteFrecuenteDto();
		String sql = """
				select count(*) cont From Incidente where id_tipo = ?;
				""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, id_tipo);
		bean.setCant(cont);
		sql = """
				select descripcion From Tipo_Incidente where id_tipo = ?;
				""";
		String descripcion = jdbcTemplate.queryForObject(sql, String.class, id_tipo);
		bean.setDescripcion(descripcion);
		bean.setId_tipo(id_tipo);
		return bean;
	}
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarTipo(int id_tipo) {
	    String sql = """
	            SELECT COUNT(*) FROM Tipo_incidente WHERE id_tipo = ?;
	            """;
	    try {
	        Integer cont = jdbcTemplate.queryForObject(sql, Integer.class, id_tipo);
	        
	        // Verificar si el conteo es nulo o diferente de 1
	        if (cont == null || cont != 1) {
	            throw new IllegalArgumentException("El id_tipo no es correcto o no existe.");
	        }
	    } catch (DataAccessException e) {
	        // Manejo de excepciones relacionadas con la base de datos
	        throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
	    }
	}
}
