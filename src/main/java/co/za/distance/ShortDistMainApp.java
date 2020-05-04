package co.za.distance;

import co.za.distance.calculator.Distance;
import co.za.distance.persistence.Planet;
import co.za.distance.util.CsvReaderUtil;
import co.za.distance.util.DistanceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"co.za.distance"})
public class ShortDistMainApp {

    static Distance distance;
    static CsvReaderUtil readerUtil;

    public static void main(String[] args) {
        SpringApplication.run(ShortDistMainApp.class, args);
        readerUtil = new CsvReaderUtil();
        readerUtil.readCsvToDB();
        distance = new Distance();
        distance.init();
        Planet earth = new Planet();
        earth.setPlanetNode(DistanceConstants.START_PLANET_NODE);
        earth.setPlanetName(DistanceConstants.START_PLANET_NAME);
        distance.calculateShortDistance(earth);
    }

}
