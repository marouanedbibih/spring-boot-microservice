package com.example.catalog_service.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Category findByName(String name);

    Boolean existsByName(String name);

    Page<Category> findByName(String name, Pageable pageable);

    @Query("{'$or': [{'name': {'$regex': ?0, '$options': 'i'}}, {'description': {'$regex': ?0, '$options': 'i'}}]}")
    Page<Category> findBySearchTerm(String searchTerm, Pageable pageable);

}
