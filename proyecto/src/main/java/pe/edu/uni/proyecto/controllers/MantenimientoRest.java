package pe.edu.uni.proyecto.controllers;

import java.time.format.DateTimeParseException;

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

    @PostMapping
    public ResponseEntity<String> registrarMantenimiento(@RequestBody MantenimientoDto bean) throws DateTimeParseException {
			
			try {
				mantenimientoService.registroMantenimiento(bean);
				return new ResponseEntity<>("Mantenimiento registrado exitosamente.", HttpStatus.CREATED);
	        } catch (IllegalArgumentException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
    }
}