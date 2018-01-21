package com.example.demo.entity;

import lombok.Data;

@Data
public class LocationDto {

    long id;
    private String address;
    private String city;
    private String state;
    private String zipCode;
}
