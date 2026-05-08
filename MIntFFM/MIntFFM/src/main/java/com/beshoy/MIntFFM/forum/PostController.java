package com.beshoy.MIntFFM.forum;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    PostRepo postRepo;

    public PostController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @PostMapping("/posts")
    public PostEntity post(@RequestBody PostEntity post) {
        return postRepo.save(post);
    }
}
