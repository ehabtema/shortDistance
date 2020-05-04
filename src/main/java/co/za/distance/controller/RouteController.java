package co.za.distance.controller;

import co.za.distance.error.RouteNotFoundException;
import co.za.distance.persistence.Route;
import co.za.distance.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Route REST service
 */

@RestController
@RequestMapping ("/routes")
public class RouteController {
    @Autowired
    RouteService routeService;

    @GetMapping
    public List<Route> allRoutes() { return routeService.findAllRoutes();}

    @GetMapping("/{id}")
    public Optional<Route> getById(@PathVariable long id) { return routeService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Route create(@RequestBody Route route) {
        return routeService.save(route);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        routeService.deleteRouteById(id);
    }

    @PutMapping("update/{id}")
    public Route update(@RequestBody Route route, @PathVariable long id) {
        routeService.findById(id).orElseThrow(RouteNotFoundException::new);
        return routeService.save(route);
    }
}
