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
public class NoteBookListDto {

    int noteId;
    String title;
    String writerId;
    String writerName;
    String targetId;
    String targetName;
    Date createDate;
    int totalNum;
    String noteImgUrl;
}
