package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.RutaDto;
import pe.edu.uni.proyecto.service.RutaService;

@RestController
@RequestMapping("api/ruta")
public class RutaRest {
	@Autowired
	private RutaService service;
	
	@PostMapping("/registrar")
	public ResponseEntity<?> transferencia(@RequestBody RutaDto bean){
		try {
			bean = service.registrarRuta(bean);
			return ResponseEntity.status(HttpStatus.CREATED).body(bean);
		} catch (Exception e) {
			// Manejo de excepción y respuesta con error 500
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error en el proceso: " + e.getMessage());
		}		
	}

}
