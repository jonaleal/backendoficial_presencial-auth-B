package co.udea.airline.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Service;

import co.udea.airline.api.model.jpa.model.security.Person;
import co.udea.airline.api.model.jpa.repository.security.PersonRepository;
import co.udea.airline.api.utils.common.JwtUtils;

@Service
public class LoginService {

        @Autowired
        PersonRepository personRepository;

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        JwtUtils jwtUtils;

        public Jwt authenticateUser(String email, String password) throws AuthenticationException {

                Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
                auth = authenticationManager.authenticate(auth);

                if (auth.isAuthenticated()) {
                        Optional<Person> p = personRepository.findByEmail(auth.getName());
                        if (!p.isPresent())
                                throw new UsernameNotFoundException("User hasn't registerred yet");
                        return jwtUtils.createToken(p.get());
                }
                throw new AuthenticationServiceException(
                                "cannot authenticate user %s".formatted(auth.getName()));
        }

        public Jwt authenticateIdToken(String jwt) {

                Authentication auth = authenticationManager.authenticate(new BearerTokenAuthenticationToken(jwt));
                if (auth.isAuthenticated()) {
                        Jwt token = (Jwt) auth.getPrincipal();
                        Optional<Person> p = personRepository.findByEmail(token.getClaimAsString("email"));
                        if (!p.isPresent())
                                throw new UsernameNotFoundException("User hasn't registered yet");
                        return jwtUtils.createToken(p.get());
                }
                throw new AuthenticationServiceException(
                                "cannot authenticate user %s".formatted(auth.getName()));
        }

}
