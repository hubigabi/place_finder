package pl.utp.placefinder.model;

import lombok.Data;

@Data
public class RequestFindPlaces {
    private String address;
    private Double lat;
    private Double lng;
    private Integer radius;
    private String type;

    public RequestFindPlaces(Double lat, Double lng, Integer radius, String type) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
        this.type = type;
    }
}
