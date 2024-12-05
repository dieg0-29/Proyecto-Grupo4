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
		String sql = """
				SELECT 
    			C.id_conductor,
    			C.nombre,
    			C.apellido,
    			C.dni,
    			C.correo,
    			C.telefono,
    			E.descripcion AS estado_conductor
				FROM 
    			CONDUCTOR C
				JOIN 
    			EST_CONDUCTOR E ON C.id_estado = E.id_estado;
				""";
		return jdbcTemplate.queryForList(sql);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void registrarConductor(ConductorDto bean) {
		validarDniCorrecto(bean.getDni());
		validarDni(bean.getDni());
		validarCorreo(bean.getCorreo());
		validarTelefono(bean.getTelefono());
		String sql = """
			insert into CONDUCTOR(id_estado,nombre,apellido,dni,correo,telefono)
			values(?,?,?,?,?,?)
		""";
		int a = 1;
		jdbcTemplate.update(sql,a,bean.getNombre(), bean.getApellido(), bean.getDni(), bean.getCorreo(), bean.getTelefono());	 
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void eliminarConductor(String dni) {
		validarDniCorrecto(dni);
        validarDniNoExiste(dni);
        
        
		String sql1 = """
				 select COUNT(*) from PROGRAMACION t1 inner join CONDUCTOR t2 on t1.id_conductor = t2.id_conductor
				where t2.dni = ?
				            """;
		int cont1 = jdbcTemplate.queryForObject(sql1, Integer.class, dni);
		if (cont1 >= 1) {
			throw new RuntimeException("El CONDUCTOR NO PUEDE SER ELIMINADO");
		}
        String sql = """
            DELETE FROM CONDUCTOR WHERE dni = ?
        """;
        jdbcTemplate.update(sql, dni);
        System.out.println("Conductor con " + dni + " eliminado correctamente.");
    }
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void editarConductor(ConductorDto bean) {
		validarDniCorrecto(bean.getDni());
        validarDniNoExiste(bean.getDni());
        validarCorreo(bean.getCorreo());
        validarTelefono(bean.getTelefono());
		validarestado(bean.getIdEstado());
        String sql = """
            UPDATE CONDUCTOR SET id_estado=?, nombre=?, apellido=? , correo = ?, telefono = ? WHERE dni = ?
        """;
        Object[] datos = { bean.getIdEstado(),bean.getNombre(),bean.getApellido(),bean.getCorreo(), bean.getTelefono() , bean.getDni()};
        jdbcTemplate.update(sql, datos);
        System.out.println("Los datos del conductor se han actualizado correctamente.");
    }
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarDniNoExiste(String dni) {
	        String sql = """
	            SELECT COUNT(*) FROM CONDUCTOR WHERE dni = ?
	        """;
	        int cont = jdbcTemplate.queryForObject(sql, Integer.class, dni);
	        if (cont == 0) {
	            throw new RuntimeException("El conductor con DNI " + dni + " no existe.");
	        }
	    }

	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarestado(int est) {
        String sql = """
	            SELECT COUNT(*) FROM est_conductor WHERE id_estado = ?
	        """;
	        int cont = jdbcTemplate.queryForObject(sql, Integer.class, est);
	        if (cont == 0) {
	            throw new RuntimeException("El estado " + est + " no existe.");
	        }
    }

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarTelefono(String telefono) {
        if (!Pattern.matches("^[9][0-9]{8}$", telefono)) {
            throw new IllegalArgumentException("El teléfono debe empezar por 9 y tener 9 dígitos numéricos.");
        }
    }

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void validarDniCorrecto(String dni) {
        if (!Pattern.matches("^[0-9]{8}$", dni)) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos numéricos.");
        }
    }
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarCorreo(String correo) {
	    if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", correo)) {
	        throw new IllegalArgumentException("El correo debe ser una dirección válida.");
	    }
	}
	
	
	private void validarDni(String dni) {
        String sql = """
        		select count(*) from Conductor where dni = ?
        		""";
        int cont = jdbcTemplate.queryForObject(sql, Integer.class, dni);
        if (cont >0) {
        	throw new RuntimeException("el conductor ya existe.");
        }
    }
	
}
