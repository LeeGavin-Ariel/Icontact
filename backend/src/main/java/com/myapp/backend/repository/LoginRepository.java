package com.myapp.backend.repository;

import com.myapp.backend.domain.login.TokenUpdateDto;
import com.myapp.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);

    User findByUserIdAndPassword(String userId, String password);
}
