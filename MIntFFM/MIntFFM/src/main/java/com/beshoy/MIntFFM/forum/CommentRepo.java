package com.beshoy.MIntFFM.forum;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPost_PostId(Long postId);
    List<CommentEntity> findByUser_UserId(Long userId);
}