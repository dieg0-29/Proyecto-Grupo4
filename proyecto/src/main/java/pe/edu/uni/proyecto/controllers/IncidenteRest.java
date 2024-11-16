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
@RequestMapping("/transporte")
public class IncidenteRest {
	@Autowired
	private IncidenteService incidenteService;
	@PostMapping("/incidente")
	public ResponseEntity<?> incidente(@RequestBody IncidenteDto bean) {
		try {
			bean = incidenteService.reportarincidente(bean);
			return ResponseEntity.status(HttpStatus.CREATED).body(bean);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error en el proceso: " + e.getMessage());
		}
	}
}
