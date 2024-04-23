package co.udea.airline.api.service;

import co.udea.airline.api.dto.RegisterRequestDTO;
import co.udea.airline.api.model.jpa.model.security.AuthenticationResponse;
import co.udea.airline.api.model.jpa.model.security.Person;
import co.udea.airline.api.model.jpa.repository.security.IdentificationTypeRepository;
import co.udea.airline.api.model.jpa.repository.security.PersonRepository;
import co.udea.airline.api.model.jpa.repository.security.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


class AuthenticationServiceTest {
    @Mock
    private PersonRepository repository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerWhenUserAlreadyExist() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO("CC", "12345678", "John", "Doe", 'M', LocalDate.of(1990, 1, 1), "555-1234", "Colombia", "Bogota", "Bogota", "Colombia", "test@example.com", "password");
        when(repository.findByEmail(request.email())).thenReturn(Optional.of(new Person()));

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertNull(response.getToken());
        assertEquals("User already exist", response.getMessage());
    }
}