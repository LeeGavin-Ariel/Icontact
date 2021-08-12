package com.myapp.backend.controller;

import com.myapp.backend.domain.dto.notebook.NoteBookCreateDto;
import com.myapp.backend.domain.dto.notebook.NoteBookDetailDto;
import com.myapp.backend.domain.dto.notebook.NoteBookListDto;
import com.myapp.backend.domain.dto.notebook.NoteBookListResultDto;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    NotebookService notebookService;

    //신규 알림장 작성
    @PostMapping
    public ResponseEntity createNotebook(@RequestBody NoteBookCreateDto noteBookDto){
        return notebookService.createNotebook(noteBookDto);

    }

    //학부모 ID(전달자)로 알림장 조회
    @GetMapping("/parents")
    public ResponseEntity<List<NoteBookListDto>> getParentsNotebook(@RequestParam int pageNum, @RequestParam String userId){
        return notebookService.getListNotebook(pageNum, userId,1);
    }

    //선생님 ID(작성자)로 알림장 조회
    @GetMapping("/teacher")
    public ResponseEntity<List<NoteBookListDto>> getTeacherNotebook(@RequestParam int pageNum, @RequestParam String userId){
        return notebookService.getListNotebook(pageNum, userId,2);
    }

    //이름으로 검색
    @GetMapping("/search")
    public ResponseEntity<NoteBookListResultDto> searchNotebook(@RequestParam int pageNum, @RequestParam String userId, @RequestParam String searchParam){
        NoteBookListResultDto result = notebookService.searchNotebook(pageNum, userId, searchParam);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity updateNotebook(@PathVariable int noteId, @RequestBody NoteBookCreateDto noteBookDto){
        noteBookDto.setNoteId(noteId);
        return notebookService.updateNotebook(noteBookDto);
    }

    //글 ID로 삭제
    @DeleteMapping
    public ResponseEntity deleteNotebook(@RequestParam int noteId){
        return notebookService.deleteNotebook(noteId);
    }



}
