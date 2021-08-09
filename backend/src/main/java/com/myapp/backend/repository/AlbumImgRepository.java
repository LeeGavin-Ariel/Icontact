package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.AlbumImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumImgRepository extends JpaRepository<AlbumImg, String> {

    List<AlbumImg> findByAlbumId(int albumId);
    void deleteAllByAlbumId(int albumId);
}
