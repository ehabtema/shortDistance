package co.za.distance.error;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException() {}
    public RouteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
