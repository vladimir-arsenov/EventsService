package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.LocationDto;
import org.example.model.Location;
import org.example.repository.LocationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationMapper {

    private final LocationRepository locationRepository;

    public Location toModel(LocationDto locationDto) {
        return Location.builder()
                .slug(locationDto.getSlug())
                .name(locationDto.getName())
                .build();
    }

    public LocationDto toDto(Location location) {
        return LocationDto.builder()
                .slug(location.getSlug())
                .name(location.getName())
                .build();
    }
}
