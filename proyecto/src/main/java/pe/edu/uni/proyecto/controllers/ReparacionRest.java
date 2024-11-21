package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.ReparacionDto;
import pe.edu.uni.proyecto.service.ReparacionService;

@RestController
@RequestMapping("/api/registrar/reparacion")
public class ReparacionRest {

    @Autowired
    private ReparacionService reparacionService;

    @PostMapping
    public ResponseEntity<String> registrarReparacion(@RequestBody ReparacionDto bean) {
        try {
            reparacionService.reparacion(bean);
            return new ResponseEntity<>("Reparación registrada exitosamente.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}