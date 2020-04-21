package co.za.distance.service;

import co.za.distance.persistence.Route;
import co.za.distance.persistence.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<Route> findAllRoutes() {
       return (List<Route>) routeRepository.findAll();
    }

    public Optional<Route> findById(Long id) {
        return routeRepository.findById(id);
    }
    public Route save(Route route) {
        return (Route) routeRepository.save(route);
    }

    public void deleteRoute(Route route) {
        routeRepository.delete(route);
    }

    public void deleteRouteById(Long id) {
        routeRepository.deleteById(id);
    }

    public void  deletedAllRoutes(List<Route> routes) {
        routeRepository.deleteAll(routes);
    }

}


