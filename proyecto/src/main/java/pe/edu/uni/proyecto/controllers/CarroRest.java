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

    @PostMapping("/api/registrar/carro")
    public ResponseEntity<String> registrarCarro(@RequestBody CarroDto carroDto) {
        try {
            boolean carroRegistrado = carroService.registrarCarro(carroDto);
            if (carroRegistrado) {
                return new ResponseEntity<>("Carro registrado exitosamente.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error al carro.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
