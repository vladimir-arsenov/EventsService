package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EventDto;
import org.example.service.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Tag(name = "Events", description = "Controller for working with events")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/events")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Get a list of events", description = "Retrieve events based on provided parameters")
    @GetMapping
    public List<EventDto> getEvents(
            @Parameter(description = "Budget amount", example = "47.50") @RequestParam BigDecimal budget,
            @Parameter(description = "Currency", example = "USD") @RequestParam String currency,
            @Parameter(description = "Start date", example = "2023-01-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @Parameter(description = "End date", example = "2024-01-01")@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @Parameter(description = "Category", example = "Танцы") @RequestParam(required = false) String category,
            @Parameter(description = "Location", example = "msk") @RequestParam(required = false) String location
    ) {
        try {
            return eventService.getEvents(budget, currency, dateFrom, dateTo, category, location).get();
        } catch (InterruptedException | ExecutionException e) {
            log.warn("Error getting events");
            throw new RuntimeException(e);
        }
    }
}

