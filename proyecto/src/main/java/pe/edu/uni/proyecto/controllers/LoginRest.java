package pe.edu.uni.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            LoginDto loginDto = new LoginDto();
            loginDto.setUsuario(usuario);
            loginDto.setClave(clave);
            String mensaje = loginService.login(loginDto);
            return ResponseEntity.ok().body(new ResponseMessage(mensaje)); // Retorna el mensaje en formato JSON
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage())); // Retorna el error en formato JSON
        }
    }

    // Clase interna para el mensaje de respuesta
    static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

