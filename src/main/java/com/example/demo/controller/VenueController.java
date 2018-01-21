package com.example.demo.controller;

import com.example.demo.entity.LocationDto;
import com.example.demo.entity.VenueLocation;
import com.example.demo.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("/all")
    public List<VenueLocation> getAll() {
        return venueService.getAll();
    }

    @PostMapping("/isAvailable/{date}")
    public boolean isLocationAvailableOnDate(@RequestBody LocationDto location,
                                             @PathVariable String date) {
        return venueService.isLocationAvailableOnDate(date, location);
    }
}
