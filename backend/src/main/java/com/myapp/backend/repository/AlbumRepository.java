package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    Page<Album> findByClassCodeOrderByAlbumIdDesc(String classCode, Pageable pageable);

    Album findByAlbumId(int albumId);
}
