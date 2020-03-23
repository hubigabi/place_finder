package pl.utp.placefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.model.PlaceDTO;
import pl.utp.placefinder.mapper.PlaceMapper;
import pl.utp.placefinder.model.RequestFindPlaces;
import pl.utp.placefinder.service.PlaceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/place")
@CrossOrigin
public class PlaceController {

    private PlaceService placeService;
    private PlaceMapper placeMapper;

    @Autowired
    public PlaceController(PlaceService placeService, PlaceMapper placeMapper) {
        this.placeService = placeService;
        this.placeMapper = placeMapper;
    }

    @GetMapping("")
    public List<Place> getPlaces(double lat, double lng, int radius, String type) {
        return placeService.makeRequest(new RequestFindPlaces(lat, lng, radius, type));
    }

    @PostMapping
    public List<PlaceDTO> findPlaces(@RequestBody RequestFindPlaces requestFindPlaces) {
        return placeService.makeRequest(requestFindPlaces)
                .stream()
                .map(placeMapper::convertToPlaceDTO)
                .collect(Collectors.toList());
    }
}
