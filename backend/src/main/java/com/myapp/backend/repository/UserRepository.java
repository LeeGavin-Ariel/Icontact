package com.myapp.backend.repository;

<<<<<<< HEAD
import com.myapp.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>  {
    User findByUserId(String userId);
    boolean deleteByUserId(String userId);
    List<User> findByClassCodeStartsWith(String code);
    boolean existsByUserId(String userId);
=======
import com.myapp.backend.domain.login.TokenUpdateDto;
import com.myapp.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);
>>>>>>> 7d9acd6d19de5ff401d4a1f9a90e178c6c527783
}

