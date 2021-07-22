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
    String kidId;
    String kidName;
    int postCnt;
    int followerCnt;
    int followCnt;
    int accept;
    String createDate;
    String profileImg;
    String classCode;
    String userId;
}
