package com.sparta.springhomework.domain.dto;


import com.sparta.springhomework.domain.entity.Posting;
import lombok.Getter;

@Getter

public class PostPostingResponseDto {
    private String title;
    private String content;
    private String author;
    private String password;

    public PostPostingResponseDto(Posting entity){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.password = entity.getPassword();

    }


}
