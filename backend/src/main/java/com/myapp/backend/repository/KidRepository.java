package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Kid;
import com.myapp.backend.domain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface KidRepository extends JpaRepository<Kid, Integer>  {
    Kid findByKidId(int kidId);
    Kid findByUserId(int userId);
    List<Kid> findByClassCodeStartsWith(String kinderCode);
}

