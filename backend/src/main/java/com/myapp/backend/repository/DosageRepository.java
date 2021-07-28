package com.myapp.backend.repository;

import com.myapp.backend.domain.dto.request.DosageResultDto;
import com.myapp.backend.domain.entity.Dosage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface DosageRepository extends JpaRepository<Dosage, Integer> {

    Dosage findByDosageId(Integer dosageId);

    void deleteByDosageId(Integer dosageId);
}
