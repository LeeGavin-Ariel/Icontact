package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Attend;
import com.myapp.backend.domain.entity.key.AttendKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendRepository extends JpaRepository<Attend, AttendKey> {

    Attend findByUserIdAndDate(String userId, Date date);

    List<Attend>findByUserId(String userId);

    List<Attend>findByUserIdAndDateGreaterThanAndDateLessThan(String userId, Date start, Date end);
}
