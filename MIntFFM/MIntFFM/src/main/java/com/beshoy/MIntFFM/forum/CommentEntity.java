package com.beshoy.MIntFFM.forum;


import com.beshoy.MIntFFM.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "comment", nullable = false, length = 255)
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


    public CommentEntity() {
    }

    public CommentEntity(String comment, PostEntity post) {
        this.comment = comment;
        this.post = post;

    }




}