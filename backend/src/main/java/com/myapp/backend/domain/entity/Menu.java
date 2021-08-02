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
@Table(name="MENU")
public class Menu {
    @Id
    private int menuId;

    @Column(length = 100)
    private String amSnack;

    @Column(length = 100)
    private String amSnackImgUrl;

    @Column(length = 100)
    private String lunch;

    @Column(length = 100)
    private String lunchImgUrl;

    @Column(length = 100)
    private String pmSnack;

    @Column(length = 100)
    private String pmSnackImgUrl;

    @Column(updatable = false)
    private Date createDate;

    @Column(length = 6, updatable = false)
    private String classCode;

    @Column(length = 15, updatable = false)
    private String userId;

}
