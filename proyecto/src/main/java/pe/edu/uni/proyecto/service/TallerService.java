package pe.edu.uni.proyecto.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.TallerDto;

<<<<<<< HEAD

=======
>>>>>>> bd4250111447cc7f287439f8c8f29a8589965b9b
@Service
public class TallerService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public TallerDto registrarTaller(TallerDto bean) {
		validarNumero(bean.getTelefono());
		
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
	private void validarNumero(String telefono) {
        if (!Pattern.matches("^[9][0-9]{8}$", telefono)) {
            throw new IllegalArgumentException("El teléfono debe empezar por 9 y tener 9 dígitos numéricos.");
        }
    }
}
