package miro.board.web;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import miro.board.domain.posts.Posts;
import miro.board.domain.posts.PostsRepository;
import miro.board.web.dto.PostsSaveRequestDto;
import miro.board.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @LocalServerPort
  private int port;

  @After
  public void cleanup() throws Exception
  {
    postsRepository.deleteAll();
  }

  @Test
  public void 등록_테스트() throws Exception
  {
    //given
    String url = "http://localhost:" + port + "/api/v1/posts";
    String title = "test title";
    String content = "test content";

    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("test author")
        .build();

    //when
    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(title);
    assertThat(all.get(0).getContent()).isEqualTo(content);
  }

  @Test
  public void 업데이트_테스트() throws Exception
  {
    //given
    Posts post = postsRepository.save(Posts.builder()
        .title("test title")
        .content("test content")
        .author("test author")
        .build());
    Long id = post.getId();
    String updatedTitle = "updated Title";
    String updatedContent = "updated Content";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
        .title(updatedTitle)
        .content(updatedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + id;

    HttpEntity<PostsUpdateRequestDto> requestEntity =
        new HttpEntity<>(requestDto);

    //when
    ResponseEntity<Long> responseEntity =
        restTemplate.exchange(url, HttpMethod.PUT, (HttpEntity<?>) requestEntity, Long.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(updatedTitle);
    assertThat(all.get(0).getContent()).isEqualTo(updatedContent);
  }
}
