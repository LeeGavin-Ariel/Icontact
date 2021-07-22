package com.myapp.backend.service;


import com.myapp.backend.domain.dto.JoinDto;
import com.myapp.backend.domain.entity.KidEntity;
import com.myapp.backend.domain.entity.KinderClassEntity;
import com.myapp.backend.domain.entity.TeacherEntity;
import com.myapp.backend.domain.entity.UserEntity;
import com.myapp.backend.repository.KidRepository;
import com.myapp.backend.repository.KinderClassRepository;
import com.myapp.backend.repository.TeacherRepository;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class JoinServiceImpl implements JoinService {

    @Autowired
    KinderClassRepository kinderClassRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    KidRepository kidRepository;


    @Override
    public ResponseEntity join(@RequestBody JoinDto joinDto){

        //1. 공통 user 만들어서 save
        UserEntity user = new UserEntity(joinDto.getUserId(),
                joinDto.getPassword(),
                joinDto.getUserName(),
                joinDto.getUserTel(),
                joinDto.getType());

        if(userRepository.existsByUserId(user.getUserId())){
            //ID중복 체크
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        else {

            if(user.getType()==2){
                //2. 선생부분 만들어서 save
                TeacherEntity teacher = new TeacherEntity();
                teacher.setUserId(joinDto.getUserId());
                teacher.setClassCode(joinDto.getClassCode());

                if(userRepository.save(user)!=null && teacherRepository.save(teacher)!=null){
                    return new ResponseEntity(HttpStatus.OK);
                }

                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else if(user.getType()==1){
                //2. 학부모부분 만들어서 save
                KidEntity kid = new KidEntity();
                kid.setKidName(joinDto.getKidName());
                kid.setClassCode(joinDto.getClassCode());
                kid.setUserId(joinDto.getUserId());

                if(userRepository.save(user)!=null && kidRepository.save(kid)!=null){
                    return new ResponseEntity(HttpStatus.OK);
                }

                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

            }

        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<String>> classList(@RequestParam String kinderCode){
        //test url : http://localhost:8080/user/kinder/class?kinderCode=1

        //code로 전체 class 조회
        List<String> className=new ArrayList<>();
        List<KinderClassEntity>kinderClass = kinderClassRepository.findByKinderCode(kinderCode);

        //반이 검색되지 않은=존재하지 않는 유치원 코드 처리
        if(kinderClass==null || kinderClass.size()==0){
            return new ResponseEntity(className, HttpStatus.NOT_FOUND);
        }

        for (KinderClassEntity k: kinderClass) {
            className.add(k.getClassName());
        }

        return new ResponseEntity(className, HttpStatus.OK);
    }
}
