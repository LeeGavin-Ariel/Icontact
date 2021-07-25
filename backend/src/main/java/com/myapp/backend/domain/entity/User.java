package com.myapp.backend.domain.entity;

import lombok.*;

import javax.persistence.*;

//@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER")
public class User {

    @Id @Column(name = "userId")
    private String userId;
    private String password;
    private String userName;
    private String userTel;
    private int type;

}
