package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.request.DosageResultDto;
import com.myapp.backend.domain.dto.request.RequestParamDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DosageDao {
    public List<DosageResultDto> teacherDosageList(RequestParamDto requestParamDto);

    public List<DosageResultDto> parentDosageList(RequestParamDto requestParamDto);

    public DosageResultDto dosageDetail(int id);

    public int totalTeacherDosageList(String userId);

    public int totalParentDosageList(String userId);
}
