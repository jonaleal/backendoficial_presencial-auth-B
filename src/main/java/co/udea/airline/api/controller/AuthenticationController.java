package co.udea.airline.api.controller;

import co.udea.airline.api.dto.RegisterRequestDTO;
import co.udea.airline.api.model.jpa.model.security.AuthenticationResponse;
import co.udea.airline.api.model.jpa.model.security.Person;
import co.udea.airline.api.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequestDTO request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }


}
