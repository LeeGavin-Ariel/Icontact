package com.myapp.backend.service;


import com.myapp.backend.domain.dto.request.RequestDto;

import java.util.List;

public interface RequestService {

    public int dosageInsert(RequestDto requestDto);

    public int returnhomeInsert(RequestDto requestDto);

    public int dosageUpdate(RequestDto requestDto);

    public int returnhomeUpdate(RequestDto requestDto);

}
