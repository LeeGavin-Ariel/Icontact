package com.myapp.backend.service;

import com.myapp.backend.repository.SNSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SNSService {

    @Autowired
    private SNSRepository snsRepositoryJPA;

    public boolean retrievePost(long profileId) {
//        Teacher teacher = snsRepositoryJPA.findByUserId(profileId);

//        teacher.setAccept(REJECT);
//        snsRepositoryJPA.save(teacher);
        return true;
    }
}
