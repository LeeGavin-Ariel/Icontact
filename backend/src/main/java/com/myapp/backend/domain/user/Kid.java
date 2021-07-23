package com.myapp.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KID")
public class Kid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kidId;

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

    @Column(length = 15, updatable = false, insertable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
