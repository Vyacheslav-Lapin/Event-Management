package com.example.demo.repository;

import com.example.demo.entity.Event;
import com.example.demo.entity.VenueLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    Event findByName(String name);

    List<Event> findByDate(String date);

    Event findByDateAndLocation(String date, VenueLocation location);

}
