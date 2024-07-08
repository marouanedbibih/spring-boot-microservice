package com.example.catalog_service.product;

import com.example.catalog_service.category.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private String image;
    private Double price;

    // @DBRef
    private Category category;
}
