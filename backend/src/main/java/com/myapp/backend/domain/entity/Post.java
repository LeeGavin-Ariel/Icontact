package com.myapp.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Post")
public class Post {
    @Id
    private long postId;
    private String content;
    private String postImgUrl;
    private Date createDate;
    private int commentCnt;
    private int likeCnt;
    private int kidId;
}
