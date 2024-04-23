package co.udea.airline.api.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.udea.airline.api.dto.JWTResponseDTO;
import co.udea.airline.api.dto.LoginRequestDTO;
import co.udea.airline.api.dto.OAuth2LoginRequestDTO;
import co.udea.airline.api.service.LoginService;
import co.udea.airline.api.utils.common.StandardResponse;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<JWTResponseDTO>> login(@RequestBody LoginRequestDTO loginRequest) {

        try {

            Jwt jwt = loginService.authenticateUser(loginRequest.email(), loginRequest.password());
            JWTResponseDTO response = new JWTResponseDTO(jwt.getSubject(), LocalDate.from(jwt.getExpiresAt()),
                    jwt.getTokenValue());
            return ResponseEntity.ok()
                    .body(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK, response));

        } catch (AuthenticationException exception) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardResponse<>(
                    StandardResponse.StatusStandardResponse.ERROR, "incorrect username or password"));

        }
    }

    @PostMapping("/login/google")
    public ResponseEntity<StandardResponse<JWTResponseDTO>> loginWithOauth2(
            @RequestBody OAuth2LoginRequestDTO loginRequest) {
        try {

            Jwt jwt = loginService.authenticateIdToken(loginRequest.idToken());
            JWTResponseDTO response = new JWTResponseDTO(jwt.getSubject(), LocalDate.from(jwt.getExpiresAt()),
                    jwt.getTokenValue());
            return ResponseEntity.ok()
                    .body(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK, response));

        } catch (UsernameNotFoundException exception) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StandardResponse<>(StandardResponse.StatusStandardResponse.ERROR, "user not found"));

        } catch (AuthenticationException exception) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse<>(StandardResponse.StatusStandardResponse.ERROR, "invalid jwt"));

        }
    }

}
