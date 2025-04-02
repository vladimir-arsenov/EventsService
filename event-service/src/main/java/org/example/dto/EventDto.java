package org.example.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Long locationId;
}
