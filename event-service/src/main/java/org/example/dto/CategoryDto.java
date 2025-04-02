package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private final Long id;
    private final String slug;
    private final String name;
}
