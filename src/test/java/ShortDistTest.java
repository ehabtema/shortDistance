import co.za.distance.calculator.Distance;
import co.za.distance.persistence.Planet;
import co.za.distance.persistence.Route;
import co.za.distance.util.CsvReaderUtil;
import co.za.distance.util.DistanceConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Component
public class ShortDistTest {

    private static final String PLANET_ROOT = "http://localhost:8082/planets";
    private static final String ROUTE_ROOT = "http://localhost:8082/routes";
    private static final String DISTANCE_ROOT = "http://localhost:8082/distance";
    private List<Path> paths = new ArrayList<>();
    private Map<String, List<String>> columnNames = new HashMap<>();

    private Planet createPlanet(String node, String name) {
        Planet planet = new Planet();
        planet.setPlanetNode(node);
        planet.setPlanetName(name);
        return planet;
    }

    private Planet createPlanetAsUri(Planet planet) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(planet)
                .post(PLANET_ROOT);
        return RestAssured.get(PLANET_ROOT + "/" + response.jsonPath().get("id"))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getObject(".", Planet.class);
    }

    private String createRouteAsUri(Route route) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(route)
                .post(ROUTE_ROOT);
        return ROUTE_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void readCsvTest() {
        CsvReaderUtil csvReaderUtil = new CsvReaderUtil();
        csvReaderUtil.readCsvToDB();
    }

    @Test
    public void insertPlanetTest() {
        Planet planet = createPlanet("RR", "RRuerty");
        planet = createPlanetAsUri(planet);
        Response response = RestAssured.get(
                PLANET_ROOT + "/" + planet.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void getPlanetsTest() {
        Response response = RestAssured.get(
                PLANET_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        System.out.println("Planets: " + response.as(List.class).toString());
    }

    @Test
    public void getShortestRouteToPlanet() {
        Response response = RestAssured.get(DISTANCE_ROOT + "/G" );
        assertEquals(response.as(List.class).size(), 4);
        System.out.println("Shortest route to planet node G is: " + response.as(List.class).toString());
    }

    @Test
    public void testInit() {
        Distance distance = new Distance();
        distance.init();
        assertEquals(distance.getPlanets().size(), 38);
        distance.getPlanets().forEach(planet -> {
            Set<String> adjacentOfPlanet = distance.getAdjacentPlanets(planet);
            System.out.println(String.format("Adjacent of planet %s are %s", planet.getPlanetName(), adjacentOfPlanet.toString()));
           assertEquals(adjacentOfPlanet.size(), 3);
        });
    }

    @Test
    public void testShortestUnvisited() {
        Distance distance = new Distance();
        assertEquals("A", distance.getUnvisitedPlanetWithShortestDist().getPlanetNode());
    }

    @Test
    public void testGetAdjacent() {
        Planet planet = new Planet();
    }

    @Test
    public void testCalculateShortDist() {
        Distance distance = new Distance();
        distance.init();
        Planet earth = new Planet();
        earth.setPlanetNode("A");
        earth.setPlanetName("Earth");
        distance.calculateShortDistance(earth);
        distance.getShortDistRouteToPlanet("V");
    }

    /**
     * Read from CSV file to the database.
     *
     * @param path        the path
     * @param charsetName the charset name
     */
    public void read(Path path, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        String tableName = getTableName(path);

        try {
            List<String> lines = Files.readAllLines(path, charset);
            for (int i = 1; i < lines.size(); i++) {
                insertRow(tableName, lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTableName(Path path) {
        return path.getFileName().toString();
    }

    private void insertRow(String tableName, String line) {
        String[] values = line.split(",");
        switch (tableName) {
            case "planet.csv": {
                Planet planet = new Planet();
                planet.setPlanetNode(values[0]);
                planet.setPlanetName(values[1]);
                createPlanetAsUri(planet);
            }
            break;
            case "route.csv": {
                Route route = new Route();
                route.setRouteId(Integer.parseInt(values[0]));
                route.setOrigin(values[1]);
                route.setDestination(values[2]);
                route.setDistance(Double.parseDouble(values[3]));
                createRouteAsUri(route);
            }
            break;
            case "traffic.csv": {
                //TODO - Implementation coming soon
            }
            break;
            default: {
                System.out.println("invalid input ...");
            }
        }
    }
}