package co.udea.airline.api.model.jpa.repository.security;

import co.udea.airline.api.model.jpa.model.security.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import co.udea.airline.api.model.jpa.model.security.Position;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByName(String name);
}
