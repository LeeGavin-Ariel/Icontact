package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
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
