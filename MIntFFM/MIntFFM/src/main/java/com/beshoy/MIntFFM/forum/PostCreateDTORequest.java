package com.beshoy.MIntFFM.forum;


public record PostCreateDTORequest(
        String title,
        String content,
        Long userId
) {
}