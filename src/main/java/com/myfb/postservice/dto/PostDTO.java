package com.myfb.postservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Long postId;
    private String title;
    private String body;
    private Long userId;
}
