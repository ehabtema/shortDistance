package co.za.distance.calculator;

import co.za.distance.persistence.Planet;
import co.za.distance.persistence.Route;
import co.za.distance.util.DistanceConstants;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 * This is an implementation of Dijkestra's Short-distance algorithm to
 * find the shortest route from planet Earth to another planet.
 * It first reads planet and route data from CSV files and loads it
 * to an embedded Derby database. It then initializes routes and
 * planets object list that the algorithm depends on.
 */
@Component
public class Distance {

    Logger logger = LoggerFactory.getLogger(Distance.class);
    private List<Planet> planets = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
    private Set<Planet> visited = new HashSet<>();
    private Set<Planet> unvisited = new HashSet<>();
    private Map<Planet, Double> shortDist = new HashMap<>();
    private Map<String, String> previous = new HashMap<>();
    private List<String> routeToDestination = new ArrayList<>();
    private boolean isNewRoute = true;
    private  String dest = "";

    /**
     * Initialize the list of planets, routes and shortDist map.
     */
    public void init() {
        routes = RestAssured.get(DistanceConstants.ROUTE_ROOT).then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Route.class);
        System.out.println("Routes: " + routes.toString());
        planets = RestAssured.get(DistanceConstants.PLANET_ROOT).then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Planet.class);
        System.out.println("Planets: " + planets.toString());

        //initialize shortest dist of all planets/vertices to infinity
        planets.forEach(planet -> shortDist.put(planet, Double.MAX_VALUE));
    }

    /**
     * Gets a set of adjacent planets to the current planet.
     *
     * @param currentPlanet the current planet
     * @return the adjacent planets
     */
    public Set<String> getAdjacentPlanets(Planet currentPlanet) {
        Set<String> adjacent = new HashSet<>();
        routes.forEach(route -> {
            if(route.getOrigin().equalsIgnoreCase(currentPlanet.getPlanetNode()) ||
                route.getDestination().equalsIgnoreCase(currentPlanet.getPlanetNode()))
                adjacent.add(route.getDestination());
        });
        return adjacent;
    }

    /**
     * Gets unvisited planet with shortest dist.
     *
     * @return the unvisited planet with shortest dist
     */
    public Planet getUnvisitedPlanetWithShortestDist() {
        Planet lowestDistancePlanet = null;
        double shortDistValue = Double.MAX_VALUE;

        for(Planet planet : unvisited) {
            double planetDist = shortDist.get(planet).doubleValue();
            if (planetDist < shortDistValue) {
                shortDistValue = planetDist;
                lowestDistancePlanet = planet;
            }
        }
        return lowestDistancePlanet;
    }

    /**
     * Calculate distance of the "to" planet based on the
     * already calculated shortest distance of the "from" planet.
     *
     * @param from
     * @param to
     * @return the distance
     */
    public double calculateDistance(Planet from, Planet to) {
        return shortDist.get(from).doubleValue() + getRouteDist(from, to);
    }

    /**
     * Gets the route distance.
     *
     * @param from the "from" planet
     * @param to the "to" planet
     * @return the edge distance from the route
     */
    public double getRouteDist(Planet from, Planet to) {
        AtomicReference<Double> dist = new AtomicReference<>((double) 0);
        routes.forEach(route -> {
            if(route.getOrigin().equalsIgnoreCase(from.getPlanetNode()) &&
                route.getDestination().equalsIgnoreCase(to.getPlanetNode()))
                 dist.set(route.getDistance());
        });
        return dist.get();
    }
    /**
     * Calculate short distance of all planets from the start planet.
     */
    public void calculateShortDistance(Planet start) {
        shortDist.put(start, 0d);
        unvisited.add(start);
        while(unvisited.size() != 0) {
            Planet current = getUnvisitedPlanetWithShortestDist();
            unvisited.remove(current);
            Set<String> currAdjacent = getAdjacentPlanets(current);
            for (String adj : currAdjacent) {
                Planet adjPlanet = getPlanetByNodeName(adj);
                if (!visited.contains(adjPlanet)) {
                    double dist = calculateDistance(current, adjPlanet);
                    if(shortDist.get(adjPlanet).doubleValue() > dist) {
                        shortDist.put(adjPlanet, dist);
                        previous.put(adj, current.getPlanetNode());
                        unvisited.add(adjPlanet);
                    }
                }
            }
            visited.add(current);
        }
    }

    /**
     * Print shortest distance route to each planet.
     */
    public void printShortDistRouteToEachPlanet() {
        planets.forEach(planet -> {
            getShortDistRouteToPlanet(planet.getPlanetNode());
            routeToDestination.clear();
        });
    }

    /**
     * Gets short dist route to a destination planet.
     *
     * @param destination the destination
     */
    public List<String> getShortDistRouteToPlanet(String destination) {
        if(isNewRoute) {
            dest = destination;
            isNewRoute = false;
            routeToDestination.clear();
        }
        routeToDestination.add(destination);
        String parent = previous.get(destination);
        if(parent !=  null && !parent.equalsIgnoreCase(DistanceConstants.START_PLANET_NODE)) {
            getShortDistRouteToPlanet(parent);
        }
        else {
            if(parent != null)
                routeToDestination.add(parent);
            Collections.reverse(routeToDestination);
            logger.info("Shortest route to " + dest + " is: " + routeToDestination.toString());
            isNewRoute = true;
        }
        return routeToDestination;
    }

    /**
     * Gets planet by node name.
     *
     * @param nodeName the node name
     * @return the planet by node name
     */
    public Planet getPlanetByNodeName(String nodeName) {
        AtomicReference<Planet> p = new AtomicReference<>();
        planets.forEach(planet -> {
            if(planet.getPlanetNode().equalsIgnoreCase(nodeName))
                p.set(planet);
        });
        return p.get();
    }

    public List<Planet> getPlanets() { return planets; }
}
