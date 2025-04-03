package org.example.configuration;

import lombok.extern.slf4j.Slf4j;
import org.example.client.ApiClient;
import org.example.executiontimeloggerstarter.LogExecutionTime;
import org.example.repository.CategoryRepository;
import org.example.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ApplicationStartUpListener {
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final ApiClient apiClient;
    private final ExecutorService fixedExecutor;
    private final ScheduledExecutorService scheduledExecutor;
    private final Duration schedulerInterval;

    public ApplicationStartUpListener(CategoryRepository categoryRepository, LocationRepository locationRepository,
                                      ApiClient apiClient,
                                      @Qualifier("category-location-initializer-fixed-executor") ExecutorService fixedExecutor,
                                      @Qualifier("category-location-initializer-scheduled-executor") ScheduledExecutorService scheduledExecutor,
                                      @Value("${application.executors.scheduler.interval}") Duration schedulerInterval
    ) {
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.apiClient = apiClient;
        this.fixedExecutor = fixedExecutor;
        this.scheduledExecutor = scheduledExecutor;
        this.schedulerInterval = schedulerInterval;
    }

    @LogExecutionTime
    @EventListener(ApplicationReadyEvent.class)
    public void initRepositories() {
        scheduledExecutor.scheduleWithFixedDelay(this::initialize, 0, schedulerInterval.getSeconds(), TimeUnit.SECONDS);
    }

    private void initialize() {

        Runnable categoryInitTask = () -> {
            log.info("Initializing category repository...");
            categoryRepository.saveAll(Arrays.asList(apiClient.getCategories()));
            log.info("Category repository initialized");
        };

        Runnable locationInitTask = () -> {
            log.info("Initializing location repository...");
            locationRepository.saveAll(List.of(apiClient.getLocations()));
            log.info("Location repository initialized");
        };

        try {
            var futures = List.of(fixedExecutor.submit(categoryInitTask), fixedExecutor.submit(locationInitTask));
            for(var future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while initializing repositories", e);
        }
    }
}
