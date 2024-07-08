package com.example.catalog_service.category;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.catalog_service.product.ProductDTO;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    
    private String id;
    private String name;
    private String description;

    private List<ProductDTO> productList;
}
