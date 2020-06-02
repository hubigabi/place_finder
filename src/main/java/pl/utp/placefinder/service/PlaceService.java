package pl.utp.placefinder.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.model.RequestFindPlaces;
import rx.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {

    private final RestTemplate restTemplate;

    private JsonFactory jsonFactory;
    private ObjectMapper objectMapper;

    int counterApiKey = 0;

    @Value("${api.key1}")
    private String apiKey1;

    @Value("${api.key2}")
    private String apiKey2;

    @Autowired
    public PlaceService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        jsonFactory = new JsonFactory();
        objectMapper = new ObjectMapper(jsonFactory);
    }

    public List<Place> makeRequest(RequestFindPlaces requestFindPlaces) {

        List<Place> places = new ArrayList<>();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
                .queryParam("location", requestFindPlaces.getLat() + "," + requestFindPlaces.getLng())
                .queryParam("radius", requestFindPlaces.getRadius())
                .queryParam("type", requestFindPlaces.getType())
                .queryParam("language ", "pl");

        if (counterApiKey % 2 == 0) {
            builder.queryParam("key", apiKey1);
        } else {
            builder.queryParam("key", apiKey2);
        }
        counterApiKey++;

        System.out.println("URI: " + builder.toUriString());

        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        Observable.just(response).filter(res -> res.getStatusCode() == HttpStatus.OK).map(r -> r.getBody()).subscribe(
                s -> {
                    try {
                        if (objectMapper.readTree(s).get("status").asText().equals("OVER_QUERY_LIMIT")) {
                            System.err.println("OVER_QUERY_LIMIT");
                        }

                        JsonNode resultsArrayNode = objectMapper.readTree(s).get("results");

                        if (resultsArrayNode.isArray()) {
                            Observable.from(resultsArrayNode).toBlocking().subscribe(placeJsonNodee ->
                                    {
                                        try {
                                            Place place = objectMapper.treeToValue(placeJsonNodee, Place.class);
                                            places.add(place);
                                        } catch (JsonProcessingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            );

                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });

/*        if (response.getStatusCode() == HttpStatus.OK) {
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
        }*/

        return places;
    }

}
