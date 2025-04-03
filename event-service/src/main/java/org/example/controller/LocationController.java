package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LocationResponseDto;
import org.example.metrics.LocationEndpointCallCounter;
import org.example.service.LocationService;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/locations")
@Validated
public class LocationController {

    private final LocationService locationService;
    private final LocationEndpointCallCounter metric;


    @GetMapping
    public List<LocationResponseDto> getAll() {
        metric.increment();
        try(var requestId = MDC.putCloseable("requestId", UUID.randomUUID().toString());
            var requestMethod = MDC.putCloseable("requestMethod", "GET"))
        {
            return locationService.getAll();
        }
    }

    @GetMapping("/{id}")
    public LocationResponseDto get(@PathVariable Long id) {
        return locationService.get(id);
    }
}
