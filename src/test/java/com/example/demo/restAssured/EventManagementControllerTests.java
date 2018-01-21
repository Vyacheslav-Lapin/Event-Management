package com.example.demo.restAssured;

import com.example.demo.entity.Event;
import com.example.demo.entity.VenueLocation;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ValidLocationRepository;
import com.example.demo.repository.VenueLocationRepository;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.example.demo.testUtils.TestUtils.*;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EventManagementControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueLocationRepository venueLocationRepository;

    @Autowired
    private ValidLocationRepository validLocationRepository;

    @Before
    public void init() {
        eventRepository.deleteAll();
        venueLocationRepository.deleteAll();
        RestAssured.port = port;
    }

    @Test
    public void eventManagementControllerGetEventByIdTest() {
        Event event = eventRepository.save(getEventWithLocation());
        given().pathParam("id", event.getId()).log().all()
                .when().log().all()
                .get("/events/{id}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void eventManagementControllerSaveTest() {
        validLocationRepository.save(getValidLocation());
        given().contentType("application/json")
                .body(toEventDto(getEventWithLocation())).log().all()
                .when().log().all()
                .post("/events/add")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void eventManagmentControllerUpdateTest() {
        Event event = eventRepository.save(getEvent());
        VenueLocation location = getLocation();
        event.setLocation(location);
        validLocationRepository.save(getSecondValidLocation());
        Event response = given().contentType("application/json")
                .body(toEventDto(event))
                .when()
                .put("/events/update")
                .as(Event.class);
        response.setName("new name");
        assertTrue(response.getName().equals("new name"));
    }

    @Test
    public void eventManagementControllerFindEventByNameTest() {
        Event event = eventRepository.save(getEvent());
        Event response = given().pathParam("name", event.getName())
                .when()
                .get("/events/findByName/{name}")
                .as(Event.class);
        assertTrue(response.getName().equals("theName"));
    }

    @Test
    public void eventManagementControllerFindEventByDateTest() {
        eventRepository.save(getEvent());
        eventRepository.save(getEvent());
        List response = given().pathParam("date", "01-22-2018")
                .when()
                .get("/events/findByDate/{date}").as(List.class);
        assertTrue(response.size() == 2);
    }

    @Test
    public void eventManagementControllerGetAllTest() {
        eventRepository.save(getEvent());
        eventRepository.save(getEvent());
        List response = given()
                .contentType("application/json")
                .when()
                .get("/events/all")
                .as(List.class);
        assertTrue(response.size() == 2);
    }

    @Test
    public void eventManagementControllerDeleteEventByIdTest() {
        Event event = eventRepository.save(getEvent());
        given().contentType("application/json")
                .pathParam("id", event.getId())
                .when()
                .delete("/events/delete/{id}");
        eventRepository.findOne(event.getId());
        assertTrue( eventRepository.findOne(event.getId()) == null);
    }
}
