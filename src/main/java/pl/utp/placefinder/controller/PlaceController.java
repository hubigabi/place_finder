package pl.utp.placefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }


    @GetMapping("")
    public List<Place> getPlaces(double lat, double lng, int radius, String type) {
        return placeService.makeRequest(lat, lng, radius, type);
    }

}
