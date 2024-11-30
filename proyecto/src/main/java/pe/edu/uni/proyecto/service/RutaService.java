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
    public void registrarRuta(RutaDto bean) {
        validarRuta(bean.getOrigen(), bean.getDestino());
        validarNombreRuta(bean.getNombre());

        if (bean.getOrigen().equals(bean.getDestino())) {
            throw new RuntimeException("El origen y el destino no pueden ser iguales.");
        }

        if (bean.getDistancia() <= 0) {
            throw new RuntimeException("La distancia debe ser un valor positivo.");
        }

        registrarRuta(bean.getNombre(), bean.getOrigen(), bean.getDestino(), bean.getDistancia());
        System.out.println("Ruta registrada correctamente.");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean modificarRuta(String nombreRuta, RutaDto datosModificados) {
        validarNombreRutaModificacion(nombreRuta);

        if (datosModificados.getOrigen().equals(datosModificados.getDestino())) {
            throw new RuntimeException("El origen y el destino no pueden ser iguales.");
        }

        if (datosModificados.getDistancia() <= 0) {
            throw new RuntimeException("La distancia debe ser un valor positivo.");
        }

        String sql = """
                UPDATE ruta 
                SET nombre_ruta = ?, origen = ?, destino = ?, distancia_km = ? 
                WHERE nombre_ruta = ?
                """;

        Object[] params = {
            datosModificados.getNombre(),
            datosModificados.getOrigen(),
            datosModificados.getDestino(),
            datosModificados.getDistancia(),
            nombreRuta
        };

        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean borrarRuta(String nombreRuta) {
        validarNombreRutaModificacion(nombreRuta);

        String sql = """
                DELETE FROM ruta 
                WHERE nombre_ruta = ?
                """;

        int rowsAffected = jdbcTemplate.update(sql, nombreRuta);
        return rowsAffected > 0;
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void registrarRuta(String nombre, String origen, String destino, double distancia) {
        String sql = """
                INSERT INTO ruta (nombre_ruta, origen, destino, distancia_km)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql, nombre, origen, destino, distancia);
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void validarNombreRuta(String nombre) {
        String sql = "SELECT COUNT(1) FROM ruta WHERE nombre_ruta = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, nombre);

        if (count > 0) {
            throw new RuntimeException("El nombre de la ruta ya existe.");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void validarNombreRutaModificacion(String nombre) {
        String sql = "SELECT COUNT(1) FROM ruta WHERE nombre_ruta = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, nombre);

        if (count == 0) {
            throw new RuntimeException("El nombre de la ruta no existe.");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void validarRuta(String origen, String destino) {
        String sql = "SELECT COUNT(1) FROM ruta WHERE origen = ? AND destino = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, origen, destino);

        if (count > 0) {
            throw new RuntimeException("La ruta ya existe.");
        }
    }
}
