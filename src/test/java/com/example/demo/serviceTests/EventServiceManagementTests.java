package com.example.demo.serviceTests;

import com.example.demo.entity.Event;
import com.example.demo.entity.ValidLocation;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ValidLocationRepository;
import com.example.demo.repository.VenueLocationRepository;
import com.example.demo.service.EventManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;

import static com.example.demo.testUtils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EventServiceManagementTests {

    @Autowired
    private EventManagementService eventManagementService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueLocationRepository venueRepository;

    @Autowired
    private ValidLocationRepository validLocationRepository;

    @Before
    public void init() {
        eventRepository.deleteAll();
        venueRepository.deleteAll();
    }

    @Test
    public void getByIdTest() {
        Event event = getEvent();
        long id = eventRepository.save(event).getId();
        Event savedEvent = eventManagementService.getById(id);

        assertEquals("theName", savedEvent.getName());
    }

    @Test
    public void saveEventTest() {
        Event event = getEvent();
        long id = eventRepository.save(event).getId();
        event.setId(id);
        Event savedEvent = eventManagementService.getById(id);

        assertEquals(savedEvent, event);
    }

    @Test
    public void updateEventTest() {
        ValidLocation location = getSecondValidLocation();
        validLocationRepository.save(location);
        Event event = getEvent();
        Event savedEvent = eventRepository.save(event);
        savedEvent.setDate("01-23-2018");
        eventManagementService.update(toEventDto(savedEvent));
        assertEquals("01-23-2018", eventManagementService.getById(savedEvent.getId()).getDate());
    }

    @Test
    public void deleteEventTest() {
        Event event = getEvent();
        Event savedEvent = eventRepository.save(event);
        eventManagementService.delete(savedEvent.getId());
        assertTrue(eventManagementService.getById(savedEvent.getId()) == null);
    }

    @Test
    public void findByNameTest() {
        Event event = getEvent();
        eventRepository.save(event);
        assertEquals("theName", eventManagementService.findByName(event.getName()).getName());
    }

    @Test
    public void findByDateTest() {
        Event event = getEvent();
        eventRepository.save(event);
        assertEquals("01-22-2018", eventManagementService.findByDate(event.getDate()).get(0).getDate());
    }

    @Test
    public void getAllEventTest() {
        Event event1 = getEvent();
        Event event2 = getEvent();
        event2.setDate("02-22-2018");
        eventRepository.save(event1);
        eventRepository.save(event2);
        assertTrue(eventManagementService.getAll().size() == 2);
    }

    @Test(expected = ValidationException.class)
    public void negativeTestForUpdateEventWithNotAvailableLocationForTheSpecificDate() {
        Event event = getEvent();
        Event savedEvent = eventRepository.save(event);
        eventManagementService.update(toEventDtoWithId(savedEvent));
    }
}
