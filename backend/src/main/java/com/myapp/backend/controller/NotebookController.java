package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.notebook.NoteBookCreateDto;
import com.myapp.backend.domain.dto.notebook.NoteBookDetailDto;
import com.myapp.backend.domain.dto.notebook.NoteBookListDto;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    NotebookService notebookService;

    //신규 알림장 작성
    @PostMapping
    public ResponseEntity createNotebook(@RequestParam(required = false) MultipartFile img, @ModelAttribute NoteBookCreateDto noteBookDto){
        return notebookService.createNotebook(img, noteBookDto);

    }

    //학부모 ID(전달자)로 알림장 조회
    @GetMapping("/parents")
    public ResponseEntity<List<NoteBookListDto>> getParentsNotebook(@RequestParam String userId){
        return notebookService.getListNotebook(userId,1);
    }

    //선생님 ID(작성자)로 알림장 조회
    @GetMapping("/teacher")
    public ResponseEntity<List<NoteBookListDto>> getTeacherNotebook(@RequestParam String userId){
        return notebookService.getListNotebook(userId,2);
    }

    //선생님 ID로 같은반 학부모들 조회
    @GetMapping("/list")
    public ResponseEntity<List<User>> getParentsList(@RequestParam String userId){
        return notebookService.getParentsList(userId);
    }

    //글 ID로 상세내용 조회
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteBookDetailDto> getDetailNotebook(@PathVariable int noteId){
        return notebookService.getDetailNotebook(noteId);
    }

    //글 ID로 수정
    @PostMapping("/{noteId}")
    public ResponseEntity updateNotebook(@RequestParam(required = false) MultipartFile img, @PathVariable int noteId, @ModelAttribute NoteBookCreateDto noteBookDto){
        noteBookDto.setNoteId(noteId);
        return notebookService.updateNotebook(img, noteBookDto);
    }

    //글 ID로 삭제
    @DeleteMapping
    public ResponseEntity deleteNotebook(@RequestParam int noteId){
        return notebookService.deleteNotebook(noteId);
    }



}
