package pe.edu.uni.proyecto.controllers;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.IncidenteDto;

import pe.edu.uni.proyecto.dto.ResponseMessage;
import pe.edu.uni.proyecto.service.IncidenteService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/incidente")
public class IncidenteRest {
	@Autowired
	private IncidenteService incidenteService;

	@PostMapping("registrar")
	 public ResponseEntity<ResponseMessage> registrarReparacion(@RequestBody IncidenteDto bean) throws DateTimeParseException {
        try {
            incidenteService.reportarincidente(bean);
            return new ResponseEntity<>(new ResponseMessage("Incidente registrado exitosamente."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@GetMapping("/lista")
	public ResponseEntity<?> obtenerListaIncidentes() {
		try {
			List<Map<String, Object>> lista = incidenteService.obtenerListaIncidente();
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los incidentes: " + e.getMessage());
		}
	}

}
