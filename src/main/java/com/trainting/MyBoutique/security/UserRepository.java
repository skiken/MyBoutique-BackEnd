package com.trainting.MyBoutique.security;

import org.springframework.stereotype.Repository;

import com.trainting.MyBoutique.persistence.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	 Optional< UserEntity> findByUsername(String username);
}
