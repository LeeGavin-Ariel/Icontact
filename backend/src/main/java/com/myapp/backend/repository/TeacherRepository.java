package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.TeacherEntity;
import com.myapp.backend.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, String> {


}