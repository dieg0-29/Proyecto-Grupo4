package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.IncidenteDto;
import pe.edu.uni.proyecto.service.IncidenteService;

@RestController
@RequestMapping("/api/registrar")
public class IncidenteRest {
	@Autowired
	private IncidenteService incidenteService;
	@PostMapping("/incidente")
	public ResponseEntity<String> incidente(@RequestBody IncidenteDto bean) {
		try {
			boolean registro = incidenteService.reportarincidente(bean);
			 if (registro) {
	                return new ResponseEntity<>("Incidente registrado exitosamente.", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Error al incidente.", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	}
}
