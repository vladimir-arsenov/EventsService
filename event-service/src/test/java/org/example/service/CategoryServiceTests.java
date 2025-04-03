package org.example.service;

import org.example.dto.CategoryResponseDto;
import org.example.mapper.CategoryMapper;
import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTests {

    private CategoryService service;
    private CategoryRepository repositoryMock;
    private CategoryMapper mapperMock;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(CategoryRepository.class);
        mapperMock = mock(CategoryMapper.class);
        service = new CategoryService(repositoryMock, mapperMock);
    }

    @Test
    public void getCategory_validId_shouldReturnCategory() {
        var category = new Category(1L, "", "");
        var categoryDto = new CategoryResponseDto("", "");
        when(repositoryMock.findById(category.getId())).thenReturn(Optional.of(category));
        when(mapperMock.toDto(any())).thenReturn(categoryDto);

        var result = service.get(category.getId());

        assertEquals(categoryDto, result);
    }

    @Test
    public void getCategory_invalidId_shouldThrowNoSuchElementException() {
        var categoryId = 1L;
        when(repositoryMock.findById(categoryId)).thenReturn(null);

        var result = assertThrows(NoSuchElementException.class, () -> service.get(categoryId));

        assertEquals("Category with id %d not found".formatted(categoryId), result.getMessage());
    }

    @Test
    public void getAllCategories_shouldReturnCategories() {
        var list = List.of(new Category(1L, "", ""), new Category(0L, "", ""));
        when(repositoryMock.findAll()).thenReturn(list);
        when(mapperMock.toDto(any())).thenReturn(new CategoryResponseDto("", ""));

        var result = service.getAll();

        assertEquals(List.of(new CategoryResponseDto("", ""), new CategoryResponseDto("", "")), result);
    }
}

