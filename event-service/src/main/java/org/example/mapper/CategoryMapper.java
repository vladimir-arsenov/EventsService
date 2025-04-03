package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryResponseDto;
import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final CategoryRepository categoryRepository;

    public Category toModel(CategoryResponseDto categoryDto) {
        return Category.builder()
                .slug(categoryDto.getSlug())
                .name(categoryDto.getName())
                .build();
    }

    public CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .slug(category.getSlug())
                .name(category.getName())
                .build();
    }
}
