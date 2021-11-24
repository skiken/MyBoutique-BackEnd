package com.trainting.MyBoutique.repository;

import com.trainting.MyBoutique.persistence.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainting.MyBoutique.persistence.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product> findAllByCategory(Optional<Category> category);
    Boolean existsByName(String name);

}
