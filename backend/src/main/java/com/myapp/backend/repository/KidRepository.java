package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.KidEntity;
import com.myapp.backend.domain.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidRepository extends JpaRepository<KidEntity, String> {


}