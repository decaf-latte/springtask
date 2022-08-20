package com.sparta.springhomework.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.springhomework.domain.dto.PostingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posting extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @JsonIgnore
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @JsonIgnore
    @Column(nullable = false)
    private String password;


    //게시글 작성시
    public Posting(PostingRequestDto postingRequestDto) {
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
        this.author = postingRequestDto.getAuthor();
        this.password = postingRequestDto.getPassword();
    }

    //게시글 수정시
    public void update(PostingRequestDto postingRequestDto) {
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
        this.author = postingRequestDto.getAuthor();
        this.password = postingRequestDto.getPassword();
    }

}
