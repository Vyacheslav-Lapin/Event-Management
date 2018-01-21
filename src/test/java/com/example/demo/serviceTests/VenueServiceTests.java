package com.example.demo.serviceTests;

import com.example.demo.entity.ValidLocation;
import com.example.demo.entity.VenueLocation;
import com.example.demo.repository.ValidLocationRepository;
import com.example.demo.repository.VenueLocationRepository;
import com.example.demo.service.VenueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static com.example.demo.testUtils.TestUtils.getLocation;
import static com.example.demo.testUtils.TestUtils.toVenueLocation;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class VenueServiceTests {

    @Autowired
    private VenueLocationRepository venueRepository;

    @Autowired
    private VenueService venueService;

    @Autowired
    private ValidLocationRepository validLocationRepository;

    @Autowired
    EntityManager entityManager;

    @Before
    public void init() {
        venueRepository.deleteAll();
    }

    @Test
    public void getAllVenueLocationTest() {
        VenueLocation location1 = getLocation();
        VenueLocation location2 = getLocation();
        location2.setAddress("101 Ocean Blvd");
        venueRepository.save(location1);
        venueRepository.save(location2);
        assertTrue(venueService.getAll().size() == 2);
    }

    @Test
    public void isValidLocationTest() {
        ValidLocation location = new ValidLocation();
        location.setAddress("111 Main St");
        location.setCity("Santa Monica");
        location.setState("CA");
        location.setZipCode("90065");
        validLocationRepository.save(location);
        assertTrue(venueService.isValidLocation(toVenueLocation(getLocation())));
    }

    @Test
    public void isLocationAvailableOnDateTest() {
        VenueLocation location = getLocation();
        venueRepository.save(location);
        String date = "01/22/2018";
        assertTrue(venueService.isLocationAvailableOnDate(date, toVenueLocation(location)));
    }
}
