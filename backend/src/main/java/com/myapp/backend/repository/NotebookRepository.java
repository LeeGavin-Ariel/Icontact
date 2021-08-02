package com.myapp.backend.repository;

import com.myapp.backend.domain.entity.KinderClass;
import com.myapp.backend.domain.entity.Notebook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotebookRepository extends JpaRepository<Notebook, Integer> {

    List<Notebook> findByWriterId(String writerId);
    List<Notebook> findByTargetId(String targetId);

//    List<Notebook> findByWriterIdOrderByNoteIdDesc(String writerId, Pageable pageable);
//    List<Notebook> findByTargetIdOrderByNoteIdDesc(String targetId, Pageable pageable);

    Page<Notebook> findByWriterIdOrderByNoteIdDesc(String writerId, Pageable pageable);
    Page<Notebook> findByTargetIdOrderByNoteIdDesc(String targetId, Pageable pageable);
    Notebook findByNoteId(int noteId);

}
