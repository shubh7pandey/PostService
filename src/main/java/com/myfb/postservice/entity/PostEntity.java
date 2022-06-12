package com.myfb.postservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Post_Table")
@Getter
@Setter
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long postId;
    private String title;
    private String body;
    private Long userId;
}
