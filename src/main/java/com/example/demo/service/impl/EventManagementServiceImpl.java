package com.example.demo.service.impl;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventDto;
import com.example.demo.entity.LocationDto;
import com.example.demo.entity.VenueLocation;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventManagementService;
import com.example.demo.service.VenueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
@Slf4j
public class EventManagementServiceImpl implements EventManagementService {

    @Autowired
    private EventRepository eventManagementRepository;

    @Autowired
    private VenueService venueService;

    @Override
    @Transactional
    public Event getById(long id) {
        return eventManagementRepository.findOne(id);
    }

    @Override
    @Transactional
    public Event save(EventDto eventDto) {
        validate(eventDto);
        return eventManagementRepository.save(toEvent(eventDto));
    }

    @Override
    @Transactional
    public Event update(EventDto eventDto) {
        validate(eventDto);
        return eventManagementRepository.save(toEvent(eventDto));
    }

    @Override
    @Transactional
    public void delete(long id) {
        eventManagementRepository.delete(id);
    }

    @Override
    @Transactional
    public Event findByName(String name) {
        return eventManagementRepository.findByName(name);
    }

    @Override
    @Transactional
    public List<Event> findByDate(String date) {
        return eventManagementRepository.findByDate(date);
    }

    @Override
    @Transactional
    public List<Event> getAll() {
        return (List<Event>) eventManagementRepository.findAll();
    }

    private void validate(EventDto event) {
        if(isValidLocation(event.getLocation()) && isLocationAvailableOnDate(event.getLocation(), event.getDate())) {
            return;
        }
        throw new ValidationException("Event validation failed.");
    }

    private boolean isLocationAvailableOnDate(LocationDto location, String date) {
        return venueService.isLocationAvailableOnDate(date, location);
    }

    private boolean isValidLocation(LocationDto location) {
        return venueService.isValidLocation(location);
    }

    private Event toEvent(EventDto eventDto) {
        Event event = new Event();
        BeanUtils.copyProperties(eventDto, event);
        VenueLocation location = new VenueLocation();
        location.setCity(eventDto.getLocation().getCity());
        location.setState(eventDto.getLocation().getState());
        location.setZipCode(eventDto.getLocation().getZipCode());
        location.setAddress(eventDto.getLocation().getAddress());
        event.setLocation(location);
        return event;
    }
}
