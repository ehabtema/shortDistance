package co.za.distance.controller;

import co.za.distance.calculator.Distance;
import co.za.distance.persistence.Planet;
import co.za.distance.util.DistanceConstants;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Short distance route REST service
 */

@RestController
@RequestMapping("/distance")
public class DistanceController {

    @GetMapping("/{node}")
    public List<String> getShortRouteToNode(@PathVariable String node) {
        Distance distance = new Distance();
        distance.init();
        Planet earth = new Planet();
        earth.setPlanetNode(DistanceConstants.START_PLANET_NODE);
        earth.setPlanetName(DistanceConstants.START_PLANET_NAME);
        distance.calculateShortDistance(earth);
        return distance.getShortDistRouteToPlanet(node);
    }
}
