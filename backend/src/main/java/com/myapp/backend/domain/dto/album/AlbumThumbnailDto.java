package com.myapp.backend.domain.dto.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumThumbnailDto {
    int albumId;
    String title;
    String thumbnailUrl;
    Date createDate;
    String createDate2;

}
