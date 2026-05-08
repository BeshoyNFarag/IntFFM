package com.beshoy.MIntFFM.forum;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PostRepo extends JpaRepository <PostEntity, Integer>  {
}
