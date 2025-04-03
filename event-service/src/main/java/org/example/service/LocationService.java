package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.LocationResponseDto;
import org.example.mapper.LocationMapper;
import org.example.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public List<LocationResponseDto> getAll() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toDto)
                .toList();
    }

    public LocationResponseDto get(Long id) {
        return locationMapper.toDto(locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location with id %d not found".formatted(id))));
    }
}
