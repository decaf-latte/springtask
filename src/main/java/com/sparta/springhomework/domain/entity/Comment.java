package com.sparta.springhomework.domain.entity;


import com.sparta.springhomework.domain.dto.CommentRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String content;

  private String author;

  @ManyToOne
  @JoinColumn(name = "posting_id")
  private Posting posting;

  public Comment(CommentRequestDto commentRequestDto, Posting posting) {
    this.author = posting.getAuthor();
    this.content = commentRequestDto.getContent();
    this.posting = posting;
  }


}
