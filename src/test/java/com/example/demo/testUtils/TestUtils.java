package com.example.demo.testUtils;

import com.example.demo.entity.*;
import org.springframework.beans.BeanUtils;

public class TestUtils {

    public static LocationDto toVenueLocation(VenueLocation location) {
        LocationDto locationDto = new LocationDto();
        BeanUtils.copyProperties(location, locationDto);
        return locationDto;
    }

    public static EventDto toEventDto(Event event) {
        EventDto eventDto = new EventDto();
        BeanUtils.copyProperties(event, eventDto);
        LocationDto location = new LocationDto();
        location.setAddress(event.getLocation().getAddress());
        location.setCity(event.getLocation().getCity());
        location.setState(event.getLocation().getState());
        location.setZipCode(event.getLocation().getZipCode());
        eventDto.setLocation(location);
        return eventDto;
    }
        public static EventDto toEventDtoWithId(Event event) {
        EventDto eventDto = new EventDto();
        BeanUtils.copyProperties(event, eventDto);
        LocationDto location = new LocationDto();
        location.setAddress(event.getLocation().getAddress());
        location.setCity(event.getLocation().getCity());
        location.setZipCode(event.getLocation().getZipCode());
        location.setState(event.getLocation().getState());
        location.setId(event.getLocation().getId());
        eventDto.setLocation(location);
        return eventDto;
    }
    public static ValidLocation getValidLocation(){
        ValidLocation location = new ValidLocation();
        location.setAddress("2015 Hollywood Blvd");
        location.setCity("Los Angeles");
        location.setState("CA");
        location.setZipCode("90027");
        return location;
    }
    public static ValidLocation getSecondValidLocation(){
        ValidLocation location = new ValidLocation();
        location.setAddress("111 Main St");
        location.setCity("Santa Monica");
        location.setState("CA");
        location.setZipCode("90065");
        return location;
    }

    public static VenueLocation getLocation() {
        VenueLocation location = new VenueLocation();
        location.setAddress("111 Main St");
        location.setCity("Santa Monica");
        location.setState("CA");
        location.setZipCode("90065");
        return location;
    }

    public static Event getEvent() {
        Event event = new Event();
        event.setName("theName");
        event.setDate("01-22-2018");
        event.setLocation(getLocation());
        return event;
    }

    public static Event getEventWithLocation() {
        Event event = new Event();
        event.setName("theEventNameWithLocation");
        event.setDate("02-02-2018");
        event.setLocation(getLocation());
        return event;
    }
}
