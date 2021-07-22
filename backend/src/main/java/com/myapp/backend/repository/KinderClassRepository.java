package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.KinderClassEntity;
import com.myapp.backend.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KinderClassRepository extends JpaRepository<KinderClassEntity, String> {


    List<KinderClassEntity> findByKinderCode(String kinderCode);

}
