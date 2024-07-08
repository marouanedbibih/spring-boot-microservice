package com.example.catalog_service.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mockito.MockitoAnnotations;

import com.example.catalog_service.basics.BasicException;
import com.example.catalog_service.basics.BasicResponse;
import com.example.catalog_service.basics.BasicValidation;
import com.example.catalog_service.basics.MessageType;
import com.example.catalog_service.category.https.CategoryRequest;

import java.util.List;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private BindingResult bindingResult;

    private Category category;
    private CategoryRequest categoryRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        category = new Category("1", "Electronics", "All electronic items");
        categoryRequest = new CategoryRequest("Electronics", "All electronic items");
    }



    // Create a test for the getCategoriesByPagination method that checks if the method returns a BasicResponse with a status of OK and a non-empty data field.
    @Test
    public void testCreateCategory() throws BasicException {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        BasicResponse response = categoryService.createCategory(categoryRequest, bindingResult);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.getData());

        
    }

    // Create a test for the updateCategory method that checks if the method returns a BasicResponse with a status of OK and a non-empty data field.
    @Test
    public void testUpdateCategory() throws BasicException {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        BasicResponse response = categoryService.updateCategory("1", categoryRequest, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.getData());
    }

    // Create a test for the deleteCategory method that checks if the method returns a BasicResponse with a status of OK.
    @Test
    public void testDeleteCategory() throws BasicException {
        doNothing().when(categoryRepository).deleteById(anyString());
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        BasicResponse response = categoryService.deleteCategory("1");
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    // Create a test for the getCategoriesByPagination method that checks if the method returns a BasicResponse with a status of OK and a non-empty data field.
    @Test
    public void testGetCategoriesByPagination() {
        Page<Category> page = new PageImpl<>(Collections.singletonList(category));
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(page);
        BasicResponse response = categoryService.getCategoriesByPagination(0, 1);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertFalse(((Page<Category>)response.getData()).getContent().isEmpty());
    }

    // Create a test for the getCategoryById method that checks if the method returns a BasicResponse with a status of OK and a non-empty data field.
    @Test
    public void testGetCategoryById() throws BasicException {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        BasicResponse response = categoryService.getCategoryById("1");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.getData());
    }
}
