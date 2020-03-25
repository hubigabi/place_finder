package pl.utp.placefinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private Double lat;
    private Double lng;
    private String name;
    private Double rating;
    private Integer userRatingsTotal;
    private Boolean openNow;
    private String address;
}
