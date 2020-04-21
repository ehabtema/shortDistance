package co.za.distance.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    List<Planet> findByPlanetName(String name);
    List<Planet> findByPlanetNode(String node);
}
