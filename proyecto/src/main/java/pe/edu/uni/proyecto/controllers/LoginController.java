package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.proyecto.dto.LoginDto;
import pe.edu.uni.proyecto.service.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            String mensaje = loginService.login(loginDto);
            return ResponseEntity.ok(mensaje); // Devuelve el mensaje de éxito
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Devuelve el mensaje de error
        }
    }
}
