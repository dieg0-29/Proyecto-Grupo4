package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import pe.edu.uni.proyecto.dto.CarroDto;
import pe.edu.uni.proyecto.service.CarroService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarroRest {
    @Autowired
    private CarroService carroService;

    @PostMapping("/registrar")
    public ResponseEntity<CarroDto> registrarCarro(@RequestBody CarroDto carroDto) throws IllegalArgumentException {
        try {
            CarroDto carroRegistrado = carroService.registrarCarro(carroDto);
            return new ResponseEntity<>(carroRegistrado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
