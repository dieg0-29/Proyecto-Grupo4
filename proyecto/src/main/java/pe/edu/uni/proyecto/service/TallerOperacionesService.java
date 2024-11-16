package pe.edu.uni.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.proyecto.dto.TallerreparacionDto;


@Service
public class TallerOperacionesService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// Método que obtiene el taller con más operaciones y devuelve un DTO
    public TallerreparacionDto obtenerTallerConMasOperaciones() {
        String sql = """
                     SELECT T.nombre_taller, 
                     (COUNT(M.id_mantenimiento) + COUNT(R.id_reparacion)) 
                     AS total_operaciones 
                     FROM TALLER T 
                     LEFT JOIN MANTENIMIENTO M ON T.id_taller = M.id_taller 
                     LEFT JOIN REPARACION R ON T.id_taller = R.id_taller 
                     GROUP BY T.id_taller, T.nombre_taller 
                     ORDER BY total_operaciones DESC 
                     OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY;
                     """;

        try {
            // Ejecutar la consulta y mapear el resultado a la clase TallerreparacionDto
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
                new TallerreparacionDto(
                    rs.getString("nombre_taller"),
                    rs.getInt("total_operaciones")
                ));
        } catch (DataAccessException e) {
            // Manejo de excepciones: puedes registrar el error o lanzar una excepción personalizada
            System.err.println("Error al acceder a la base de datos: " + e.getMessage());
            // Aquí podrías lanzar una excepción personalizada si lo deseas
            throw new RuntimeException("Error al obtener el taller con más operaciones", e);
        }
    }
}