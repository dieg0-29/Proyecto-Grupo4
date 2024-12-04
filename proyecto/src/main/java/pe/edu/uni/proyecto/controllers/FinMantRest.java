package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.FinMantDto;
import pe.edu.uni.proyecto.service.FinMantService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/finalizar/mantenimiento")
public class FinMantRest {
	
	@Autowired
	private FinMantService finMantService;
	
	@PostMapping()
	public ResponseEntity<?> finalizarMantenimiento(@RequestBody FinMantDto bean) {
		try {
			bean = finMantService.finalizarMantenimiento(bean);
			return ResponseEntity.status(HttpStatus.CREATED).body(bean);
		} catch (Exception e) {
			// Manejo de excepción y respuesta con error 500
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error al registrar la programacion: " + e.getMessage());
		}
	}
}
