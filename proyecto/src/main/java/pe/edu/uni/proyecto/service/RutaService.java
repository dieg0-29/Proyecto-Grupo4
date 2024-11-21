package pe.edu.uni.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.proyecto.dto.RutaDto;

@Service
public class RutaService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean registrarRuta(RutaDto bean) {
		try {
		//validaciones
		validarExistenciaRuta(bean.getOrigen(),bean.getDestino());
		validarNombreRuta(bean.getNombre());
		
		//Comparacion de las rutas
		if(bean.getOrigen().equals(bean.getDestino())) {
			throw new RuntimeException("Las 2 rutas no pueden ser iguales.");
		}
		
		//distancia
		if (bean.getDistancia() <= 0) {
		    throw new RuntimeException("La distancia debe ser un valor positivo.");
		}
		
		registrarRuta(bean.getNombre(),bean.getOrigen(),bean.getDestino(),bean.getDistancia());

		
		// Reporte final
		System.out.println("Proceso ok.");
		return true;
	} catch (Exception err){
		err.printStackTrace();
		return false;
	}
}
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void registrarRuta(String nombre, String origen, String destino, double distancia) {
		String sql = """
				INSERT INTO RUTA (nombre_ruta, origen, destino, distancia_km)
                VALUES (?, ?, ?, ?)			
			""";
			jdbcTemplate.update(sql,nombre,origen,destino,distancia);	
	}
	
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarNombreRuta(String nombre) {
		String sql = """
				select COUNT(1) from RUTA where nombre_ruta = ?				
			""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, nombre);
		if(cont==1) {
			throw new RuntimeException("La nombre de la ruta ya existe.");
		}
		
		
	}
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	private void validarExistenciaRuta(String origen, String destino) {
		String sql = """
				select COUNT(1) from RUTA where origen = ? and destino = ?				
			""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, origen, destino);
		if(cont==1) {
			throw new RuntimeException("La ruta ya existe.");
		}
		
	}

}