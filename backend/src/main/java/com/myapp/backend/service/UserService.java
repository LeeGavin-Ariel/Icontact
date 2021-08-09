package com.myapp.backend.service;

import com.myapp.backend.domain.dto.mypage.ChangePasswordDto;
import com.myapp.backend.domain.dto.mypage.MyPageResultDto;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.KinderRepository;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    KinderRepository kinderRepository;

    private final int WAIT = 1;
    private final int APPROVE = 2;
    private final int REJECT = 3;
    private final int REMOVE = 4;

    private final int SUCCESS = 1;
    private final int FAIL = -1;


    // 유저 조회
    public MyPageResultDto retrieveUser(String userId) {
        MyPageResultDto myPageResultDto = new MyPageResultDto();
        User findUser = userRepository.findByUserId(userId);
        myPageResultDto.userInfoSet(findUser);
        myPageResultDto.kinderClassInfoSet(kinderRepository.findByClassCode(findUser.getClassCode()));

        return myPageResultDto;
    }

    //유저 승인
    public boolean approveUser(String userId) {
        User user = userRepository.findByUserId(userId);

        user.setAccept(APPROVE);
        userRepository.save(user);
        return true;
    }

    //유저 거절
    public boolean rejectUser(String userId) {
        User user = userRepository.findByUserId(userId);

        user.setAccept(REJECT);
        userRepository.save(user);
        return true;
    }

    //유저 삭제
    public boolean removeUser(String userId) {
        User user = userRepository.findByUserId(userId);

        user.setAccept(REMOVE);
        userRepository.save(user);
        return true;
    }

    //전체 유저 조회
    public List<User> retrieveAllUser(String kinderCode) {
        List<User> userlist = userRepository.findByClassCodeStartsWith(kinderCode);

        return userlist;
    }


    public int updateProfileImg(String photoKey, String userId) throws IOException {
       User user = userRepository.findByUserId(userId);

        System.out.println(photoKey + " " + userId);
       if(user != null && photoKey != null && !photoKey.equals("")){
           user.setProfileImg(photoKey);
           userRepository.save(user);
           return SUCCESS;
       }
       return FAIL;
    }

    public boolean updatePassword(ChangePasswordDto changePasswordDto) {
        User findUser = userRepository.findByUserId(changePasswordDto.getUserId());
        findUser.setPassword(changePasswordDto.getPassword());
        userRepository.save(findUser);
        return true;
    }

    //우리반 유저 조회
    public List<User> retrieveAllClassUser(String classCode) {
        List<User> userlist = userRepository.findByClassCode(classCode);
        return userlist;
    }
}
