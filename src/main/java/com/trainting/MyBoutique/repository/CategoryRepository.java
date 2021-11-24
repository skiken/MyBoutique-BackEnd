package com.trainting.MyBoutique.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainting.MyBoutique.persistence.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Boolean existsByName(String name);
    public Category findByName(String name);
}
