package com.myapp.backend.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "TEACHER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEntity {

    @Id
    String userId;

    int stateCode;
    int accept;

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createDate;

    String profileImg;
    String classCode;

}
