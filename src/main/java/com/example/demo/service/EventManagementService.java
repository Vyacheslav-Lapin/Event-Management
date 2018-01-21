package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventDto;

import java.util.List;

public interface EventManagementService {

    Event getById(long id);

    Event save(EventDto event);

    Event update(EventDto event);

    void delete(long id);

    Event findByName(String name);

    List<Event> findByDate(String date);

    List<Event> getAll();

}
