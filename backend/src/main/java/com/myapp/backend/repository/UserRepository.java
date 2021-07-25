package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


    boolean existsByUserId(String userId);

}
