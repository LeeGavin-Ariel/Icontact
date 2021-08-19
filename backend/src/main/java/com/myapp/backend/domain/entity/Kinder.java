package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "kinderClasses")
@Table(name = "KINDER")
public class Kinder {

    @Id
    @Column(length = 4)
    private String kinderCode;

    @Column(length = 20)
    private String kinderName;

    @OneToMany(mappedBy = "kinder")
    private List<KinderClass> kinderClasses;

}
