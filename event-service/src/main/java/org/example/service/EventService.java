package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.ApiClient;
import org.example.dto.EventDto;
import org.example.repository.CategoryRepository;
import org.example.repository.LocationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {

    private final ApiClient apiClient;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Async
    public CompletableFuture<List<EventDto>> getEvents(BigDecimal budget, String currency, LocalDate dateFrom,
                                                       LocalDate dateTo, String category, String location) {
        categoryRepository.findBySlug(category).orElseThrow(
                () -> new EntityNotFoundException("Category with slug %s not found".formatted(category)));

        categoryRepository.findBySlug(category).orElseThrow(
                () -> new EntityNotFoundException("Category with slug %s not found".formatted(category)));

        List<EventDto> result = new ArrayList<>();

        CompletableFuture<Void> allDone = CompletableFuture
                .supplyAsync(() -> apiClient.getEvents(dateFrom, dateTo, category, location))
                .exceptionally(ex -> {
                    log.error("Error while getting events: {}", ex.getMessage());
                    return new EventDto[0];
                })
                .thenAcceptBoth(CompletableFuture
                                .supplyAsync(() -> apiClient.convertMoney(budget, currency))
                                .exceptionally(ex -> {
                                    log.error("Error while converting money: {}", ex.getMessage());
                                    return BigDecimal.ZERO;
                                }),
                        (events, convertedBudget) -> result.addAll(Arrays.stream(events)
                                .filter(event -> event.getPrice().compareTo(convertedBudget) <= 0)
                                .toList())
                );

        return allDone.thenApply(v -> new ArrayList<>(result));
    }
}
