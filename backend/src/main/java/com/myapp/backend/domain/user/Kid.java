package com.myapp.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kid {

    @Id
    private String kidId;

    @Column(length = 8)
    private String kidName;

    private int postCnt;
    private int followerCnt;
    private int followCnt;
    private int accept;

    @Column(length = 100)
    private String statusMsg;
    private String createDate;

    @Column(length = 100)
    private String profileImg;

    @Column(length = 6)
    private String classCode;

}
