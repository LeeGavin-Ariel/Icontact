package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Teacher")
public class Teacher {

    @Id
    private String userId;
    private int stateCode;
    private int accept;
    private Date createDate;
    private String  profileImg;
    private String  classCode ;
}
