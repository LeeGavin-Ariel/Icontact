package com.myapp.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="USER")
public class User {
    @Id
    @Column(length = 15)
    private String userId;

    @JsonIgnore
    @Column(length = 15)
    private String password;

    @Column(length = 8)
    private String userName;

    @Column(length = 13)
    private String userTel;

    @Column(updatable = false)
    private int type;

    @JsonIgnore
    @Column(length = 255)
    private String refreshToken;

    private int accept;

    @Column(insertable = false, updatable = false)
    private Date createDate;

    @Column(length = 100)
    private String profileImg;

    private int stateCode;

    @Column(length = 8)
    private String kidName;

    @Column(length = 6)
    private String classCode;
}
