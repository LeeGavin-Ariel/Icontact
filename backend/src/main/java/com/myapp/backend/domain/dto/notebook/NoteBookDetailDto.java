package com.myapp.backend.domain.dto.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteBookDetailDto {

    int noteId;
    String title;
    String content;
    String createDate;
    String writerId;
    String writerName;
    String targetId;
    String targetName;
    String kidName;
    String noteImgUrl;
    //img 처리 방법 고민2
}
