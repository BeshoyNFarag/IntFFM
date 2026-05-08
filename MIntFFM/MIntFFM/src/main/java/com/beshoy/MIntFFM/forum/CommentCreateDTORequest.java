package com.beshoy.MIntFFM.forum;


public record CommentCreateDTORequest(
        String comment,
        Long postId
) {
}