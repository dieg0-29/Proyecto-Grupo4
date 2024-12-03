package pe.edu.uni.proyecto.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.ConductorDto;

@Service
public class ConductorService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> obtenerListaConductores() {
		String sql = "SELECT id_conductor Id_conductor,id_estado Estado, "
				+ "nombre Nombre,apellido Apellido,dni DNI, "
				+ "correo Correo,telefono Telefono "
				+ "FROM CONDUCTOR "
				+ "ORDER BY 1";
		return jdbcTemplate.queryForList(sql);
	}

	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarTelefono(String telefono) {
        if (!Pattern.matches("^[9][0-9]{8}$", telefono)) {
            throw new IllegalArgumentException("El teléfono debe empezar por 9 y tener 9 dígitos numéricos.");
        }
    }

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void validarDni(String dni) {
        if (!Pattern.matches("^[0-9]{8}$", dni)) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos numéricos.");
        }
        String sql = """
        		select count(*) from Conductor where dni = ?
        		""";
        int cont = jdbcTemplate.queryForObject(sql, Integer.class, dni);
        if (cont >0) {
        	throw new RuntimeException("el conductor ya existe.");
        }
    }
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean registrarConductor(ConductorDto bean) {
		validarDni(bean.getDni());
		validarTelefono(bean.getTelefono());
		try {
		String sql = """
			insert into CONDUCTOR
			values(1,?,?,?,?,?)				
		""";
		
		jdbcTemplate.update(sql, bean.getNombre(), bean.getApellido(), bean.getDni(), bean.getCorreo(), bean.getTelefono());
		return true;
		} catch (Exception err){
			err.printStackTrace();
			return false;
		}
	}
}
