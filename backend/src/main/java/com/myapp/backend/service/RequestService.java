package com.myapp.backend.service;


import com.myapp.backend.domain.dto.request.RequestDto;
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

}
