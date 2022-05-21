package miro.board.service.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import miro.board.domain.posts.Posts;
import miro.board.domain.posts.PostsRepository;
import miro.board.web.dto.PostsListResponseDto;
import miro.board.web.dto.PostsResponseDto;
import miro.board.web.dto.PostsSaveRequestDto;
import miro.board.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
  private final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto)
  {
    Posts post = postsRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

    post.update(requestDto.getTitle(), requestDto.getContent());

    return id;
  }

  @Transactional
  public void delete(Long id)
  {
    Posts post = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

    postsRepository.delete(post);
  }

  @Transactional
  public PostsResponseDto findById(Long id)
  {
    Posts entity = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
    return new PostsResponseDto(entity);
  }

  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc()
  {
    return postsRepository.findAllDesc().stream()
        .map(PostsListResponseDto::new)
        .collect(Collectors.toList());
  }
}
