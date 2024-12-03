package pe.edu.uni.proyecto.service;

import java.util.List;

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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registrarRuta(RutaDto bean) {
        validarNombreRuta(bean.getNombre());
        validarRuta(bean.getOrigen(), bean.getDestino());
        if (bean.getOrigen().equals(bean.getDestino())) {
            throw new IllegalArgumentException("El origen y el destino no pueden ser iguales.");
        }
        validarDistancia(bean.getDistancia());

        String sql = """
                INSERT INTO ruta (nombre_ruta, origen, destino, distancia_km)
                VALUES (?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql, bean.getNombre(), bean.getOrigen(), bean.getDestino(), bean.getDistancia());
        System.out.println("Ruta registrada correctamente.");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void modificarRuta(long id, RutaDto datosModificados) {
        //validarNombreRutaModificacion(datosModificados.getNombre());

        if (datosModificados.getOrigen().equals(datosModificados.getDestino())) {
            throw new IllegalArgumentException("El origen y el destino no pueden ser iguales.");
        }

        if (datosModificados.getDistancia() <= 0) {
            throw new IllegalArgumentException("La distancia debe ser un valor positivo.");
        }

        String sql = """
                UPDATE ruta 
                SET origen = ?, destino = ?, distancia_km = ?, nombre_ruta= ?
                WHERE id_ruta = ?
                """;

        int rowsAffected = jdbcTemplate.update(sql, datosModificados.getOrigen(),datosModificados.getDestino(),datosModificados.getDistancia(),datosModificados.getNombre(),id);

        if (rowsAffected == 0) {
            throw new RuntimeException("No se pudo modificar la ruta. Verifique que el nombre de la ruta sea correcto.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void borrarRuta(String nombreRuta) {
        validarNombreRutaModificacion(nombreRuta);

        // Eliminar dependencias en REPARACION
        String deleteReparacionSql = """
                DELETE FROM reparacion 
                WHERE id_incidente IN (
                    SELECT id_incidente 
                    FROM incidente 
                    WHERE id_programacion IN (
                        SELECT id_programacion 
                        FROM programacion 
                        WHERE id_ruta = (SELECT id_ruta FROM ruta WHERE nombre_ruta = ?)
                    )
                )
                """;
        jdbcTemplate.update(deleteReparacionSql, nombreRuta);
    
        // Eliminar dependencias en INCIDENTE
        String deleteIncidenteSql = """
                DELETE FROM incidente 
                WHERE id_programacion IN (
                    SELECT id_programacion 
                    FROM programacion 
                    WHERE id_ruta = (SELECT id_ruta FROM ruta WHERE nombre_ruta = ?)
                )
                """;
        jdbcTemplate.update(deleteIncidenteSql, nombreRuta);
    
        // Eliminar dependencias en PROGRAMACION
        String deleteProgramacionSql = """
                DELETE FROM programacion 
                WHERE id_ruta = (SELECT id_ruta FROM ruta WHERE nombre_ruta = ?)
                """;
        jdbcTemplate.update(deleteProgramacionSql, nombreRuta);
    
        // Finalmente, eliminar la ruta
        String deleteRutaSql = """
                DELETE FROM ruta 
                WHERE nombre_ruta = ?
                """;
        int rowsAffected = jdbcTemplate.update(deleteRutaSql, nombreRuta);

        if (rowsAffected == 0) {
            throw new RuntimeException("No se encontró ninguna ruta con el nombre proporcionado.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void validarNombreRuta(String nombre) {
        String sql = "SELECT COUNT(1) FROM ruta WHERE nombre_ruta = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, nombre);

        if (count > 0) {
            throw new RuntimeException("El nombre de la ruta ya está registrado en el sistema.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void validarNombreRutaModificacion(String nombre) {
        String sql = "SELECT COUNT(1) FROM ruta WHERE nombre_ruta = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, nombre);

        if (count == 0) {
            throw new RuntimeException("No existe una ruta con el nombre especificado.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void validarRuta(String origen, String destino) {
        String sql = "SELECT COUNT(1) FROM ruta WHERE origen = ? AND destino = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, origen, destino);

        if (count > 0) {
            throw new RuntimeException("Ya existe una ruta con el origen y destino especificados.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void validarDistancia(double distancia) {
        if (distancia <= 0 || distancia > 999.99) {
            throw new IllegalArgumentException("La distancia debe ser mayor a 0 y no mayor a 999.99.");
        }
    }

    @Transactional(readOnly = true)
    public List<RutaDto> listarRutas() {
        String sql = """
                SELECT id_ruta, nombre_ruta AS Nombre, origen AS Origen, destino AS Destino, distancia_km AS Distancia
                FROM ruta
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new RutaDto(
                rs.getInt("id_ruta"),
                rs.getString("Nombre"),
                rs.getString("Origen"),
                rs.getString("Destino"),
                rs.getDouble("Distancia")
        ));
    }
}
