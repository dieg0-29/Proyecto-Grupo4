package pe.edu.uni.proyecto.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.LoginDto;

@Service
public class LoginService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Map<String,Object> login(LoginDto bean) {
	    validarUsuario(bean.getUsuario());
	    validarClave(bean.getUsuario(), bean.getClave());
	    String sql = "select id_empleado, nombre, apellido from empleado where usuario = ? and clave = ?";
	    Map<String,Object> rec;
	    try {
	        rec = jdbcTemplate.queryForMap(sql, bean.getUsuario(), bean.getClave());
	    } catch (Exception e) {
	        throw new RuntimeException("La clave ingresada no es correcta"); // Lanzar excepción si no se encuentra el usuario
	    }
	    return rec;
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarUsuario(String usuario) {
		String sql = "select count(1) cont from EMPLEADO where usuario = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, usuario);
		if(cont != 1) {
			throw new RuntimeException("El usuario " + usuario +" no existe");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarClave(String usuario, String clave) {
		String sql = "select count(1) cont from EMPLEADO where usuario = ? and clave = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, usuario, clave);
		if(cont != 1) {
			throw new RuntimeException("La clave ingresada no es correcta");
		}
	}
}
