package com.myapp.backend.domain.dto.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {
    private int albumId;
    private String title;
    private String createDate;
    private String thumbnailUrl;
    private String classCode;
    private String writerId;
    private String downloadUrl;
    private ArrayList<String> photoList;
}