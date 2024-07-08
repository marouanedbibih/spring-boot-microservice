package com.example.catalog_service.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.example.catalog_service.product.https.ProductRequest;

public class ProductServiceTest {
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    private ProductRequest productRequest;
    
    @BeforeEach
    void setUp() {
        productRequest = ProductRequest.builder()
            .name("product")
            .description("description")
            .image("image")
            .price(10.0)
            .categoryId("1")
            .build();
    }

    @Test
    void testCreateProduct() {
        
    }
}
