package com.example.demo.restAssured;

import com.example.demo.entity.ValidLocation;
import com.example.demo.entity.VenueLocation;
import com.example.demo.repository.ValidLocationRepository;
import com.example.demo.repository.VenueLocationRepository;
import com.example.demo.service.VenueService;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.demo.testUtils.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class VenueControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private VenueLocationRepository venueLocationRepository;

    @Autowired
    private VenueService venueService;

    @Autowired
    private ValidLocationRepository validLocationRepository;

    @Before
    public void init() {
        venueLocationRepository.deleteAll();
        RestAssured.port = port;
    }

    @Test
    public void getAllVenueLocationTest() {
        VenueLocation location1 = getLocation();
        VenueLocation location2 = getLocation();
        location2.setAddress("101 Ocean Blvd");
        venueLocationRepository.save(location1);
        venueLocationRepository.save(location2);
        Assert.assertTrue(venueService.getAll().size() == 2);
    }

    @Test
    public void isValidLocationTest() {
        ValidLocation location = getSecondValidLocation();
        validLocationRepository.save(location);
        Assert.assertTrue(venueService.isValidLocation(toVenueLocation(getLocation())));
    }

    @Test
    public void isLocationAvailableOnDateTest() {
        VenueLocation location = getLocation();
        venueLocationRepository.save(location);
        String date = "01-22-2018";
        Assert.assertTrue(venueService.isLocationAvailableOnDate(date, toVenueLocation(location)));
    }
}
