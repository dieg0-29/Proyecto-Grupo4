package pe.edu.uni.proyecto.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import pe.edu.uni.proyecto.dto.IncidenteRutaDto;

@Service
public class IncidenteRutaService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<IncidenteRutaDto> obtenerRutasConMasIncidentes() {
		String sql = "WITH RutaIncidentes AS ( "
				+ "    SELECT "
				+ "        RUTA.nombre_ruta, "
				+ "        COUNT(INCIDENTE.id_incidente) AS total_incidentes "
				+ "    FROM "
				+ "        INCIDENTE "
				+ "    INNER JOIN "
				+ "        PROGRAMACION ON INCIDENTE.id_programacion = PROGRAMACION.id_programacion "
				+ "    INNER JOIN "
				+ "        RUTA ON PROGRAMACION.id_ruta = RUTA.id_ruta "
				+ "    GROUP BY "
				+ "        RUTA.nombre_ruta "
				+ ") "
				+ "SELECT "
				+ "    nombre_ruta, "
				+ "    total_incidentes "
				+ "FROM "
				+ "    RutaIncidentes "
				+ "WHERE "
				+ "    total_incidentes = ( "
				+ "        SELECT MAX(total_incidentes) "
				+ "        FROM RutaIncidentes "
				+ "    );";
		return jdbcTemplate.query(sql, new IncidenteRutaDtoRowMapper());
	}
	
	private static class IncidenteRutaDtoRowMapper implements RowMapper<IncidenteRutaDto> {
        @Override
        public IncidenteRutaDto mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            IncidenteRutaDto bean = new IncidenteRutaDto();
            bean.setNombreRuta(rs.getString("nombre_ruta"));
            bean.setTotalIncidentes(rs.getInt("total_incidentes"));
            return bean;
        }
    }
}
