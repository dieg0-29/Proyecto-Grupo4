package pe.edu.uni.proyecto.controllers;

import java.util.List;
import java.util.Map;

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

import pe.edu.uni.proyecto.dto.CarroDto;
import pe.edu.uni.proyecto.dto.ResponseMessage;
import pe.edu.uni.proyecto.service.CarroService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/carro")
public class CarroRest {
    @Autowired
    private CarroService carroService;

    @PostMapping("/registrar")
    public ResponseEntity<ResponseMessage> registrarCarro(@RequestBody CarroDto bean) {
        try {
            carroService.registrarCarro(bean);
            return new ResponseEntity<>(new ResponseMessage("Incidente registrado exitosamente."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/lista")
    public ResponseEntity<?> obtenerCarrosConEstadoDescripcion() {
        try {
            List<Map<String, Object>> lista = carroService.obtenerCarrosConDescripcionEstado();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los carros: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/eliminar/{placa}")
    public ResponseEntity<?> eliminarCarro(@PathVariable String placa) {
        try {
            carroService.eliminarCarro(placa);
            return ResponseEntity.ok("Carro eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarCarro(@RequestBody CarroDto bean) {
        try {
            carroService.editarCarro(bean);
            return ResponseEntity.ok("Los datos del carro se han actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
