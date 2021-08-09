package com.myapp.backend.domain.dto.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResultDto {
    int pageCnt;

    List<AlbumThumbnailDto> thumbnailList;

}
