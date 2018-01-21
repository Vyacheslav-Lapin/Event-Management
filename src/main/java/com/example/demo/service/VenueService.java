package com.example.demo.service;

import com.example.demo.entity.LocationDto;
import com.example.demo.entity.VenueLocation;

import java.util.List;

public interface VenueService {

    List<VenueLocation> getAll();

    boolean isValidLocation(LocationDto location);

    boolean isLocationAvailableOnDate(String date, LocationDto location);

}
