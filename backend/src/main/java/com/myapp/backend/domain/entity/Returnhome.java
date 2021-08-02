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
@Table(name="RETURNHOME")
public class Returnhome {
    @Id
    private int rhId;

    @Column(length = 8)
    private String rhDate;

    @Column(length = 10)
    private String rhTime;

    @Column(length = 10)
    private String guardian;

    @Column(length = 11)
    private String guardianTel;

    @Column(length = 10)
    private String emergency;

    @Column(length = 11)
    private String emergencyTel;

    @Column(updatable = false)
    private Date createDate;

    @Column(length = 15, updatable = false)
    private String userId;

}
