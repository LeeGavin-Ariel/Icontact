package com.myapp.backend.repository;

<<<<<<< HEAD
import com.myapp.backend.domain.entity.User;
=======
import com.myapp.backend.domain.login.TokenUpdateDto;
import com.myapp.backend.domain.user.User;
>>>>>>> 7d9acd6d19de5ff401d4a1f9a90e178c6c527783
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);

    User findByUserIdAndPassword(String userId, String password);
}
