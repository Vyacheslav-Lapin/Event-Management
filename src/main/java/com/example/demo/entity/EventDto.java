package com.example.demo.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventDto {

    private long id;
    private @NotNull String name;
    private @NotNull String date;
    private @NotNull LocationDto location;
}
