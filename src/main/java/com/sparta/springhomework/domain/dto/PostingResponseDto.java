package com.sparta.springhomework.domain.dto;

import com.sparta.springhomework.domain.entity.Timestamped;
import com.sparta.springhomework.domain.entity.Posting;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;


@Getter
@MappedSuperclass // 상속했을 때, 컬럼으로 인식하게 합니다.
@EntityListeners(AuditingEntityListener.class) // 생성/수정 시간을 자동으로 반영하도록 설정
public class PostingResponseDto extends Timestamped {
    private final String title;
    private final String content;
    private final String author;

    public PostingResponseDto (Posting entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        super.createdAt = entity.getCreatedAt();
    }

    @Builder
    public PostingResponseDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
