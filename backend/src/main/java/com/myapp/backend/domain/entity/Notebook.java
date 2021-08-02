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
@Table(name="NOTEBOOK")
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int noteId;

    @Column(length = 50)
    String title;

    @Column(length = 255)
    String content;

    @Column(insertable = false, updatable = false)
    private Date createDate;

    @Column(length = 100)
    String noteImgUrl;

    @Column(length = 15)
    String writerId;

    @Column(length = 15)
    String targetId;

}
