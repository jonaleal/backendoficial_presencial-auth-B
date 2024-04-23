package co.udea.airline.api.model.jpa.model.security;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "POSITION_PRIVILEGE")
public class PositionPrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSITION_PRIVILEGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POSITION_ID")
    @JsonIgnore
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIVILEGE_ID")
    @JsonIgnore
    private Privilege privilege;

}
