package pe.edu.uni.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.proyecto.dto.ResponseMessage;
import pe.edu.uni.proyecto.dto.RutaDto;
import pe.edu.uni.proyecto.service.RutaService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/ruta")
public class RutaRest {

    @Autowired
    private RutaService rutaService;

    @PostMapping("/registrar")
    public ResponseEntity<ResponseMessage> registrarRuta(@RequestBody RutaDto bean) {
        try {
            rutaService.registrarRuta(bean);
            return new ResponseEntity<>(new ResponseMessage("Ruta registrada exitosamente."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ResponseMessage> modificarRuta(@PathVariable long id, @RequestBody RutaDto datosModificados) {
        if (datosModificados == null) {
            return new ResponseEntity<>(new ResponseMessage("Error: datosModificados no puede ser nulo."), HttpStatus.BAD_REQUEST);
        }
        try {
            rutaService.modificarRuta(id, datosModificados);
            return ResponseEntity.ok(new ResponseMessage("Ruta modificada exitosamente."));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<ResponseMessage> borrarRuta(@PathVariable int id) {
        try {
            rutaService.borrarRuta(id);
            return ResponseEntity.ok(new ResponseMessage("Ruta eliminada exitosamente."));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarRutas() {
        try {
            List<RutaDto> rutas = rutaService.listarRutas();
            return ResponseEntity.ok(rutas);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error al listar rutas: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
