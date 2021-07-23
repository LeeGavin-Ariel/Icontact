package com.myapp.backend.repository;

import com.myapp.backend.domain.login.TokenUpdateDto;
import com.myapp.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);
    void setRefreshToken(TokenUpdateDto tokenUpdateDto);
    String getRefreshToken(String userId);
}
