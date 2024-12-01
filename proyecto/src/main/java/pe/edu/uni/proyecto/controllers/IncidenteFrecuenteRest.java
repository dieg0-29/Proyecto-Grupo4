package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.IncidenteFrecuenteDto;
import pe.edu.uni.proyecto.service.ConsultaIncidenteFrec;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class IncidenteFrecuenteRest {
	@Autowired    
    private ConsultaIncidenteFrec consultaIncidenteFrec;

    @GetMapping("/api/frecuencia/incidente/{id_tipo}")
    public ResponseEntity<?> frecuenciaIncidente(@PathVariable int id_tipo) {
        try {
            // Llamar al servicio para obtener la frecuencia del incidente
            IncidenteFrecuenteDto bean = consultaIncidenteFrec.frecuenciaIncidente(id_tipo);
            return ResponseEntity.ok(bean); // Devolver el bean con estado 200 OK
            
        } catch (IllegalArgumentException e) {
            // Manejo de IllegalArgumentException y respuesta con error 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage()); // Devolver el mensaje de error
        } catch (Exception e) {
            // Manejo de otras excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado."); // Mensaje genérico para otros errores
        }
    }
}
