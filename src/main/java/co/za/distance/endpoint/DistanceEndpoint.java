package co.za.distance.endpoint;

import co.za.distance.calculator.Distance;
import co.za.distance.persistence.Planet;
import co.za.distance.util.DistanceConstants;
import localhost._8082.ws.distance.RouteRequest;
import localhost._8082.ws.distance.RouteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class DistanceEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8082/ws/distance";
    private Distance distance;
    private Planet earth;

    @Autowired
    public DistanceEndpoint(Distance distanceCalculator) {
        this.distance = distanceCalculator;
        earth = new Planet();
        earth.setPlanetNode(DistanceConstants.START_PLANET_NODE);
        earth.setPlanetName(DistanceConstants.START_PLANET_NAME);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RouteRequest")
    @ResponsePayload
    public RouteResponse getRoute(@RequestPayload RouteRequest request) {

        RouteResponse response = new RouteResponse();

        distance.init();
        distance.calculateShortDistance(earth);
        response.getShortestRoute().clear();
        response.getShortestRoute().addAll(distance.getShortDistRouteToPlanet(request.getNode()));
        return response;
    }
}
