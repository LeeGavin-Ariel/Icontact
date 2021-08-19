package com.myapp.backend.service;


import com.myapp.backend.domain.dto.attend.AttendDto;

import java.util.Date;
import java.util.List;

public interface AttendService {

    public int writeAttend(String userId, Date date);

    public List<AttendDto> attendParents(String userId, Date date);
    public List<AttendDto> attendTeacher(String userId, Date date);

}
