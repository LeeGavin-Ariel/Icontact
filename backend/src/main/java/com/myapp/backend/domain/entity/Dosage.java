package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="DOSAGE")
@ToString(exclude = "user")
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

    @Column(length = 15, insertable = false, updatable = false)
    private String userId;


}
