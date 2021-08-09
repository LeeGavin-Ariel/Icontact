package com.myapp.backend.domain.dto.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteBookParamDto {

    String userId;
    String searchParam;
    int type;
    int limit;
    int offset;
}
