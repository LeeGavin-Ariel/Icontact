package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.request.RequestParamDto;
import com.myapp.backend.domain.dto.request.ReturnhomeResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReturnhomeDao {
    public List<ReturnhomeResultDto> teacherReturnhomeList(RequestParamDto requestParamDto);

    public List<ReturnhomeResultDto> parentReturnhomeList(RequestParamDto requestParamDto);

    public ReturnhomeResultDto returnhomeDetail(int id);

    public int totalTeacherReturnhomeList(String userId);

    public int totalParentReturnhomeList(String userId);
}
