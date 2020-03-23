package pl.utp.placefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.model.RequestFindPlaces;
import pl.utp.placefinder.service.PlaceService;

import java.util.ArrayList;

@Controller
public class MapController {

    PlaceService placeService;

    @Autowired
    public MapController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/map")
    public ModelAndView getMap() {
        ModelAndView mav = new ModelAndView("map");
        mav.addObject("places", new ArrayList<Place>());
        mav.addObject("lat", 0);
        mav.addObject("lng", 0);
        return mav;
    }

    @GetMapping("/map/places")
    public ModelAndView getMap(double lat, double lng, int radius, String type) {
        ModelAndView mav = new ModelAndView("map");
        mav.addObject("places", placeService.makeRequest(new RequestFindPlaces(lat, lng, radius, type)));
        mav.addObject("lat", lat);
        mav.addObject("lng", lng);
        return mav;
    }
}
