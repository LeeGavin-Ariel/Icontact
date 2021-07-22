package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "KINDER_CLASS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KinderClassEntity {

    @Id
    String classCode;

    String className;
    String kinderCode;

}
