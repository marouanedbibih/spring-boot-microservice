package com.example.catalog_service.category;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.catalog_service.basics.BasicException;
import com.example.catalog_service.basics.BasicResponse;
import com.example.catalog_service.basics.BasicValidation;
import com.example.catalog_service.basics.MessageType;
import com.example.catalog_service.category.https.CategoryRequest;

import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create a new category
    /*
     * This method is used to create a new category
     * 
     * @param request
     * 
     * @param bindingResult
     * 
     * @return BasicResponse
     * 
     * @throws BasicException
     */
    public BasicResponse createCategory(@Valid CategoryRequest request, BindingResult bindingResult)
            throws BasicException {
        // Validation the request
        Map<String, String> messagesList = BasicValidation.getValidationsErrors(bindingResult);
        if (!messagesList.isEmpty()) {
            throw new BasicException(BasicResponse.builder()
                    .data(null)
                    .message("Validation Error")
                    .messagesList(messagesList)
                    .messageType(MessageType.ERROR)
                    .status(HttpStatus.BAD_REQUEST)
                    .redirectUrl(null)
                    .build());
        }
        // check if the category already exists
        try {
            ifExistsByName(request.getName());
        } catch (BasicException e) {
            return e.getResponse();
        }

        // transform the request to Entity
        Category category = transformResponseDTO(request);
        // Save the category
        category = categoryRepository.save(category);
        // Return the response
        return BasicResponse.builder()
                .data(buildDTO(category))
                .message("Category created successfully")
                .messagesList(null)
                .messageType(MessageType.SUCCESS)
                .status(HttpStatus.CREATED)
                .redirectUrl("/categories")
                .build();

    }



    

    // Update the existing category
    /*
     * This method is used to update the existing category
     * 
     * @param id
     * 
     * @param request
     * 
     * @param bindingResult
     * 
     * @return BasicResponse
     * 
     * @throws BasicException
     */
    public BasicResponse updateCategory(String id, @Valid CategoryRequest request, BindingResult bindingResult)
            throws BasicException {
        // Validation the request
        Map<String, String> messagesList = BasicValidation.getValidationsErrors(bindingResult);
        if (!messagesList.isEmpty()) {
            throw new BasicException(BasicResponse.builder()
                    .data(null)
                    .message("Validation Error")
                    .messagesList(messagesList)
                    .messageType(MessageType.ERROR)
                    .status(HttpStatus.BAD_REQUEST)
                    .redirectUrl(null)
                    .build());
        }
        // Find the category by id
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new BasicException(BasicResponse.builder()
                    .data(null)
                    .message("Category not found")
                    .messagesList(messagesList)
                    .messageType(null)
                    .status(HttpStatus.NOT_FOUND)
                    .redirectUrl(null)
                    .build());
        }
        // Check if the category already exists
        try {
            ifExistsByName(request.getName());
        } catch (BasicException e) {
            return e.getResponse();
        }
        // Set new values
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        // Save the category
        category = categoryRepository.save(category);
        // Return the response
        return BasicResponse.builder()
                .data(buildDTO(category))
                .message("Category updated successfully")
                .messagesList(null)
                .messageType(MessageType.SUCCESS)
                .status(HttpStatus.OK)
                .redirectUrl("/categories")
                .build();
    }

    // Delete the existing category
    /*
     * This method is used to delete the existing category
     * 
     * @param id
     * 
     * @return BasicResponse
     * 
     * @throws BasicException
     */
    public BasicResponse deleteCategory(String id) throws BasicException {
        // Find the category by id
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new BasicException(BasicResponse.builder()
                    .data(null)
                    .message("Category not found")
                    .messagesList(null)
                    .messageType(null)
                    .status(HttpStatus.NOT_FOUND)
                    .redirectUrl(null)
                    .build());
        }
        // Delete the category
        categoryRepository.deleteById(id);
        // Return the response
        return BasicResponse.builder()
                .data(null)
                .message("Category deleted successfully")
                .messagesList(null)
                .messageType(MessageType.SUCCESS)
                .status(HttpStatus.OK)
                .redirectUrl("/categories")
                .build();
    }
    
    // Get all categories with pagination
    /*
     * This method is used to get all categories with pagination
     * 
     * @param page
     * @param size
     * @return BasicResponse
     */
    public BasicResponse getCategoriesByPagination(int page, int size) {
        // Créer une instance de Pageable avec le numéro de page et la taille de page
        Pageable pageable = PageRequest.of(page, size);
        // Utiliser le repository pour trouver les catégories avec pagination
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        // Construire la réponse avec les catégories trouvées
        return BasicResponse.builder()
                .data(categoryPage.getContent())
                .message("Categories fetched successfully")
                .messageType(MessageType.SUCCESS)
                .status(HttpStatus.OK)
                .build();
    } 
    // Get category by id
    /*
     * This method is used to get the category by id
     * 
     * @param id
     * 
     * @return BasicResponse
     * 
     * @throws BasicException
     */
    public BasicResponse getCategoryById(String id) throws BasicException {
        // Find the category by id
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new BasicException(BasicResponse.builder()
                    .data(null)
                    .message("Category not found")
                    .messagesList(null)
                    .messageType(null)
                    .status(HttpStatus.NOT_FOUND)
                    .redirectUrl(null)
                    .build());
        }
        // Return the response
        return BasicResponse.builder()
                .data(buildDTO(category))
                .message(null)
                .messagesList(null)
                .messageType(null)
                .status(HttpStatus.OK)
                .redirectUrl(null)
                .build();
    }

    // Search categories by name with pagination
    /*
     * This method is used to search categories by name with pagination
     * 
     * @param name
     * @param page
     * @param size
     * @return BasicResponse
     */
    public BasicResponse searchCategoriesByName(String term, int page, int size) {
        // Créer une instance de Pageable avec le numéro de page et la taille de page
        Pageable pageable = PageRequest.of(page, size);
        // Utiliser le repository pour trouver les catégories par nom avec pagination
        Page<Category> categoryPage = categoryRepository.findBySearchTerm(term, pageable);
        // Construire la réponse avec les catégories trouvées
        return BasicResponse.builder()
                .data(categoryPage.getContent())
                .message("Categories fetched successfully")
                .messageType(MessageType.SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }


    // Get all products of a category by pagination


    
    // Build Entity from DTO
    public Category buildEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
    }

    // Build DTO from Entity
    public CategoryDTO buildDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    // Transform Request DTO to Normal DTO
    public CategoryDTO transformRequestDTO(CategoryRequest request) {
        return CategoryDTO.builder()
                .id(null)
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    // Transfrom Request DTO to Entity
    public Category transformResponseDTO(CategoryRequest request) {
        return Category.builder()
                .id(null)
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    // Private method to check if the category already exists
    public Boolean ifExistsByName(String name) throws BasicException{
        Map<String, String> messagesList = new HashMap<>();
        if (categoryRepository.existsByName(name)) {
            messagesList.put("name", "Category already exists");
            throw new BasicException(BasicResponse.builder()
                    .data(null)
                    .message("Category already exists")
                    .messagesList(messagesList)
                    .messageType(MessageType.ERROR)
                    .status(HttpStatus.IM_USED)
                    .redirectUrl(null)
                    .build());
        }
        return false;
    }

}
