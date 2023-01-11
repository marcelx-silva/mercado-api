package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category,UUID> {
    Optional<Category> findByName(String name);
}
