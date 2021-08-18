package com.myapp.backend.domain.dto.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
