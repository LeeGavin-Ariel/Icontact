package com.myapp.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Table(name="TEACHER")
public class Teacher {

    @Id
    private String userId;

    private int stateCode;
    private int accept;
    private String createDate;

    @Column(length = 100)
    private String profileImg;

    @Column(length = 6)
    private String classCode;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
