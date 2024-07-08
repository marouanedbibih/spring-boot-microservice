package com.example.catalog_service.product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository  extends MongoRepository<Product, String>{
    Boolean existsByName(String name);
}
