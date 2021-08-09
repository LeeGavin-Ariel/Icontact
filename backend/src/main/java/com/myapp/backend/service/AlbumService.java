package com.myapp.backend.service;

import com.myapp.backend.domain.dto.album.AlbumDetailDto;
import com.myapp.backend.domain.dto.album.AlbumDto;
import com.myapp.backend.domain.dto.album.AlbumResultDto;
import com.myapp.backend.domain.dto.album.AlbumThumbnailDto;

import java.util.List;

public interface AlbumService {
    public int albumInsert(AlbumDto albumDto);

    public AlbumResultDto albumThumbnail(String classCode, int pageNum);
    AlbumDetailDto getAlbumDetail(int albumId);
    public int albumDelete(int albumId);
}
