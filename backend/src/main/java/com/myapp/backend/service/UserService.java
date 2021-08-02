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
    private UserRepository userRepositoryJPA;
    @Autowired
    KinderRepository kinderRepository;

    private final int WAIT = 1;
    private final int APPROVE = 2;
    private final int REJECT = 3;
    private final int REMOVE = 4;


    // 유저 조회
    public MyPageResultDto retrieveUser(String userId) {
        MyPageResultDto myPageResultDto = new MyPageResultDto();
        User findUser = userRepositoryJPA.findByUserId(userId);
        myPageResultDto.userInfoSet(findUser);
        myPageResultDto.kinderClassInfoSet(kinderRepository.findByClassCode(findUser.getClassCode()));

        return myPageResultDto;
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

    //전체 유저 조회
    public List<User> retrieveAllUser(String kinderCode) {
        List<User> userlist = userRepositoryJPA.findByClassCodeStartsWith(kinderCode);

        return userlist;
    }


    public String updateProfileImg(MultipartFile file, String userId) throws IOException {
        // parent directory를 찾는다.
//        Path directory = Paths.get("./profileImg").toAbsolutePath().normalize();
        Path directory = Paths.get("../frontend/src/assets/profileImg").toAbsolutePath().normalize();
        System.out.println(directory);

        // directory 해당 경로까지 디렉토리를 모두 만든다.
        Files.createDirectories(directory);

        // 파일명을 바르게 수정한다.
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // 아니면 걍 유저 아이디로 파일명 지정
        String fileName = userId+"."+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        // 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류방지)
        Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");
        // 파일을 저장할 경로를 Path 객체로 받는다.
        Path targetPath = directory.resolve(fileName).normalize();
        System.out.println(targetPath);
        System.out.println(targetPath.toString());

        // 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
//        Assert.state(!Files.exists(targetPath), fileName + " File already exists.");

        User findUser = userRepositoryJPA.findByUserId(userId);
        findUser.setProfileImg(targetPath.toString());
        userRepositoryJPA.save(findUser);

        file.transferTo(targetPath);

        return targetPath.toString();
    }

    public boolean updatePassword(ChangePasswordDto changePasswordDto) {
        User findUser = userRepositoryJPA.findByUserId(changePasswordDto.getUserId());
        findUser.setPassword(changePasswordDto.getPassword());
        userRepositoryJPA.save(findUser);
        return true;
    }

    //우리반 유저 조회
    public List<User> retrieveAllClassUser(String classCode) {
        List<User> userlist = userRepositoryJPA.findByClassCode(classCode);
        return userlist;
    }
}
