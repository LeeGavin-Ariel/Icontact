package com.myapp.backend.service;


import com.myapp.backend.domain.dto.join.JoinDto;
import com.myapp.backend.domain.entity.KinderClass;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.KinderClassRepository;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class JoinServiceImpl implements JoinService {

    @Autowired
    KinderClassRepository kinderClassRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public ResponseEntity join(@RequestBody JoinDto joinDto){
        //ID중복체크
        if(userRepository.existsByUserId(joinDto.getUserId())){
            //ID중복 체크
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        //1. 공통 user 만들어서 save

        User user = new User();
        user.setUserId(joinDto.getUserId());
        user.setPassword(joinDto.getPassword());
        user.setUserName(joinDto.getUserName());
        user.setUserTel(joinDto.getUserTel());
        user.setType(joinDto.getType());
        user.setClassCode(joinDto.getClassCode());

        if(user.getType()==2){
            //2. 선생부분 만들어서 save

            user.setClassCode(joinDto.getClassCode());
            if(userRepository.save(user)!=null){
                return new ResponseEntity(HttpStatus.OK);
            }

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else if(user.getType()==1){
            //2. 학부모부분 만들어서 save

            user.setKidName(joinDto.getKidName());
            if(userRepository.save(user)!=null){
                return new ResponseEntity(HttpStatus.OK);
            }

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<String[]>> classList(@RequestParam String kinderCode){
        //test url : http://localhost:8080/user/kinder/class?kinderCode=1

        //code로 전체 class 조회
        List<String[]> className=new ArrayList<>();
        List<KinderClass>kinderClass = kinderClassRepository.findByKinderCode(kinderCode);

        //반이 검색되지 않은=존재하지 않는 유치원 코드 처리
        if(kinderClass==null || kinderClass.size()==0){
            return new ResponseEntity(className, HttpStatus.NOT_FOUND);
        }

        for (KinderClass k: kinderClass) {
            className.add(new String[]{ k.getClassName(), k.getClassCode() } );
        }

        return new ResponseEntity(className, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity changePW(JoinDto joinDto) {

        try {
            User user = userRepository.getById(joinDto.getUserId());
            if(user==null){
                return new ResponseEntity(HttpStatus.NOT_FOUND);//404
            }

            user.setPassword(joinDto.getPassword());
            if(userRepository.save(user)!=null) {
                return new ResponseEntity(HttpStatus.OK);
            }
            else{
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);//500
            }
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);//500
        }

    }


}
