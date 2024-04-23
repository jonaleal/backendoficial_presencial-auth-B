package co.udea.airline.api.utils.common;

import java.security.KeyPair;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import co.udea.airline.api.model.jpa.model.security.Person;
import co.udea.airline.api.model.jpa.model.security.Position;
import co.udea.airline.api.model.jpa.model.security.Privilege;

@Component
public class JwtUtils {

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    JwtDecoder jwtDecoder;

    @Autowired
    KeyPair keyPair;

    private final Long EXPIRATION = 8 * 60 * 60L; // in seconds

    public Jwt createToken(Person person) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(person.getEmail())
                .claim("roles", person.getPositions().stream()
                        .map(Position::getName).collect(Collectors.toList()))
                .claim("privileges", person.getPrivileges().stream()
                        .map(Privilege::getName).collect(Collectors.toList()))
                .issuer("https://airline-api.com")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRATION))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims));

    }

    public Jwt getToken(String token) {
        return jwtDecoder.decode(token);
    }

    public List<GrantedAuthority> getAuthorities(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");
        List<String> privileges = jwt.getClaimAsStringList("privileges");

        List<GrantedAuthority> authorities = roles.stream()
                .map(roleStr -> new SimpleGrantedAuthority("ROLE_".concat(roleStr)))
                .collect(Collectors.toList());

        authorities.addAll(privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
                
        return authorities;
    }

    public boolean validateToken(Jwt jwt) throws JwtException {
        if (Instant.now().isAfter(jwt.getExpiresAt())) {
            throw new JwtException("token has expired");
        }
        return false;
    }

}
