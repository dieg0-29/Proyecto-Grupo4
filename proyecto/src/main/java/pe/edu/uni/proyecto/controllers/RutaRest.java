package pe.edu.uni.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.RutaDto;
import pe.edu.uni.proyecto.service.RutaService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/ruta")
public class RutaRest {

    @Autowired
    private RutaService rutaService;

    @GetMapping("/listar")
    public ResponseEntity<List<RutaDto>> listarRutas() {
        List<RutaDto> rutas = rutaService.listarRutas();
        return ResponseEntity.ok(rutas);
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarRuta(@RequestBody RutaDto bean) {
        try {
            rutaService.registrarRuta(bean);
            return new ResponseEntity<>("Ruta registrada exitosamente.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarRuta(@RequestParam String nombreRuta, @RequestBody RutaDto datosModificados) {
        try {
            boolean actualizado = rutaService.modificarRuta(nombreRuta, datosModificados);
            return actualizado ? ResponseEntity.ok("Ruta modificada correctamente.") : 
                                 ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la ruta.");
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> borrarRuta(@RequestParam String nombreRuta) {
        try {
            boolean eliminado = rutaService.borrarRuta(nombreRuta);
            return eliminado ? ResponseEntity.ok("Ruta eliminada correctamente.") : 
                               ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la ruta.");
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}