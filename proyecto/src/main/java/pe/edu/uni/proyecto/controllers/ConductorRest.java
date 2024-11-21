package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.ConductorDto;
import pe.edu.uni.proyecto.service.ConductorService;

@RestController
@RequestMapping("/api/registrar/conductor")
public class ConductorRest {

	@Autowired
	private ConductorService conductorService;
	
	@PostMapping()
	public ResponseEntity<String> registrarCond(@RequestBody ConductorDto bean) {
		try {
			boolean registro = conductorService.registrarConductor(bean);
			 if (registro) {
	                return new ResponseEntity<>("Conductor registrado exitosamente.", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Error al registrar conductor.", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	}
}
