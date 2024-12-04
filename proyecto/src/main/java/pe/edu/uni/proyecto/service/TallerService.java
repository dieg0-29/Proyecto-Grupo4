package pe.edu.uni.proyecto.service;

import java.util.List;
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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registrarTaller(TallerDto bean) {
        validarNombreTaller(bean.getNombre());
        validarNumero(bean.getTelefono());

        String sql = """
                INSERT INTO taller(nombre_taller, direccion, telefono, calificacion) 
                VALUES (?, ?, ?, ?)
                """;

        int calificacionInicial = 0; 
        Object[] datos = {bean.getNombre(), bean.getDireccion(), bean.getTelefono(), calificacionInicial};

        int rowsAffected = jdbcTemplate.update(sql, datos);

        if (rowsAffected == 0) {
            throw new RuntimeException("No se pudo registrar el taller. Intente nuevamente.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void modificarTaller(int id, TallerDto datosModificados) {
        //validarNombreTallerModificacion(datosModificados.getNombre());
        validarNumero(datosModificados.getTelefono());

        String sql = """
                UPDATE taller 
                SET direccion = ?, telefono = ?,nombre_taller = ?
                WHERE id_taller = ?
                """;

        Object[] params = {
            
            datosModificados.getDireccion(),
            datosModificados.getTelefono(),
            datosModificados.getNombre(),
            id
        };

        int rowsAffected = jdbcTemplate.update(sql, params);

        if (rowsAffected == 0) {
            throw new RuntimeException("No se encontró el taller o no se pudo modificar.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void borrarTaller(int id) {
        

        // Eliminar dependencias en REPARACION
        String deleteReparacionSql = """
                Select count(*) FROM reparacion 
                WHERE id_taller = ?
                """;
        int c = jdbcTemplate.queryForObject(deleteReparacionSql,Integer.class ,id);
        String ReparacionSql = """
                Select count(*) FROM mantenimiento 
                WHERE id_taller = ?
                """;
        int w =c+ jdbcTemplate.queryForObject(ReparacionSql,Integer.class ,id);
        if(w != 0) {
        	throw new RuntimeException(" Taller usado.");
        }
        // Eliminar dependencias en MANTENIMIENTO
        String deleteMantenimientoSql = """
                DELETE FROM mantenimiento 
                WHERE id_taller = ?
                """;
        jdbcTemplate.update(deleteMantenimientoSql, id);

        // Finalmente, eliminar el taller
        String deleteTallerSql = """
                DELETE FROM taller 
                WHERE id_taller = ?
                """;
        int rowsAffected = jdbcTemplate.update(deleteTallerSql, id);

        if (rowsAffected == 0) {
            throw new RuntimeException("No se encontró el taller para eliminar.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void validarNumero(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío.");
        }

        if (telefono.length() > 9) {
            throw new IllegalArgumentException("El número de teléfono no puede exceder los 9 dígitos.");
        }
        if (!Pattern.matches("^[9][0-9]{8}$", telefono)) {
            throw new IllegalArgumentException("El teléfono debe empezar con 9 y tener 9 dígitos numéricos.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void validarNombreTaller(String nombre) {
        String sql = "SELECT COUNT(1) FROM taller WHERE nombre_taller = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, nombre);

        if (count > 0) {
            throw new RuntimeException("El nombre del taller ya está registrado.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void validarNombreTallerModificacion(String nombre) {
        String sql = "SELECT COUNT(1) FROM taller WHERE nombre_taller = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, nombre);

        if (count == 0) {
            throw new RuntimeException("No existe un taller con el nombre especificado.");
        }
    }

    @Transactional(readOnly = true)
    public List<TallerDto> listarTalleres() {
        String sql = """
                SELECT id_taller, nombre_taller AS Nombre, direccion AS Direccion, telefono AS Telefono, calificacion AS Calificacion
                FROM taller
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TallerDto(
                rs.getInt("id_taller"),
                rs.getString("Nombre"),
                rs.getString("Direccion"),
                rs.getString("Telefono"),
                rs.getDouble("Calificacion")
        ));
    }
}
