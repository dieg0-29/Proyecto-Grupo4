package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.MantenimientoDto;
import pe.edu.uni.proyecto.service.MantenimientoService;

@RestController
@RequestMapping("/api/registrar/mantenimiento")
public class MantenimientoRest {
	
	@Autowired
    private MantenimientoService mantenimientoService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarMantenimiento(@RequestBody MantenimientoDto mantenimientoDto) {
        try {
            boolean resultado = mantenimientoService.registroMantenimiento(mantenimientoDto);
            if (resultado) {
                return new ResponseEntity<>("Mantenimiento registrado exitosamente.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error al registrar el mantenimiento.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalArgumentException e) {
            // Manejo de errores específicos
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            // Manejo de errores generales
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejo de errores inesperados
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
