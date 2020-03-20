package pl.utp.placefinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.utp.placefinder.model.Place;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class InitService {

    PlaceService placeService;

    @Autowired
    public InitService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostConstruct
    public void init() {
//        List<Place> places = placeService.makeRequest(53.1422, 17.6017, 2000, "restaurant");
//        places.forEach(System.out::println);
    }
}
