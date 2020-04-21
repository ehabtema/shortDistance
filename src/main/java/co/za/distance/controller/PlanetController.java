package co.za.distance.controller;

import co.za.distance.persistence.Planet;
import co.za.distance.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    @Autowired
    private PlanetService planetService;

    @GetMapping
    public List<Planet> allPlanets() {
        return planetService.findAllPlanets();
    }

    @GetMapping("/name/{planetName}")
    public List<Planet> findByName(@PathVariable String name) { return planetService.findByPlanetName(name); }

    @GetMapping("/node/{planetNode}")
    public List<Planet> findByNode(@PathVariable String node) { return planetService.findByPlanetNode(node); }

    @GetMapping("/{id}")
    public Optional<Planet> getById(@PathVariable Long id) {
        return planetService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Planet create(@RequestBody Planet planet) { return planetService.createPlanet(planet); }

    @PostMapping("/{id}")
    public Planet update(@PathVariable Long id, @RequestBody Planet planet) {return planetService.update(id, planet);}

    @DeleteMapping("/delete")
    public void deletePlanets(@RequestBody List<Planet> planets) {
        planetService.deletedAllPlanets(planets);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePlanetById(@PathVariable Long id) {
        planetService.deletePlanetById(id);
    }
}
