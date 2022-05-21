package miro.board.web.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import miro.board.domain.posts.Posts;

@Getter
public class PostsResponseDto {

  private Long id;
  private String title;
  private String content;
  private String author;
  private LocalDateTime modifiedDate;

  public PostsResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
    this.modifiedDate = entity.getModifiedDate();
  }

}
