package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDto;
import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final CategoryRepository categoryRepository;

    public Category toModel(CategoryDto categoryDto) {
        return Category.builder()
                .slug(categoryDto.getSlug())
                .name(categoryDto.getName())
                .build();
    }

    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .slug(category.getSlug())
                .name(category.getName())
                .build();
    }
}
