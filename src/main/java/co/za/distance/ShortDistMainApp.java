package co.za.distance;

import co.za.distance.calculator.Distance;
import co.za.distance.persistence.Planet;
import co.za.distance.util.CsvReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"co.za.distance"})
public class ShortDistMainApp {

//    @Autowired
    static Distance distance;
//    @Autowired
    static CsvReaderUtil readerUtil;

    public static void main(String[] args) {
        SpringApplication.run(ShortDistMainApp.class, args);
        readerUtil = new CsvReaderUtil();
        readerUtil.readCsvToDB();
        distance = new Distance();
        distance.init();
        Planet earth = new Planet();
        earth.setPlanetNode("A");
        earth.setPlanetName("Earth");
        distance.calculateShortDistance(earth);
    }

    /*@PostConstruct
    public void init() throws InterruptedException {
        readerUtil.readCsvToDB();
       *//* distance.init();
        Planet earth = new Planet();
        earth.setPlanetNode("A");
        earth.setPlanetName("Earth");
        distance.calculateShortDistance(earth);*//*
    }*/

}
