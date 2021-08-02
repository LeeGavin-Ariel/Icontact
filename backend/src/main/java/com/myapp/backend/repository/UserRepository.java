package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>  {
    User findByUserId(String userId);
    boolean deleteByUserId(String userId);
    List<User> findByClassCodeStartsWith(String code);
    List<User> findByClassCode(String classCode);
    boolean existsByUserId(String userId);
}

