package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.CarroDto;
import pe.edu.uni.proyecto.service.CarroService;

<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.RestController;

>>>>>>> bd4250111447cc7f287439f8c8f29a8589965b9b
@RestController
public class CarroRest {
    @Autowired
    private CarroService carroService;

    @PostMapping("/registrarcarro")
    public ResponseEntity<CarroDto> registrarCarro(@RequestBody CarroDto carroDto) throws IllegalArgumentException {
        try {
            CarroDto carroRegistrado = carroService.registrarCarro(carroDto);
            return new ResponseEntity<>(carroRegistrado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
