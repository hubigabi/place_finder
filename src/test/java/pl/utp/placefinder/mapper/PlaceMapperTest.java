package pl.utp.placefinder.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.utp.placefinder.model.Photo;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.model.PlaceDTO;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PlaceMapperTest {

    @Autowired
    private PlaceMapper placeMapper;

    @Test
    void convertToPlaceDTO() {
        Double lat = 56.867;
        Double lng = 62.6543;
        String icon = "icon";
        String id = "id";
        String name = "name";
        Boolean openNow = true;
        Photo[] photos = {new Photo(), new Photo()};
        String place_id = "place_id";
        Double rating = 4.7;
        String reference = "reference";
        String scope = "scope";
        String[] types = {"type1", "type2", "type3"};
        Integer userRatingsTotal = 58;
        String address = "vicinity";


        Place place = new Place(lat, lng, icon, id, name, openNow, photos,
                place_id, rating, reference, scope, types, userRatingsTotal, address);

        PlaceDTO placeDTO = placeMapper.convertToPlaceDTO(place);

        assertEquals(lat, placeDTO.getLat());
        assertEquals(lng, placeDTO.getLng());
        assertEquals(rating, placeDTO.getRating());
        assertEquals(userRatingsTotal, placeDTO.getUserRatingsTotal());
        assertEquals(openNow, placeDTO.getOpenNow());
        assertEquals(address, placeDTO.getAddress());
    }
}