package pe.edu.uni.proyecto.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.proyecto.dto.LoginDto;
import pe.edu.uni.proyecto.service.LoginService;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class LoginRest {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String usuario, @RequestParam String clave) {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsuario(usuario);
        loginDto.setClave(clave);
        
        try {
            Map<String,Object> rec = loginService.login(loginDto);
            return ResponseEntity.status(HttpStatus.OK).body(rec); // Cambié a HttpStatus.OK para un inicio de sesión exitoso
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("no existe")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
            } else if (errorMessage.contains("no es correcta")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La clave ingresada no es correcta");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
            }
        }
    }
}

