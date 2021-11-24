package com.trainting.MyBoutique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainting.MyBoutique.persistence.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

}
