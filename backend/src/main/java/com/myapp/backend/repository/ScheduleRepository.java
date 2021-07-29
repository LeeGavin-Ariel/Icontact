package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findByScheduleId(int id);

    void deleteByScheduleId(int id);
}
