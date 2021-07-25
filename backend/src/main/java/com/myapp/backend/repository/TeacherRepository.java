package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Teacher;
import com.myapp.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TeacherRepository extends JpaRepository<Teacher, String>  {
    Teacher findByUserId(String userId);
    List<Teacher> findByClassCodeStartsWith(String kinderCode);
//    User find
}

