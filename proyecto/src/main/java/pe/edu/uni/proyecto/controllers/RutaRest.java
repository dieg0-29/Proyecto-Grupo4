package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.RutaDto;
import pe.edu.uni.proyecto.service.RutaService;

@RestController
@RequestMapping("api/registro/ruta")
public class RutaRest {
	@Autowired
	private RutaService service;
	
	public ResponseEntity<?> transferencia(@RequestBody RutaDto bean){
		try {
			boolean registro = service.registrarRuta(bean);
			 if (registro) {
	                return new ResponseEntity<>("Ruta registrada exitosamente.", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Error al registrar ruta.", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        }		
	}

}
