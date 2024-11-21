package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.TallerDto;
import pe.edu.uni.proyecto.service.TallerService;


@RestController
@RequestMapping("/api/registrar/taller")
public class TallerRest {
	@Autowired
	private TallerService tallerService;
	
	public ResponseEntity<String> registrarTaller(@RequestBody TallerDto bean) {
		try {
			boolean registro = tallerService.registrarTaller(bean);
			 if (registro) {
	                return new ResponseEntity<>("Taller registrado exitosamente.", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Error al registrar taller.", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	}
}
