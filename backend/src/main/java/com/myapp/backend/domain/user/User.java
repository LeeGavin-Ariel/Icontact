package com.myapp.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Kid> kids = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Teacher teacher;
}
