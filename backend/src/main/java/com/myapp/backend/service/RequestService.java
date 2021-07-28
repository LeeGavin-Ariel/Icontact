package com.myapp.backend.service;


import com.myapp.backend.domain.dto.request.DosageResultDto;
import com.myapp.backend.domain.dto.request.RequestDto;
import com.myapp.backend.domain.dto.request.ReturnhomeResultDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestService {

    public int dosageInsert(RequestDto requestDto);

    public int returnhomeInsert(RequestDto requestDto);

    public int dosageUpdate(RequestDto requestDto);

    public int returnhomeUpdate(RequestDto requestDto);

    @Transactional
    public int dosageDelete(int id);

    @Transactional
    public int returnhomeDelete(int id);

    public List<DosageResultDto> teacherDosageList(String userId);

    public List<ReturnhomeResultDto> teacherReturnhomeList(String userId);

    public List<DosageResultDto> parentDosageList(String userId);

    public List<ReturnhomeResultDto> parentReturnhomeList(String userId);
}
