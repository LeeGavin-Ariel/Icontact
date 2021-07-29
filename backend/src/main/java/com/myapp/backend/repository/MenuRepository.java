package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Menu findByMenuId(int id);

    void deleteByMenuId(int id);
}
