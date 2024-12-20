package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.uni.proyecto.dto.ConductorFrecuenteDto;
import pe.edu.uni.proyecto.service.ConsultaConductorFrecService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class ConductorFrecuenteRest {
	
	@Autowired
    private ConsultaConductorFrecService consultaConductorFrecService;
	@GetMapping("/api/conductor/{idConductor}/incidentes")
    public ResponseEntity<?> obtenerFrecuenciaIncidente(
            @PathVariable int idConductor,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFinal) {
        
        ConductorFrecuenteDto bean = new ConductorFrecuenteDto();
        bean.setIdConductor(idConductor);
        bean.setFechaInicio(fechaInicio);
        bean.setFechaFinal(fechaFinal);

        try {
            ConductorFrecuenteDto resultado = consultaConductorFrecService.frecuenciaIncidenteconductor(bean);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            // Manejo de errores específicos de validación (como conductor no válido)
            return ResponseEntity.badRequest().body(e.getMessage()); // Devuelve el mensaje de error
        } catch (RuntimeException e) {
            // Manejo de errores de validación de fechas
            return ResponseEntity.badRequest().body(e.getMessage()); // Devuelve el mensaje de error
        } catch (Exception e) {
            // Manejo de otros errores
            return ResponseEntity.status(500).body("Error interno del servidor."); // Mensaje genérico
        }
    }
}
