package pl.utp.placefinder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    @JsonProperty("lat")
    Double lat;

    @JsonProperty("lng")
    Double lng;

    @JsonProperty("icon")
    String icon;

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;

    @JsonProperty("open_now")
    Boolean open_now = false;

    @JsonProperty("photos")
    Photo[] photos;

    @JsonProperty("place_id")
    String place_id;

    @JsonProperty("rating")
    private Double rating = 0.0;

    @JsonProperty("reference")
    String reference;

    @JsonProperty("scope")
    String scope;

    @JsonProperty("types")
    String[] types;

    @JsonProperty("user_ratings_total")
    Integer user_ratings_total = 0;

    @JsonProperty("vicinity")
    String vicinity;


    //Nested values
    @JsonProperty("geometry")
    private void getFromGeometry(Map<String, Object> geometry) {
        Map<String, Double> location = (Map<String, Double>) geometry.get("location");
        lat = location.get("lat");
        lng = location.get("lng");
    }

    @JsonProperty("opening_hours")
    private void getFromOpening_hours(Map<String, String> opening_hours) {
        open_now = Boolean.parseBoolean(opening_hours.get("open_now"));
    }

}
