package Blogproject.springbootrestapi.service;

import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.payload.PostDto;
import Blogproject.springbootrestapi.payload.PostResponse;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);
    List<PostDto> getPostsByCategoryId(Long categoryId);

    PostDto getPostByTitle (String title);

    List<PostDto> getRandomPosts();
    List<PostDto> getPostByTopic(String topic);

    List<String> getPopularTopics();

    List<PostDto> getUserList(Long userId);


}
