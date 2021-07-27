package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.KinderClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KinderRepository extends JpaRepository<KinderClass, String> {

    KinderClass findByClassCode(String classCode);
}
