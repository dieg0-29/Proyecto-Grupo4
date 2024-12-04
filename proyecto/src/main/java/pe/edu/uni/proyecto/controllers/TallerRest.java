package pe.edu.uni.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.ResponseMessage;
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
    public ResponseEntity<ResponseMessage> registrarTaller(@RequestBody TallerDto bean) {
        try {
            tallerService.registrarTaller(bean);
            return new ResponseEntity<>(new ResponseMessage("Taller registrado exitosamente."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ResponseMessage> modificarTaller(@PathVariable int id, @RequestBody TallerDto datosModificados) {
        try {
            tallerService.modificarTaller(id, datosModificados);
            return ResponseEntity.ok(new ResponseMessage("Taller modificado correctamente."));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<ResponseMessage> borrarTaller(@PathVariable int id) {
        try {
            tallerService.borrarTaller(id);
            return ResponseEntity.ok(new ResponseMessage("Taller eliminado correctamente."));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
