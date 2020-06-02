package pl.utp.placefinder.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.utp.placefinder.PlaceFinderApplication;
import pl.utp.placefinder.mapper.PlaceMapper;
import pl.utp.placefinder.model.Place;
import pl.utp.placefinder.model.PlaceDTO;
import pl.utp.placefinder.model.RequestFindPlaces;
import pl.utp.placefinder.service.PlaceService;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PlaceFinderApplication.class)
@AutoConfigureMockMvc
public class PlaceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceMapper placeMapper;

    @Test
    public void getPlaces() throws Exception {

        double lat = 53.121784;
        double lng = 18.00075;
        int radius = 1000;
        String type = "restaurant";

        List<Place> placeList = placeService.makeRequest(new RequestFindPlaces(lat, lng, radius, type));

        mvc.perform(MockMvcRequestBuilders.get("/api/place?lat=" + lat +
                "&lng=" + lng + "&radius=" + radius + "&type=" + type))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json;"))
                .andExpect(jsonPath("$.*", hasSize(placeList.size())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void findPlaces() throws Exception {
        double lat = 53.121784;
        double lng = 18.00075;
        int radius = 1000;
        String type = "restaurant";

        RequestFindPlaces requestFindPlaces = new RequestFindPlaces(lat, lng, radius, type);

        List<Place> placeListExpected = placeService.makeRequest(requestFindPlaces);

        ObjectMapper mapper = new ObjectMapper();
        String requestFindPlacesJSON = mapper.writeValueAsString(requestFindPlaces);
        System.out.println(requestFindPlacesJSON);

        mvc.perform(MockMvcRequestBuilders.post("/api/place")
                .contentType(APPLICATION_JSON)
                .content(requestFindPlacesJSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json;"))
                .andExpect(jsonPath("$.*", hasSize(placeListExpected.size())))
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    mapper.registerModule(new JavaTimeModule());
                    List<PlaceDTO> placesFromJson = mapper.readValue(json, new TypeReference<List<PlaceDTO>>() {
                    });

                    placesFromJson = placesFromJson.stream()
                            .sorted(Comparator.comparing(PlaceDTO::getName, Comparator.reverseOrder()))
                            .collect(Collectors.toList());

                    List<PlaceDTO> sortedDelegations = placeListExpected.stream()
                            .map(placeMapper::convertToPlaceDTO)
                            .sorted(Comparator.comparing(PlaceDTO::getName, Comparator.reverseOrder()))
                            .collect(Collectors.toList());

                    assertEquals(placesFromJson, sortedDelegations);
                })
                .andDo(MockMvcResultHandlers.print());
    }
}