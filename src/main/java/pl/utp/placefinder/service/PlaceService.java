package pl.utp.placefinder.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.utp.placefinder.model.Place;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {

    private final RestTemplate restTemplate;

    private JsonFactory jsonFactory;
    private ObjectMapper objectMapper;

    @Value("${api.key1}")
    private String apiKey1;

    @Value("${api.key2}")
    private String apiKey2;

    public PlaceService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        jsonFactory = new JsonFactory();
        objectMapper = new ObjectMapper(jsonFactory);
    }

    public List<Place> makeRequest(double lat, double lng, int radius, String type) {

        List<Place> places = new ArrayList<>();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
                .queryParam("key", apiKey1)
                .queryParam("location", lat + "," + lng)
                .queryParam("radius", radius)
                .queryParam("type", type)
                .queryParam("language ", "pl");

        System.out.println("URI: " + builder.toUriString());

        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseString = response.getBody();

            try {
                if (objectMapper.readTree(responseString).get("status").asText().equals("OVER_QUERY_LIMIT")) {
                    System.err.println("OVER_QUERY_LIMIT");
                }

                JsonNode resultsArrayNode = objectMapper.readTree(responseString).get("results");

                if (resultsArrayNode.isArray()) {
                    for (JsonNode placeJsonNode : resultsArrayNode) {
                        Place place = objectMapper.treeToValue(placeJsonNode, Place.class);
                        places.add(place);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Status code is not OK!");
        }

        return places;
    }

}
