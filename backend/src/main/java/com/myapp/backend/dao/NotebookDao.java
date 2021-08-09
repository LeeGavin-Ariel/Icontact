package com.myapp.backend.dao;

import com.myapp.backend.domain.dto.notebook.NoteBookListDto;
import com.myapp.backend.domain.dto.notebook.NoteBookParamDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotebookDao {

    public List<NoteBookListDto> searchNotebook(NoteBookParamDto notebookParamDto);

}
