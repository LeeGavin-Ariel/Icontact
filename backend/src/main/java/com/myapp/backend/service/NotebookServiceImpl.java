package com.myapp.backend.service;

import com.myapp.backend.domain.dto.notebook.NoteBookCreateDto;
import com.myapp.backend.domain.dto.notebook.NoteBookDetailDto;
import com.myapp.backend.domain.dto.notebook.NoteBookListDto;
import com.myapp.backend.domain.entity.Notebook;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.NotebookRepository;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public ResponseEntity createNotebook(MultipartFile img, NoteBookCreateDto noteBookDto){
        try {
            //DB 저장
            Notebook notebook = new Notebook();
            notebook.setTitle(noteBookDto.getTitle());
            notebook.setContent(noteBookDto.getContent());
            notebook.setWriterId(noteBookDto.getWriterId());
            notebook.setTargetId(noteBookDto.getTargetId());

            notebookRepository.save(notebook);
            if(img!=null) {
                //이미지 존재시 저장
                
                //경로 재고민 필요
                Path dir = Paths.get("./nootbook").toAbsolutePath().normalize();

                Files.createDirectories(dir);

                //noteId를 이미지 이름으로 사용한다
                String fileName = StringUtils.cleanPath(
                        notebook.getNoteId()+img.getOriginalFilename().substring( img.getOriginalFilename().lastIndexOf(".") )
                );
                Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");

                Path targetPath = dir.resolve(fileName).normalize();

                img.transferTo(targetPath);

//                notebook.setNoteImgUrl(targetPath.toString());
                notebook.setNoteImgUrl("./nootbook/"+fileName);

                notebookRepository.save(notebook);
            }

            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<NoteBookListDto>> getListNotebook(String userId, int type){

        try {

            List<Notebook> notebooks =null;

            //type==1이면 부모입장, 수신자 고정/작성자 변경
            //type==2면 선생입장, 작성자 고정/수신자 변경
            if(type==1)notebooks=notebookRepository.findByTargetId(userId);
            else notebooks=notebookRepository.findByWriterId(userId);

            List<NoteBookListDto> result = new ArrayList<>();
            User target = null, writer = null;

            if(type==1)target = userRepositoryJPA.findByUserId(userId);
            else writer = userRepositoryJPA.findByUserId(userId);

            for (Notebook n : notebooks) {
                if(type==1)writer = userRepositoryJPA.findByUserId(n.getWriterId());
                else target = userRepositoryJPA.findByUserId(n.getTargetId());

                NoteBookListDto dto = new NoteBookListDto(n.getNoteId(),
                        n.getTitle(),
                        n.getWriterId(),
                        writer.getUserName(),
                        n.getTargetId(),
                        target.getUserName());


//                dto.setNoteId(n.getNoteId());
//                dto.setTitle(n.getTitle());
//                dto.setWriterId(n.getWriterId());
//                dto.setWriterName(writer.getUserName());
//                dto.setTargetId(n.getTargetId());
//                dto.setTargetName(target.getUserName());

                result.add(dto);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
            noteBookDetailDto.setNoteImgUrl(Paths.get(notebook.getNoteImgUrl()).toAbsolutePath().normalize().toString());

            System.out.println(noteBookDetailDto.getNoteImgUrl());
            return new ResponseEntity<>(noteBookDetailDto, HttpStatus.OK);


        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public ResponseEntity updateNotebook(MultipartFile img, NoteBookCreateDto noteBookDto){
        try {
            Notebook notebook = notebookRepository.getById(noteBookDto.getNoteId());
            notebook.setTitle(noteBookDto.getTitle());
            notebook.setContent(noteBookDto.getContent());
            notebook.setTargetId(noteBookDto.getTargetId());

            //기존 이미지 삭제
            String imgPath = notebook.getNoteImgUrl();
            if(!"".equals(imgPath)){
                File file = new File(imgPath);
                file.delete();
                notebook.setNoteImgUrl("");
            }
            
            if(img!=null) {
                //이미지 새로 저장
                Path dir = Paths.get("./nootbook").toAbsolutePath().normalize();

                Files.createDirectories(dir);

                String fileName = StringUtils.cleanPath(
                        notebook.getNoteId()+img.getOriginalFilename().substring( img.getOriginalFilename().lastIndexOf(".") )
                );

                Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");

                Path targetPath = dir.resolve(fileName).normalize();

                img.transferTo(targetPath);

//                notebook.setNoteImgUrl(targetPath.toString());
                notebook.setNoteImgUrl("./nootbook/"+fileName);
            }

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
