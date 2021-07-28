package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.request.DosageResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DosageDao {
    public List<DosageResultDto> teacherDosageList(String userId);

    public List<DosageResultDto> parentDosageList(String userId);

    public DosageResultDto dosageDetail(int id);

}
