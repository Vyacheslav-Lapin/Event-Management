package com.example.demo.repository;

import com.example.demo.entity.ValidLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidLocationRepository extends CrudRepository<ValidLocation, Long> {
    ValidLocation findByAddressAndCityAndStateAndZipCode(String address, String city, String state, String zipCode);
}
