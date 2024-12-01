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

import pe.edu.uni.proyecto.dto.TallerDto;
import pe.edu.uni.proyecto.service.TallerService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/taller")
public class TallerRest {

    @Autowired
    private TallerService tallerService;

    @GetMapping("/listar")
    public ResponseEntity<List<TallerDto>> listarTalleres() {
        List<TallerDto> talleres = tallerService.listarTalleres();
        return ResponseEntity.ok(talleres);
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarTaller(@RequestBody TallerDto bean) {
        try {
            boolean registrado = tallerService.registrarTaller(bean);
            return registrado ? new ResponseEntity<>("Taller registrado exitosamente.", HttpStatus.CREATED) : 
                                new ResponseEntity<>("Error al registrar taller.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarTaller(@RequestParam String nombreTaller, @RequestBody TallerDto datosModificados) {
        System.out.println("Datos recibidos: " + datosModificados);
        try {
            boolean actualizado = tallerService.modificarTaller(nombreTaller, datosModificados);
            return actualizado ? ResponseEntity.ok("Taller modificado correctamente.") : 
                                 ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el taller.");
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> borrarTaller(@RequestParam String nombreTaller) {
        try {
            boolean eliminado = tallerService.borrarTaller(nombreTaller);
            return eliminado ? ResponseEntity.ok("Taller eliminado correctamente.") : 
                               ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el taller.");
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}