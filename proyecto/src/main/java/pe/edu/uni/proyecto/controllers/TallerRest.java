package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping()
	public ResponseEntity<?> registrarTaller(@RequestBody TallerDto bean) {
		try {
			bean = tallerService.registrarTaller(bean);
			return ResponseEntity.status(HttpStatus.CREATED).body(bean);
		} catch (Exception e) {
			// Manejo de excepción y respuesta con error 500
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error al registrar el taller: " + e.getMessage());
		}
	}
}
