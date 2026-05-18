package com.beshoy.MIntFFM.forum;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostRepo extends JpaRepository <PostEntity, Long>  {

    List<PostEntity> findByUser_UserId(Long userId);
}
