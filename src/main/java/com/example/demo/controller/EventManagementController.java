package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventDto;
import com.example.demo.repository.ValidLocationRepository;
import com.example.demo.service.EventManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventManagementController {
    @Autowired
    ValidLocationRepository repository;

    @Autowired
    private EventManagementService eventManagementService;

    @GetMapping("/{id}")
    public Event get(@PathVariable Long id) {
        return eventManagementService.getById(id);
    }

    @PostMapping("/add")
    public Event save(@RequestBody @Valid EventDto event) {
        return eventManagementService.save(event);
    }

    @PutMapping("/update")
    public Event update(@RequestBody @Valid EventDto event) {
        return eventManagementService.update(event);
    }

    @GetMapping("/findByName/{name}")
    public Event findByName(@PathVariable String name) {
        return eventManagementService.findByName(name);
    }

    @GetMapping("/findByDate/{date}")
    public List<Event> findByDate(@PathVariable String date) {
        return eventManagementService.findByDate(date);
    }

    @GetMapping("/all")
    public List<Event> getAll() {
        return eventManagementService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        eventManagementService.delete(id);
    }
}
