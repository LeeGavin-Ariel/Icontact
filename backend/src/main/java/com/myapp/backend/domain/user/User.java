package com.myapp.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
