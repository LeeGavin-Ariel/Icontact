package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="DOSAGE")
public class Dosage {

    @Id
    private int dosageId;

    @Column(length = 50)
    private String symptom;

    @Column(length = 30)
    private String medicineType;

    private int dosageVol;

    private int dosageCnt;

    @Column(length = 30)
    private String dosageTime;

    @Column(length = 10)
    private String storage;

    @Column(length = 100)
    private String specialNote;

    @Column(insertable = false, updatable = false)
    private Date createDate;

    @Column(length = 15)
    private String userId;

}
