package com.example.demo.service.impl;

import com.example.demo.entity.Event;
import com.example.demo.entity.LocationDto;
import com.example.demo.entity.ValidLocation;
import com.example.demo.entity.VenueLocation;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ValidLocationRepository;
import com.example.demo.repository.VenueLocationRepository;
import com.example.demo.service.VenueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VenueServiceImpl implements VenueService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueLocationRepository venueRepository;

    @Autowired
    ValidLocationRepository validLocationRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public List<VenueLocation> getAll() {
        return (List<VenueLocation>) venueRepository.findAll();
    }

    @Override
    public boolean isValidLocation(LocationDto location) {
        ValidLocation validLocation = validLocationRepository.findByAddressAndCityAndStateAndZipCode(location.getAddress(),
                location.getCity(), location.getState(), location.getZipCode());
        if (validLocation == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isLocationAvailableOnDate(String date, LocationDto location) {
        VenueLocation venueLocation = new VenueLocation();
        BeanUtils.copyProperties(location, venueLocation);
        List<Event> events = eventRepository.findByDate(date);
        log.info(events.toString());
        Optional<Event> optional = events.stream().filter(event -> event.getLocation().equals(venueLocation)).findFirst();
        if (!optional.isPresent()) {
            return true;
        }
        return false;
    }
}
