package co.udea.airline.api.service;

import co.udea.airline.api.dto.RegisterRequestDTO;
import co.udea.airline.api.model.jpa.model.security.AuthenticationResponse;
import co.udea.airline.api.model.jpa.model.security.Person;
import co.udea.airline.api.model.jpa.repository.security.PersonRepository;
import co.udea.airline.api.model.jpa.repository.security.IdentificationTypeRepository;
import co.udea.airline.api.model.jpa.repository.security.PositionRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final PersonRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final IdentificationTypeRepository idRepository;
    private final PositionRepository positionRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PersonRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 IdentificationTypeRepository idRepository,
                                 PositionRepository positionRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.idRepository = idRepository;
        this.positionRepository = positionRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequestDTO request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByEmail(request.email()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        Person user = new Person();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setIdentificationNumber(request.idNumber());
        user.setIdentificationType(idRepository.findByIdentificationType(request.idType()));
        user.setCity(request.city());
        user.setCountry(request.country());
        user.setPhoneNumber(request.phoneNumber());
        user.setGenre(request.genre());
        user.setPositions(positionRepository.findByName("USER"));
        user.setVerified(true);
        user.setFailedLoginAttempts(0);
        user.setEnabled(true);
        user = repository.save(user);

        String jwt = jwtService.generateToken(user);


        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public AuthenticationResponse authenticate(Person request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Person user = repository.findByEmail(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);


        return new AuthenticationResponse(jwt, "User login was successful");

    }

}
