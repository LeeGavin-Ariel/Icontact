package com.myapp.backend.service;

import com.myapp.backend.domain.dto.admin.AdminUserDto;
import com.myapp.backend.domain.entity.Kid;
import com.myapp.backend.domain.entity.Teacher;
import com.myapp.backend.repository.KidRepository;
import com.myapp.backend.repository.TeacherRepository;
import com.myapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepositoryJPA;
    @Autowired
    private TeacherRepository teacherRepositoryJPA;
    @Autowired
    private KidRepository kidRepositoryJPA;


    private final int WAIT = 1;
    private final int APPROVE = 2;
    private final int REJECT = 3;

    // 아이 조회
    public Kid retrieveKid(int userId) {
        return  kidRepositoryJPA.findByUserId(userId);
    }

    //아이 승인
    public boolean approveKid(int profileId) {
        Kid kid = kidRepositoryJPA.findByKidId(profileId);

        kid.setAccept(APPROVE);
        kidRepositoryJPA.save(kid);
        return true;
    }

    //아이 거절
    public boolean rejectKid(int profileId) {
        Kid kid = kidRepositoryJPA.findByKidId(profileId);

        kid.setAccept(REJECT);
        kidRepositoryJPA.save(kid);
        return true;
    }

    //선생님 승인
    public Teacher retrieveTeacher(String userId) {
        return  teacherRepositoryJPA.findByUserId(userId);
    }

    //선생님 승인
    public boolean approveTeacher(String userId) {
        Teacher teacher = teacherRepositoryJPA.findByUserId(userId);

        teacher.setAccept(APPROVE);
        teacherRepositoryJPA.save(teacher);
        return true;
    }

    //선생님 거절
    public boolean rejectTeacher(String userId) {
        Teacher teacher = teacherRepositoryJPA.findByUserId(userId);

        teacher.setAccept(REJECT);
        teacherRepositoryJPA.save(teacher);
        return true;
    }

    public List<AdminUserDto> retrieveAllUser(String kinderCode) {
//         유치원 4자리로 유치원반코드 앞 4자리와 비교해서 같은 Teacher 목록과 Kid 목록을 각각 조회하여 리스트에 담아 반환
//         근데 Like 비교를 어떻게 하나요
//        List<AdminUserDto> resultList = null;
        List<Teacher> teacherlist = teacherRepositoryJPA.findByClassCodeStartsWith(kinderCode);
        List<Kid> kidlist = kidRepositoryJPA.findByClassCodeStartsWith(kinderCode);
        for (Teacher t : teacherlist) {
            System.out.println(t.getUserId());
        }
        for (Kid k : kidlist) {
            System.out.println(k.getUserId()+","+k.getKidName());

        }
        //성공

//        resultList.add((AdminUserDto) kidRepositoryJPA.findByClassCodeStartsWith(kinderCode));

        return null;
    }

}
