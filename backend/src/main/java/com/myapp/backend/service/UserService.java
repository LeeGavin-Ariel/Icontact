package com.myapp.backend.service;

import com.myapp.backend.domain.dto.mypage.ChangeUserRequestDto;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepositoryJPA;

    private final int WAIT = 1;
    private final int APPROVE = 2;
    private final int REJECT = 3;
    private final int REMOVE = 4;


    // 유저 조회
    public User retrieveUser(String userId) {
        return userRepositoryJPA.findByUserId(userId);
    }

    //유저 승인
    public boolean approveUser(String userId) {
        User user = userRepositoryJPA.findByUserId(userId);

        user.setAccept(APPROVE);
        userRepositoryJPA.save(user);
        return true;
    }

    //유저 거절
    public boolean rejectUser(String userId) {
        User user = userRepositoryJPA.findByUserId(userId);

        user.setAccept(REJECT);
        userRepositoryJPA.save(user);
        return true;
    }

    //유저 삭제
    public boolean removeUser(String userId) {
        User user = userRepositoryJPA.findByUserId(userId);

        user.setAccept(REMOVE);
        userRepositoryJPA.save(user);
        return true;
    }


    //유저 정보수정
    public boolean updateUser(ChangeUserRequestDto user) {
        //존재확인은 컨트롤러에서
        User findUser = userRepositoryJPA.findByUserId(user.getUserId());
        findUser.setPassword(user.getPassword());
        findUser.setProfileImg(user.getProfileImg());

        userRepositoryJPA.save(findUser);
        return true;
    }

    //전체 유저 조회
    public List<User> retrieveAllUser(String kinderCode) {
        List<User> userlist = userRepositoryJPA.findByClassCodeStartsWith(kinderCode);

        return userlist;
    }
}
