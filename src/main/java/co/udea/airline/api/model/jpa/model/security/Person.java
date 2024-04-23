package co.udea.airline.api.model.jpa.model.security;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="PERSON")
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID") // TODO: create name conversion strategy
    private Integer personId;

    @OneToOne
    @NotNull
    @JoinColumn(name = "ID_IDENTIFICATION_TYPE")
    private IdentificationType identificationType;

    @NotBlank
    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    private Character genre;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "CITY")
    private String city;
    @Column(name = "RESIDENCE")
    private String address;
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Column(name = "ACCESS_KEY")
    private String password;

    @Column(name = "EXTERNAL_LOGIN_SOURCE")
    private String externalLoginSource;

    private Boolean enabled;

    private Boolean verified;

    private Boolean isAccountNonLocked;

    @Column(name = "FAILED_LOGIN_ATTEMPTS")
    private Integer failedLoginAttempts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PERSON_POSITION", joinColumns = @JoinColumn(name = "PERSON_ID"), inverseJoinColumns = @JoinColumn(name = "POSITION_ID"))
    private List<Position> positions;

    public Set<Privilege> getPrivileges() {
        return getPositions().stream()
                .map(pos -> pos.getPrivileges())
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // prefix all roles with 'ROLE_' and uppercase them
        Set<GrantedAuthority> authorities = getPositions().stream()
                .map(pos -> new SimpleGrantedAuthority("ROLE_".concat(pos.getName().toUpperCase())))
                .collect(Collectors.toSet());

        // add all privleges
        authorities.addAll(getPrivileges());

        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
