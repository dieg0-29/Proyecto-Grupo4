package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.ProgramacionDto;
import pe.edu.uni.proyecto.service.ProgramacionService;

@RestController
@RequestMapping("/api/registrar/programar")
public class ProgramacionRest {

	@Autowired
	private ProgramacionService programacionService;

	@PostMapping()
	public ResponseEntity<String> registrarCliente(@RequestBody ProgramacionDto bean) {
		try {
			boolean registro = programacionService.registrarProg(bean);
			 if (registro) {
	                return new ResponseEntity<>("Programacion registrado exitosamente.", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Error a la programacion.", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	}
}
