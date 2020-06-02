package pl.utp.placefinder.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.model.RequestFindPlaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @Test
    public void makeRequest1() {
        double lat = 53.1373;
        double lng = 17.6011;
        int radius = 1000;
        String type = "restaurant";

        List<String> expectedResults = Arrays.asList("Arabeska", "Pico Bello Italiano", "Restauracja Hotel Ekspresja");

        RequestFindPlaces requestFindPlaces = new RequestFindPlaces(lat, lng, radius, type);

        List<Place> placeList = placeService.makeRequest(requestFindPlaces);

        List<String> placeNameListActual = placeList.stream()
                .map(Place::getName)
                .sorted(Comparator.comparing(s -> s))
                .collect(Collectors.toList());


        assertTrue(placeNameListActual.containsAll(expectedResults));
        assertTrue(placeNameListActual.size() > 5);
    }

    @Test
    public void makeRequest2() {
        double lat = 53.1373;
        double lng = 17.6011;
        int radius = 500;
        String type = "hair_care";

        List<String> expectedResults = Arrays.asList("Sztos Barber Shop", "Salon Fryzjerski Ewela");

        RequestFindPlaces requestFindPlaces = new RequestFindPlaces(lat, lng, radius, type);

        List<Place> placeList = placeService.makeRequest(requestFindPlaces);

        List<String> placeNameListActual = placeList.stream()
                .map(Place::getName)
                .sorted(Comparator.comparing(s -> s))
                .collect(Collectors.toList());

        System.out.println(placeNameListActual);

        assertTrue(placeNameListActual.containsAll(expectedResults));
        assertTrue(placeNameListActual.size() > 5);
    }
}