package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {


    boolean existsByUserId(String userId);

}
