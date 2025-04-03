package org.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ExecutorsConfig {
    
    @Bean("category-location-initializer-fixed-executor")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(2);
    }

    @Bean("category-location-initializer-scheduled-executor")
    public ScheduledExecutorService scheduledThreadPool() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
