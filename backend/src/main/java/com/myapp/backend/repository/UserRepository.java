package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>  {
    User findByUserId(String userId);
}

