package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Returnhome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnhomeRepository extends JpaRepository<Returnhome, Integer> {

    Returnhome findByRhId(Integer rhId);

    void deleteByRhId(Integer rhId);

}
