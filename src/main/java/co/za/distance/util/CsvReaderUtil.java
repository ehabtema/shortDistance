package co.za.distance.util;

import co.za.distance.persistence.Planet;
import co.za.distance.persistence.Route;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for loading CSV files into DB.
 */
@Component
public class CsvReaderUtil {
    Logger logger = LoggerFactory.getLogger(CsvReaderUtil.class);
    public void readCsvToDB() {
        logger.info("Read CSV files to DB starting...");
        List<Path> paths = new ArrayList<>();

        paths.add(Paths.get(DistanceConstants.PLANET_CSV));
        paths.add(Paths.get(DistanceConstants.ROUTE_CSV));
        paths.add(Paths.get(DistanceConstants.TRAFFIC_CSV));

        paths.forEach(path -> read(path));
        logger.info("Read CSV files to DB finished.");
    }


    /**
     * Reads CVS input files and stores the data in the in-memory Derby database
     *
     *  @param path        the path to the CSV file
     */
    public void read(Path path) {
        Charset charset = Charset.forName(DistanceConstants.CHARSET);
        String tableName = getTableName(path);
        try {
            // assumption: first line of each file contains the column names
            List<String> lines = Files.readAllLines(path, charset);
            for (int i = 1; i < lines.size(); i++) {
                insertRow(tableName, lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the table name from the CSV file name.
     *
     * @param path the path
     * @return the table name
     */
    private static String getTableName(Path path) {
        return path.getFileName().toString();
    }


    /**
     * Insert a row into the table.
     *
     * @param tableName the table name
     * @param line      the data line from the CSV file
     */
    private void insertRow(String tableName, String line) {
        String[] values = line.split(",");
        switch (tableName) {
            case "planet.csv": {
                Planet planet = new Planet();
                planet.setPlanetNode(values[0]);
                planet.setPlanetName(values[1]);
                createPlanet(planet);
            }
            break;
            case "route.csv" : {
                Route route = new Route();
                route.setRouteId(Integer.parseInt(values[0]));
                route.setOrigin(values[1]);
                route.setDestination(values[2]);
                route.setDistance(Double.parseDouble(values[3]));
                createRoute(route);
            }
            break;
            case "traffic.csv" : {
            }
            break;
            default: {
                System.out.println("invalid input ...");
            }
        }
    }

    private void createPlanet(Planet planet) {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(planet)
                .post(DistanceConstants.PLANET_ROOT);
    }

    private void createRoute(Route route) {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(route)
                .post(DistanceConstants.ROUTE_ROOT);
    }

}
