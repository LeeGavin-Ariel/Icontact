package com.myapp.backend.service;


import com.myapp.backend.domain.dto.request.RequestDto;

public interface RequestService {

    public int insertDosage(RequestDto requestDto);

    public int insertReturnhome(RequestDto requestDto);
}
