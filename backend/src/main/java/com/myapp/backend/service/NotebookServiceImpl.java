package com.myapp.backend.service;

import com.myapp.backend.dao.NotebookDao;
import com.myapp.backend.domain.dto.notebook.*;
import com.myapp.backend.domain.entity.Notebook;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.NotebookRepository;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotebookServiceImpl implements NotebookService{

    @Autowired
    private UserRepository userRepositoryJPA;

    @Autowired
    NotebookRepository notebookRepository;

    @Autowired
    NotebookDao notebookDao;

    @Override
    @Transactional
    public ResponseEntity createNotebook(NoteBookCreateDto noteBookDto){
        try {
            //DB 저장
            Notebook notebook = new Notebook();
            notebook.setTitle(noteBookDto.getTitle());
            notebook.setContent(noteBookDto.getContent());
            notebook.setWriterId(noteBookDto.getWriterId());
            notebook.setTargetId(noteBookDto.getTargetId());
            notebook.setNoteImgUrl(noteBookDto.getImg());

            notebookRepository.save(notebook);

            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<NoteBookListDto>> getListNotebook(int pageNum, String userId, int type){

        try {

            Page<Notebook> pages =null;
            List<Notebook> notebooks =null;

            //type==1이면 부모입장, 수신자 고정/작성자 변경
            //type==2면 선생입장, 작성자 고정/수신자 변경
            int pageCnt=4;
            Pageable page = PageRequest.of(pageNum, pageCnt);

            if(type==1){
                pages=notebookRepository.findByTargetIdOrderByNoteIdDesc(userId, page);
            }
            else{
                pages=notebookRepository.findByWriterIdOrderByNoteIdDesc(userId, page);
            }

            List<NoteBookListDto> result = new ArrayList<>();
            User target = null, writer = null;
            int totalCnt =  pages.getTotalPages();

            if(type==1)target = userRepositoryJPA.findByUserId(userId);
            else writer = userRepositoryJPA.findByUserId(userId);

            for (Notebook n : pages) {
                if(type==1)writer = userRepositoryJPA.findByUserId(n.getWriterId());
                else target = userRepositoryJPA.findByUserId(n.getTargetId());

                NoteBookListDto dto = new NoteBookListDto(n.getNoteId(),
                        n.getTitle(),
                        n.getWriterId(),
                        writer.getUserName(),
                        n.getTargetId(),
                        target.getUserName(),
                        n.getCreateDate(),
                        totalCnt);

                result.add(dto);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public NoteBookListResultDto searchNotebook(int pageNum, String userId, String searchParam) {

        int type = userRepositoryJPA.findByUserId(userId).getType();
        int pageCnt=4;

        NoteBookParamDto notebookParamDto = new NoteBookParamDto(userId,searchParam, type, pageCnt*pageNum,4);
        System.out.println(notebookParamDto);
        NoteBookListResultDto result =new NoteBookListResultDto();


        List<NoteBookListDto>searchResult = notebookDao.searchNotebook(notebookParamDto);
        System.out.println(searchResult);
        int totalNum=0;

        for (NoteBookListDto n : searchResult){
            String writerName = userRepositoryJPA.findByUserId(n.getWriterId()).getUserName();
            String targetName = userRepositoryJPA.findByUserId(n.getTargetId()).getUserName();
            n.setWriterName(writerName);
            n.setTargetName(targetName);
            totalNum=n.getTotalNum();
        }
        result.setPageCnt((int) Math.ceil((double)totalNum/pageCnt));
        result.setResult(searchResult);
        System.out.println(totalNum);
        return result;
    }



    @Override
    public ResponseEntity<NoteBookDetailDto> getDetailNotebook(int noteId){

        try {
            Notebook notebook = notebookRepository.findByNoteId(noteId);
            NoteBookDetailDto noteBookDetailDto = new NoteBookDetailDto();

            User target = userRepositoryJPA.findByUserId(notebook.getTargetId());

            //반환값만들기

            noteBookDetailDto.setNoteId(notebook.getNoteId());
            noteBookDetailDto.setTitle(notebook.getTitle());
            noteBookDetailDto.setContent(notebook.getContent());
            noteBookDetailDto.setCreateDate(notebook.getCreateDate());
            noteBookDetailDto.setWriterId(notebook.getWriterId());
            noteBookDetailDto.setWriterName(userRepositoryJPA.findByUserId(notebook.getWriterId()).getUserName());
            noteBookDetailDto.setTargetId(notebook.getTargetId());
            noteBookDetailDto.setTargetName(target.getUserName());
            noteBookDetailDto.setKidName(target.getKidName());
            noteBookDetailDto.setNoteImgUrl(notebook.getNoteImgUrl());

            System.out.println(noteBookDetailDto.getNoteImgUrl());
            return new ResponseEntity<>(noteBookDetailDto, HttpStatus.OK);


        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public ResponseEntity updateNotebook(NoteBookCreateDto noteBookDto){
        try {
            Notebook notebook = notebookRepository.getById(noteBookDto.getNoteId());
            System.out.println(noteBookDto);
            notebook.setTitle(noteBookDto.getTitle());
            notebook.setContent(noteBookDto.getContent());
            notebook.setTargetId(noteBookDto.getTargetId());
            notebook.setNoteImgUrl(noteBookDto.getImg());

            notebookRepository.save(notebook);

            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity deleteNotebook(int noteId){

        try {
            notebookRepository.deleteById(noteId);
            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<User>> getParentsList(String userId) {
        try {
            List<User> parents = new ArrayList<>();

            String classCode = userRepositoryJPA.findByUserId(userId).getClassCode();
            List<User> result = userRepositoryJPA.findByClassCodeStartsWith(classCode);

            for (User user : result){
                //선생님은 제외
                if(user.getType()==2)continue;

                //필요 정보만 선별해 반환
                User temp = new User();
                temp.setUserId(user.getUserId());
                temp.setUserName(user.getUserName());
                temp.setKidName(user.getKidName());
                parents.add(temp);
            }
            return new ResponseEntity(parents, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
