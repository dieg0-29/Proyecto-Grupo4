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
    public boolean registrarTaller(TallerDto bean) {
        validarNumero(bean.getTelefono());

        // Registrar taller
        String sql = """
                INSERT INTO taller(nombre_taller, direccion, telefono, calificacion) VALUES (?, ?, ?, ?)
                """;

        int calif = 0; 
        Object[] datos = {bean.getNombreTaller(), bean.getDireccion(), bean.getTelefono(), calif};
        int rowsAffected = jdbcTemplate.update(sql, datos);
        
        return rowsAffected > 0;
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    private void validarNumero(String telefono) {
        if (telefono.length() > 9) {
            throw new IllegalArgumentException("El número de teléfono no puede tener más de 9 dígitos.");
        }
        if (!Pattern.matches("^[9][0-9]{8}$", telefono)) {
            throw new IllegalArgumentException("El teléfono debe empezar por 9 y tener 9 dígitos numéricos.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean modificarTaller(String nombreTaller, TallerDto datosModificados) {
        validarNumero(datosModificados.getTelefono());

        String sql = """
                UPDATE taller 
                SET direccion = ?, telefono = ?, calificacion = ? 
                WHERE nombre_taller = ?
                """;

        Object[] params = {datosModificados.getDireccion(), datosModificados.getTelefono(), datosModificados.getCalificacion(),nombreTaller};

        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean borrarTaller(String nombreTaller) {
        String sql = """
                DELETE FROM taller 
                WHERE nombre_taller = ?
                """;

        int rowsAffected = jdbcTemplate.update(sql, nombreTaller);
        return rowsAffected > 0;
    }
}