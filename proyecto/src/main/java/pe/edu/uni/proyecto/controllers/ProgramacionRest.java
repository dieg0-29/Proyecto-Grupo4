package pe.edu.uni.proyecto.controllers;

import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.MantenimientoDto;
import pe.edu.uni.proyecto.dto.ProgramacionDto;
import pe.edu.uni.proyecto.dto.ResponseMessage;
import pe.edu.uni.proyecto.service.ProgramacionService;

@RestController
@CrossOrigin("http://localhost:5500")
@RequestMapping("/api/registrar/programar")
public class ProgramacionRest {

	@Autowired
	private ProgramacionService programacionService;

	@PostMapping
    public ResponseEntity<ResponseMessage> registrarMantenimiento(@RequestBody ProgramacionDto bean) throws DateTimeParseException {
        try {
        	programacionService.registrarProg(bean);
            return new ResponseEntity<>(new ResponseMessage("Mantenimiento registrado exitosamente."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseMessage("Error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
