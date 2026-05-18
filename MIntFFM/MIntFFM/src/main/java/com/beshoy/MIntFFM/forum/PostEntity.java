package com.beshoy.MIntFFM.forum;



import com.beshoy.MIntFFM.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    @OneToMany(mappedBy = "post")
    private List<CommentEntity> comments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

    }



    public PostEntity() {
    }

    public PostEntity(String title, String content, UserEntity user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }


}