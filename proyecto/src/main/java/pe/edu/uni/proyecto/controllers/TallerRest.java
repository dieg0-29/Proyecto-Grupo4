package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.TallerDto;
import pe.edu.uni.proyecto.service.TallerService;

@RestController
@RequestMapping("/api/taller")
public class TallerRest {
    @Autowired
    private TallerService tallerService;

    @PostMapping
    public ResponseEntity<String> registrarTaller(@RequestBody TallerDto bean) {
        try {
            boolean registro = tallerService.registrarTaller(bean);
            if (registro) {
                return new ResponseEntity<>("Taller registrado exitosamente.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error al registrar taller.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarTaller(
            @RequestParam String nombreTaller, 
            @RequestBody TallerDto datosModificados) {
        try {
            boolean actualizado = tallerService.modificarTaller(nombreTaller, datosModificados);
            if (actualizado) {
                return ResponseEntity.ok("Taller modificado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el taller.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> borrarTaller(@RequestParam String nombreTaller) {
        try {
            boolean eliminado = tallerService.borrarTaller(nombreTaller);
            if (eliminado) {
                return ResponseEntity.ok("Taller eliminado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el taller.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }
}