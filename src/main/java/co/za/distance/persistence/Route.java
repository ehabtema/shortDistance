package co.za.distance.persistence;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Route {
    @Id
    private long routeId;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private Double distance;

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return routeId == route.routeId &&
                origin.equals(route.origin) &&
                destination.equals(route.destination) &&
                distance.equals(route.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, origin, destination, distance);
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance +
                '}';
    }
}
