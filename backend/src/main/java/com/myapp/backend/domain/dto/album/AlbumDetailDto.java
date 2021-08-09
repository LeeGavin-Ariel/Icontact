package com.myapp.backend.domain.dto.album;

import com.myapp.backend.domain.entity.AlbumImg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDetailDto {
    private String downloadUrl;
    private List<AlbumImg> albumImgs;
}
