package pl.utp.placefinder.mapper;

import org.springframework.stereotype.Component;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.model.PlaceDTO;

@Component
public class PlaceMapper {

    public PlaceDTO convertToPlaceDTO(Place place) {
        return new PlaceDTO(place.getLat(), place.getLng(), place.getName(), place.getRating(), place.getUser_ratings_total(), place.getOpen_now());
    }

}
