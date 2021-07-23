package com.myapp.backend.repository;

import com.myapp.backend.domain.user.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KidRepository extends JpaRepository<Kid, String> {

//    List<Kid> findAllByUserId(String userId);
}
