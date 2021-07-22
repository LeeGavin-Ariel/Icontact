package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "KID")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KidEntity {

    @Id
    int kidId;

    String kidName;

    int postCnt;
    int followerCnt;
    int followCnt;

    String statusMsg;
    int accept;

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createDate;

    String profileImg;
    String classCode;
    String userId;
}
