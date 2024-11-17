package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.CarroDto;
import pe.edu.uni.proyecto.service.CarroService;

@RestController
public class CarroRest {
    @Autowired
    private CarroService carroService;

    @PostMapping("/registrarcarro")
    public ResponseEntity<Object> registrarCarro(@RequestBody CarroDto carroDto) {
        try {
            CarroDto carroRegistrado = carroService.registrarCarro(carroDto);
            return new ResponseEntity<>(carroRegistrado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
