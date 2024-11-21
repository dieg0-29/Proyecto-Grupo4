package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.uni.proyecto.dto.TallerreparacionDto;
import pe.edu.uni.proyecto.service.TallerOperacionesService;

@RestController

public class TallerOperacionesRest {

	@Autowired
    private TallerOperacionesService tallerService;

    // Endpoint para obtener el taller con más operaciones
    @GetMapping("/api/consulta/taller/operaciones")
    public ResponseEntity<TallerreparacionDto> obtenerTallerConMasOperaciones() {
        try {
            TallerreparacionDto taller = tallerService.obtenerTallerConMasOperaciones();
            return ResponseEntity.ok(taller); // Devuelve 200 OK con el DTO
        } catch (RuntimeException e) {
            // Manejo de excepciones: devuelve un error 500 con un mensaje
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null); // Podrías devolver un mensaje de error más detallado si lo deseas
        }
    }
}