package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.ReparacionDto;
import pe.edu.uni.proyecto.service.ReparacionService;


@RestController
@RequestMapping("api/registrar/reparacion")
public class ReparacionRest {
	
	@Autowired
	private ReparacionService service;
	
	public ResponseEntity<String> transferencia(@RequestBody ReparacionDto bean){
		try {
			boolean registro = service.reparacion(bean);
			 if (registro) {
	                return new ResponseEntity<>("Reparacion registrado exitosamente.", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Error al registrar reparacion.", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        }		
	}	

}