package co.za.distance.service;

import co.za.distance.persistence.Planet;
import co.za.distance.persistence.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    public List<Planet> findAllPlanets() {
       return (List<Planet>) planetRepository.findAll();
    }

    public List<Planet> findByPlanetName(String name) {
        return planetRepository.findByPlanetName(name);
    }

    public List<Planet> findByPlanetNode(String node) { return planetRepository.findByPlanetNode(node);}

    public Planet createPlanet(Planet planet) {
        return planetRepository.save(planet);
    }

    public Planet createPlanet(String planetNode, String planetName) {
        Planet planet = new Planet();
        planet.setPlanetNode(planetNode);
        planet.setPlanetName(planetName);
        return planetRepository.save(planet);
    }

    public Planet update(long id, Planet planet) {
        Planet p = planetRepository.findById(id).get();
        p.setPlanetNode(planet.getPlanetNode());
        p.setPlanetName(planet.getPlanetName());
        return planetRepository.save(p);
    }
    public void saveAllPlanets(List<Planet> planets) {
        planetRepository.saveAll(planets);
    }

    public void deletePlanet(Planet planet) {
        planetRepository.delete(planet);
    }

    public void deletePlanetById(Long id) {
        planetRepository.deleteById(id);
    }

    public void  deletedAllPlanets(List<Planet> planets) {
        planetRepository.deleteAll(planets);
    }

    public Optional<Planet> findById(Long id) {
        return planetRepository.findById(id);
    }
}


