package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.KinderClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KinderClassRepository extends JpaRepository<KinderClass, String> {


    List<KinderClass> findByKinderCode(String kinderCode);

}
