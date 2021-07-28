package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.request.DosageResultDto;
import com.myapp.backend.domain.dto.request.ReturnhomeResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReturnhomeDao {
    public List<ReturnhomeResultDto> teacherReturnhomeList(String userId);

    public List<ReturnhomeResultDto> parentReturnhomeList(String userId);

    public ReturnhomeResultDto returnhomeDetail(int id);

}
