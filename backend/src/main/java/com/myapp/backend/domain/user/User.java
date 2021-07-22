package com.myapp.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER")
public class User {
    @Id
    String userId;
    @JsonIgnore
    String password;
    String userName;
    String userTel;

    @Column(updatable = false)
    int type;

}
