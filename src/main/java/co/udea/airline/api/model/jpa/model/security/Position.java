package co.udea.airline.api.model.jpa.model.security;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Position { // == Role

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSITION_ID")
    private Long positionId;

    @Column(name="POSITION_NAME")
    private String name;

    @Column(name="DETAIL")
    private String description;

    @ManyToMany
    @JoinTable(name = "POSITION_PRIVILEGE", joinColumns = @JoinColumn(name = "POSITION_ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID"))
    private List<Privilege> privileges;

}
