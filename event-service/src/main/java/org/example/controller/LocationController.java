package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LocationResponseDto;
import org.example.service.LocationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/locations")
@Validated
public class LocationController {

    private final LocationService locationService;


    @GetMapping("")
    public List<LocationResponseDto> getAll() {
        return locationService.getAll();
    }

    @GetMapping("/{id}")
    public LocationResponseDto get(@PathVariable Long id) {
        return locationService.get(id);
    }
}
