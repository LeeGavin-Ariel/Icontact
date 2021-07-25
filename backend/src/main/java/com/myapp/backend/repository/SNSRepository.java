package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.Kid;
import com.myapp.backend.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SNSRepository extends JpaRepository<Post, Integer>  {
    List<Post> findByKidId(int kidId);
}

