package com.myapp.backend.domain.kinder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KINDER_CLASS")
public class KinderClass {

    @Id
    @Column(length = 6)
    private String classCode;

    @Column(length = 20)
    private String className;

    @Column(length = 4, updatable = false, insertable = false)
    private String kinderCode;

    @ManyToOne
    @JoinColumn(name = "kinderCode")
    Kinder kinder;
}
