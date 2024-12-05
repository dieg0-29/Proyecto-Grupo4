package pe.edu.uni.proyecto.controllers;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import pe.edu.uni.proyecto.dto.ReparacionDto;
import pe.edu.uni.proyecto.dto.ResponseMessage;
import pe.edu.uni.proyecto.service.ReparacionService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("api/reparacion")
public class ReparacionRest {

    @Autowired
    private ReparacionService reparacionService;

    @PostMapping("/registrar")
    public ResponseEntity<ResponseMessage> registrarReparacion(@RequestBody ReparacionDto bean) throws DateTimeParseException {
        try {
            reparacionService.reparacion(bean);
            return new ResponseEntity<>(new ResponseMessage("Reparacion registrada exitosamente."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
    
    @GetMapping("/lista")
	public ResponseEntity<?> obtenerTodosLosMovimientos() {
	    try {
	        List<Map<String, Object>> lista = reparacionService.consultaTodosLasReparaciones();
	        return ResponseEntity.ok(lista);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al obtener los movimientos: " + e.getMessage());
	    }
	}
    
    
}