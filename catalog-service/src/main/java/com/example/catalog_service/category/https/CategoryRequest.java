package com.example.catalog_service.category.https;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long")
    private String name;

    @NotBlank(message = "The description cannot be empty")
    @Size(min = 10, max = 500, message = "The description must be between 10 and 500 characters long")
    private String description;
}
