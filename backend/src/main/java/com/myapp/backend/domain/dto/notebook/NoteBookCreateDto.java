package com.myapp.backend.domain.dto.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteBookCreateDto {

    int noteId;
    String title;
    String content;
    String writerId;
    String targetId;
    //img 처리 방법 고민

}
