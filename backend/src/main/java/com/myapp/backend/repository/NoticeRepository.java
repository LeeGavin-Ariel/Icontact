package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    Notice findByNoticeId(int id);

    void deleteByNoticeId(int id);
}
