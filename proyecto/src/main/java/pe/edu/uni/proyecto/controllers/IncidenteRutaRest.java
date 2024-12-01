package pe.edu.uni.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.IncidenteRutaDto;
import pe.edu.uni.proyecto.service.IncidenteRutaService;


@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/incidente/rutas")
public class IncidenteRutaRest {
	@Autowired
	private IncidenteRutaService rutaService;
	
	@GetMapping("/mas-incidentes")
	public List<IncidenteRutaDto> obtenerRutasConMasIncidentes() {
		return rutaService.obtenerRutasConMasIncidentes();
	}
}
