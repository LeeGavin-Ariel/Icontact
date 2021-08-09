package com.myapp.backend.service;

import com.myapp.backend.domain.dto.notebook.NoteBookCreateDto;
import com.myapp.backend.domain.dto.notebook.NoteBookDetailDto;
import com.myapp.backend.domain.dto.notebook.NoteBookListDto;
import com.myapp.backend.domain.dto.notebook.NoteBookListResultDto;
import com.myapp.backend.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


public interface NotebookService {

    public ResponseEntity createNotebook(NoteBookCreateDto noteBookDto);

    public ResponseEntity<List<NoteBookListDto>> getListNotebook(int pageNum, String userId, int type);

    public NoteBookListResultDto searchNotebook(int pageNum, String userId, String searchParam);

    public ResponseEntity<NoteBookDetailDto> getDetailNotebook(int noteId);

    public ResponseEntity updateNotebook(NoteBookCreateDto noteBookDto);

    public ResponseEntity deleteNotebook(int noteId);


    ResponseEntity<List<User>> getParentsList(String userId);
}
