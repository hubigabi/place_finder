package pl.utp.placefinder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @JsonProperty("height")
    Integer height;

    @JsonProperty("html_attributions")
    String[] html_attributions;

    @JsonProperty("photo_reference")
    String photo_reference;

    @JsonProperty("width")
    Integer width;
}
