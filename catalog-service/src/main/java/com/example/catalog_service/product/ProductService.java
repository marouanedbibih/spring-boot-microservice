package com.example.catalog_service.product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.catalog_service.basics.BasicException;
import com.example.catalog_service.basics.BasicResponse;
import com.example.catalog_service.basics.BasicValidation;
import com.example.catalog_service.basics.MessageType;
import com.example.catalog_service.category.Category;
import com.example.catalog_service.category.CategoryRepository;
import com.example.catalog_service.product.https.ProductRequest;

import jakarta.validation.Valid;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Create a new product
    public BasicResponse createProduct(@Valid ProductRequest request, BindingResult bindingResult)
            throws BasicException {
        // Validate the product
        Map<String, String> errors = BasicValidation.getValidationsErrors(bindingResult);
        if (!errors.isEmpty()) {
            throw new BasicException(BasicResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Invalid fields")
                    .messageType(MessageType.ERROR)
                    .data(errors)
                    .build());
        }
        // Check if the product already exists
        if (productRepository.existsByName(request.getName())) {
            errors.put("name", "Product already exists");
            throw new BasicException(BasicResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Product already exists")
                    .data(errors)
                    .messageType(MessageType.ERROR)
                    .build());
        }
        // Check if the category exists
        if (categoryRepository.existsById(request.getCategoryId())) {
            errors.put("category", "Category not found");
            throw new BasicException(BasicResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Category not found")
                    .data(errors)
                    .messageType(MessageType.ERROR)
                    .build());

        }
        // Find the category
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        // Build the product Entity
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .price(request.getPrice())
                .category(category)
                .build();
        // Save the product
        productRepository.save(product);
        // return response
        return BasicResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Product created successfully")
                .messageType(MessageType.SUCCESS)
                .data(buildProductDTO(product))
                .build();

    }
    // Update an existing product
    // Delete an existing product
    // Find a product by its ID
    // Find all products with pagination
    // Find all products by category with pagination
    // Search for products by name

    // Build DTO to Entity
    public Product buildProduct(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .image(productDTO.getImage())
                .price(productDTO.getPrice())
                .category(productDTO.getCategory())
                .build();
    }

    // Build Entity to DTO
    public ProductDTO buildProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .category(product.getCategory())
                .build();
    }

}
