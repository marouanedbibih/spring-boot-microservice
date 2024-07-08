package com.example.catalog_service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalog_service.basics.BasicResponse;
import com.example.catalog_service.basics.MessageType;
import com.example.catalog_service.category.https.CategoryRequest;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/catalog-api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Save Category api
    @PostMapping("/")
    public ResponseEntity<BasicResponse> saveCategoryApi(@RequestBody @Valid CategoryRequest categoryRequest, BindingResult bindingResult) {
        try {
            BasicResponse response = categoryService.createCategory(categoryRequest, bindingResult);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse.builder()
                .data(null)
                .message(e.getMessage())
                .messageType(MessageType.ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
        }
    }

    //Update Category api
    @PutMapping("/{id}")
    public ResponseEntity<BasicResponse> updateCategoryApi(@PathVariable String id, @RequestBody CategoryRequest categoryRequest,BindingResult bindingResult) {
        try {
            BasicResponse response = categoryService.updateCategory(id, categoryRequest, bindingResult);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse.builder()
                .data(null)
                .message(e.getMessage())
                .messageType(MessageType.ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
        }
    }

    // Delete Category api
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> deleteCategoryApi(@PathVariable String id) {
        try {
            BasicResponse response = categoryService.deleteCategory(id);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse.builder()
                .data(null)
                .message(e.getMessage())
                .messageType(MessageType.ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
        }
    }
    // Get Category by Id api
    @GetMapping("/{id}")
    public ResponseEntity<BasicResponse> getCategoryApi(@PathVariable String id) {
        try {
            BasicResponse response = categoryService.getCategoryById(id);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse.builder()
                .data(null)
                .message(e.getMessage())
                .messageType(MessageType.ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
        }
    }
    // Get All Categories api
    @GetMapping("/")
    public ResponseEntity<BasicResponse> getCategoriesApi(@PathParam(value = "page") int page, @PathParam(value = "size") int size) {
        try {
            BasicResponse response = categoryService.getCategoriesByPagination(page, size);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse.builder()
                .data(null)
                .message(e.getMessage())
                .messageType(MessageType.ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
        }
    }
    // Get All Products by Category Id api
    // Search Categories by Name api
    @GetMapping("/search")
    public ResponseEntity<BasicResponse> searchCategoriesApi(@PathParam(value = "name") String name , @PathParam(value = "page") int page, @PathParam(value = "size") int size) {
        try {
            BasicResponse response = categoryService.searchCategoriesByName(name, page, size);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse.builder()
                .data(null)
                .message(e.getMessage())
                .messageType(MessageType.ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
        }
    }


}
