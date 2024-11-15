package pe.edu.uni.proyecto.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import pe.edu.uni.proyecto.dto.TallerDto;



@Service
public class TallerService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public TallerDto registrarTaller(TallerDto bean) {
		ValidarNumero(bean.getTelefono());
		
		//Registrar taller
		String sql="""
				insert into taller(nombre_taller,direccion,telefono,calificacion) values(?,?,?,?)
				""";

	int calif = 0;
	Object[] datos = {bean.getNombreTaller(), bean.getDireccion(), bean.getTelefono(), calif};
	jdbcTemplate.update(sql,datos);
	
	return bean;
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void ValidarNumero(String nombre) {
		 if (!Pattern.matches("\\d+", nombre)) {
	            throw new IllegalArgumentException("El telefono solo debe contener valores numéricos.");
	        }
	}
}
