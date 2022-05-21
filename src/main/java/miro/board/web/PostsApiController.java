package miro.board.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import miro.board.service.posts.PostsService;
import miro.board.web.dto.PostsListResponseDto;
import miro.board.web.dto.PostsResponseDto;
import miro.board.web.dto.PostsSaveRequestDto;
import miro.board.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

  private final PostsService postsService;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto requestDto)
  {
    return postsService.save(requestDto);
  }

  @PutMapping("/api/v1/posts/{id}")
  public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
    return postsService.update(id, requestDto);
  }

  @DeleteMapping("/api/v1/posts/{id}")
  public Long delete(@PathVariable Long id) {
    postsService.delete(id);
    return id;
  }

  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById(@PathVariable Long id) {
    PostsResponseDto dto = postsService.findById(id);
    return dto;
  }

  @GetMapping("/api/v1/posts/list")
  public List<PostsListResponseDto> findAll() {
    return postsService.findAllDesc();
  }

}
