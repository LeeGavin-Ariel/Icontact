package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="ALBUM")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int albumId;

    @Column(length = 50)
    private String title;

    @Column(updatable = false)
    private Date createDate;

    @Column(length = 100)
    private String thumbnailUrl;

    @Column(length = 100)
    private String downloadUrl;

    @Column(length = 6, updatable = false)
    private String classCode;

    @Column(length = 15, updatable = false)
    private String writerId;
}