package pe.edu.uni.proyecto.controllers;

import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.IncidenteDto;
import pe.edu.uni.proyecto.dto.MantenimientoDto;
import pe.edu.uni.proyecto.dto.ResponseMessage;
import pe.edu.uni.proyecto.service.IncidenteService;

@RestController
@CrossOrigin("http://localhost:5500")
@RequestMapping("/api/registrar")
public class IncidenteRest {
	@Autowired
	private IncidenteService incidenteService;

	@PostMapping("/incidente")
	 public ResponseEntity<ResponseMessage> registrarReparacion(@RequestBody IncidenteDto bean) throws DateTimeParseException {
        try {
            incidenteService.reportarincidente(bean);
            return new ResponseEntity<>(new ResponseMessage("Incidente registrado exitosamente."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
