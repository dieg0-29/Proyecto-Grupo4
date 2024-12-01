package pe.edu.uni.proyecto.service;

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
	public String login(LoginDto bean) {
		//validarEmpleado(bean.getIdEmpleado());
		validarUsuario(bean.getUsuario());
		validarClave(bean.getUsuario(), bean.getClave());
		return("Inicio de sesión exitoso");
		
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
	private void validarUsuario(String usuario) {
		String sql = "select count(1) cont from USUARIO where usuario = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, usuario);
		if(cont != 1) {
			throw new RuntimeException("El usuario " + usuario +" no existe");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarClave(String usuario, String clave) {
		String sql = "select count(1) cont from USUARIO where usuario = ? and clave = ?";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, usuario, clave);
		if(cont != 1) {
			throw new RuntimeException("La clave ingresada no es correcta");
		}
	}
}
