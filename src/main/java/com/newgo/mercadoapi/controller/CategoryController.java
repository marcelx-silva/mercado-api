package com.newgo.mercadoapi.controller;

import com.newgo.mercadoapi.domain.dto.category.CategoryDTO;
import com.newgo.mercadoapi.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/managed-categories")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> save(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @GetMapping("/managed-categories/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> getAllCategories() {
        return ResponseEntity
                .ok()
                .body(categoryService.findAll());
    }

    @GetMapping("/managed-categories/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> getCategoryById(@PathVariable("id") UUID uuid) {
        return ResponseEntity
                .ok()
                .body(categoryService.findById(uuid));
    }

    @PutMapping("/managed-categories/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> updateCategoryByIdPut(@PathVariable("id") UUID uuid, @RequestBody CategoryDTO categoryDTO) {
        Optional<CategoryDTO> categoryOptional = categoryService.findById(uuid);
        if (categoryOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exist");

        categoryDTO.setUuid(uuid);
        System.out.println(categoryDTO);
        categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(categoryService.findById(uuid));
    }

    @PatchMapping("/managed-categories/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> updateCategoryByIdPatch(@PathVariable("id") UUID uuid, @RequestBody CategoryDTO categoryDTO) {
        Optional<CategoryDTO> category = categoryService.findById(uuid);
        if (category.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exist");


        if (categoryDTO.getName()==null)
            categoryDTO.setName(category.get().getName());

        if (categoryDTO.getDescription()==null)
            categoryDTO.setDescription(category.get().getDescription());

        categoryDTO.setUuid(category.get().getUuid());
        categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(categoryService.findById(uuid));
    }

    @DeleteMapping("/managed-categories/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable("id") UUID uuid) {
        Optional<CategoryDTO> categoryOptional = categoryService.findById(uuid);
        if (categoryOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exist");

        categoryService.deleteById(uuid);
        return ResponseEntity.ok().body(categoryOptional);
    }
}
