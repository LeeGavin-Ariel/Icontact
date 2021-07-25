package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Kid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidRepository extends JpaRepository<Kid, String> {


}